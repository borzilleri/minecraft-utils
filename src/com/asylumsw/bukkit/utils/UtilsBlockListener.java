package com.asylumsw.bukkit.utils;

import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.LeavesDecayEvent;

/**
 *
 * @author jonathan
 */
public class UtilsBlockListener extends BlockListener {
	private final Utils plugin;

	public UtilsBlockListener(Utils instance) {
		plugin = instance;
	}

	@Override
	public void onLeavesDecay(LeavesDecayEvent event) {
		AppleTree.onLeavesDecay(event);
	}


}
