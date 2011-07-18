package io.rampant.bukkit.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author jonathan
 */
public class Utils extends JavaPlugin {
	protected static final String DEFAULT_WHO_PREFIX = "[#GRAY]Online Players: ";
	protected static final String DEFAULT_WHO_COLOR = "AQUA";
	protected static final String DEFAULT_WHO_DELIMITER = "[#GRAY], ";
	protected static final String DEFAULT_RULES_PREFIX = "[#GRAY][rules] [#DARK_RED]";
	protected static final String DEFAULT_MOTD_PREFIX = "[#GRAY][motd] [#DARK_AQUA]";
	protected static final String DEFAULT_MOTD = "Welcome!";
	protected static final String DEFAULT_RULES = "Don't be a dick!";

	public UtilsPlayerListener playerListener = new UtilsPlayerListener(this);
	public static Server server;
	public Configuration config;

	public void loadDefaults() {
		config = getConfiguration();

		config.getString("who.prefix", DEFAULT_WHO_PREFIX);
		config.getString("who.color", DEFAULT_WHO_COLOR);
		config.getString("who.delimiter", DEFAULT_WHO_DELIMITER);
		
		config.getString("rules.prefix", DEFAULT_RULES_PREFIX);
		List<String> list = config.getStringList("rules.text", null);
		if( list.isEmpty() ) {
			list.add(DEFAULT_RULES);
			list.add(DEFAULT_RULES);
			config.setProperty("rules.text", list);
		}
		
		config.getString("motd.prefix", DEFAULT_MOTD_PREFIX);
		list = config.getStringList("motd.text", null);
		if( list.isEmpty() ) {
			list.add(DEFAULT_MOTD);
			config.setProperty("motd.text", list);
		}
		
		config.save();
	}

	@Override
	public void onEnable() {
		server = getServer();
		loadDefaults();

		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Monitor, this);

		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	@Override
	public void onDisable() {
		System.out.println("Utils Disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if( cmd.getName().equalsIgnoreCase("who") ) {
			sendPlayerList(sender);
			return true;
		}
		else if( cmd.getName().equalsIgnoreCase("rules") ) {
			sendAnnounceTo(sender, "rules.prefix", "rules.text");
			return true;
		}
		else if( cmd.getName().equalsIgnoreCase("motd") ) {
			sendAnnounceTo(sender, "motd.prefix", "motd.text");
			return true;
		}
		else if( cmd.getName().equalsIgnoreCase("utils") ) {
			if( 0 >= args.length ) {
				return false;
			}
			String action = args[0];

			if( action.equalsIgnoreCase("reload") ) {
				if( !sender.isOp() ) {
					sender.sendMessage(ChatColor.DARK_GRAY + "[utils] "
									+ ChatColor.RED + "ERROR: Only admins may use this command");
				}
				loadDefaults();
				sender.sendMessage(ChatColor.DARK_GRAY + "[utils] Configuration reloaded.");
				return true;
			}
			return false;
		}
		
		return false;
	}

	protected void sendPlayerList(CommandSender sender) {
		String color = ChatColor.valueOf(config.getString("who.color")).toString();
		String delimiter = parseColors(config.getString("who.delimiter"));

		String msg = parseColors(config.getString("who.prefix"));
		for( Player player : getServer().getOnlinePlayers() ) {
			if( msg.length() >= 45) {
				sender.sendMessage(msg);
				msg = "";
			}
			msg += color+player.getName()+delimiter;
		}
		sender.sendMessage(msg.substring(0, msg.length() - 2));
	}

	protected void sendAnnounceTo(CommandSender sender, String prefixConfig, String textConfig) {
		String prefix = parseColors(config.getString(prefixConfig, ""));
		List<String> text = config.getStringList(textConfig, null);
		for( String line : text ) {
			sender.sendMessage(prefix+parseColors(line));
		}
	}

	protected String parseColors(String str) {
		Pattern p = Pattern.compile("\\[#([A-Z_]+)\\]");
		Matcher m = p.matcher(str);
		StringBuffer out = new StringBuffer();
		String color;
		while( m.find() ) {
			try {
				color = ChatColor.valueOf(m.group(1)).toString();
			}
			catch ( IllegalArgumentException ex ){
				color = "";
			}
			m.appendReplacement(out, color);
		}
		m.appendTail(out);
		return out.toString();
	}
}
