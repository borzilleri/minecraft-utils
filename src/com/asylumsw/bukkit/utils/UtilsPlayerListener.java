package com.asylumsw.bukkit.utils;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.Player;
import org.bukkit.event.player.PlayerChatEvent;


/**
 *
 * @author jonathan
 */
public class UtilsPlayerListener extends PlayerListener {
	private final Utils plugin;

	public UtilsPlayerListener(Utils instance) {
		plugin = instance;
	}

	@Override
	public void onPlayerCommand(PlayerChatEvent event) {
		String[] split = event.getMessage().split(" ");
		Player player = event.getPlayer();

		if (split[0].equalsIgnoreCase("/who")) {
			player.sendMessage(PlayerList.getPlayerList(plugin.getServer()));
			event.setCancelled(true);
		}
		else if( split[0].equalsIgnoreCase("/rules") ) {
			player.sendMessage(ServerRules.getRules());
			event.setCancelled(true);
		}
		else if( split[0].equalsIgnoreCase("/motd") ) {
			event.setCancelled(true);
		}
	}


}
