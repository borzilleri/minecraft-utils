package io.rampant.bukkit.utils;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerJoinEvent;

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
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.sendAnnounceTo(event.getPlayer(), "motd.prefix", "motd.text");
		plugin.sendPlayerList(event.getPlayer());
	}
}
