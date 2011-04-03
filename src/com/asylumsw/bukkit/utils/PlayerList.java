package com.asylumsw.bukkit.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

/**
 *
 * @author jonathan
 */
public class PlayerList {

	public static void sendWhoList(CommandSender sender, Server server) {
		sender.sendMessage(getPlayerList(server));
	}

	public static String getPlayerList(Server server) {
		String list = ChatColor.GRAY + "Online Players: ";
		
		for( Player player : server.getOnlinePlayers() ) {
			list += ChatColor.AQUA + player.getName()
							+ ChatColor.GRAY + ", ";
		}

		return list;		
	}

}
