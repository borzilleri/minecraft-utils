package com.asylumsw.bukkit.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Server;

/**
 *
 * @author jonathan
 */
public class PlayerList {

	public static void sendWhoList(Player player, Server server) {
		player.sendMessage(getPlayerList(server));
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
