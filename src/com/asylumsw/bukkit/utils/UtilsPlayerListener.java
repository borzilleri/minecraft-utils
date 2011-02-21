package com.asylumsw.bukkit.utils;

import org.bukkit.event.player.PlayerListener;
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
	public void onPlayerJoin(PlayerEvent event) {
		MessageOfTheDay.sendMotdTo(event.getPlayer());
	}


}
