package com.asylumsw.bukkit.utils;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jonathan
 */
public class AppleTree {

	public final static double APPLE_DROP_CHANCE = 3.00;
	public final static double GOLDEN_APPLE_DROP_CHANCE = 0.05;

	public static void onLeavesDecay(LeavesDecayEvent event) {
		if (event.isCancelled()) {
			return;
		}

		Random generator = new Random();
		double chance = generator.nextDouble() * 100.0;
		Material itemToSpawn = null;

		if (GOLDEN_APPLE_DROP_CHANCE >= chance) {
			itemToSpawn = Material.GOLDEN_APPLE;
		} else if (APPLE_DROP_CHANCE+GOLDEN_APPLE_DROP_CHANCE >= chance) {
			itemToSpawn = Material.APPLE;
		}

		if (null == itemToSpawn) {
			return;
		}

		event.getBlock().getWorld().dropItemNaturally(
						new Location(event.getBlock().getWorld(),
						event.getBlock().getX(),
						event.getBlock().getY(), event.getBlock().getZ()),
						new ItemStack(itemToSpawn, 1));
	}
}
