package com.asylumsw.bukkit.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author jonathan
 */
public class Utils extends JavaPlugin {
	public UtilsPlayerListener playerListener = new UtilsPlayerListener(this);
	public UtilsBlockListener blockListener = new UtilsBlockListener(this);
	public static Properties props = new Properties();
	public static Server server;

	public void loadProperties() {
		try {
			props.load(new FileInputStream("utils.properties"));
			AppleTree.setDropChances(props);
		}
		catch( IOException ex ) {
			System.out.println(ex.getMessage());
		}
	}
	
	@Override
	public void onEnable() {
		server = getServer();
		loadProperties();

		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Monitor, this);
		pm.registerEvent(Event.Type.LEAVES_DECAY, blockListener, Event.Priority.Monitor, this);

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
			PlayerList.sendWhoList(sender, getServer());
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("rules")) {
			ServerRules.sendRulesTo(sender);
			return true;
		}
		else if( cmd.getName().equalsIgnoreCase("motd") ) {
			MessageOfTheDay.sendMotdTo(sender);
			return true;
		}
		else if( cmd.getName().equalsIgnoreCase("props") ) {
			if( !sender.isOp() ) {
				sender.sendMessage(ChatColor.DARK_GRAY+"[utils] "+
								ChatColor.RED+"ERROR: Only admins may use this command");
				return true;
			}

			if( 0 >= args.length ) return false;
			if( args[1].equalsIgnoreCase("refresh") ) {
				loadProperties();
				sender.sendMessage("[utils] Properties Reloaded");
			}
			else if( args[1].equalsIgnoreCase("report") ) {
				AppleTree.reportDropChances((Player)sender);
			}
		}

		return false;
	}
}
