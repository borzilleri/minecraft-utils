package com.asylumsw.bukkit.utils;

import java.io.File;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author jonathan
 */
public class Utils extends JavaPlugin {
	public UtilsPlayerListener playerListener = new UtilsPlayerListener(this);
	public UtilsBlockListener blockListener = new UtilsBlockListener(this);
	
	@Override
	public void onEnable() {
		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Monitor, this);
		pm.registerEvent(Event.Type.LEAVES_DECAY, blockListener, Event.Priority.Monitor, this);

		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	@Override
	public void onDisable() {
		System.out.println("Kits Disabled.");
	}

	public static void main(String[] args) {}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if( cmd.getName().equalsIgnoreCase("who") ) {
			PlayerList.sendWhoList((Player)sender, getServer());
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("rules")) {
			ServerRules.sendRulesTo((Player)sender);
			return true;
		}
		else if( cmd.getName().equalsIgnoreCase("motd") ) {
			MessageOfTheDay.sendMotdTo((Player)sender);
			return true;
		}

		return false;
	}
}
