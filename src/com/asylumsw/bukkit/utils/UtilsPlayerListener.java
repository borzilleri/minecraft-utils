package com.asylumsw.bukkit.utils;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;


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
			ServerRules.sendRulesTo(player);
			event.setCancelled(true);
		}
		else if( split[0].equalsIgnoreCase("/motd") ) {
			MessageOfTheDay.sendMotdTo(player);
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerJoin(PlayerEvent event) {
		MessageOfTheDay.sendMotdTo(event.getPlayer());
	}


}
