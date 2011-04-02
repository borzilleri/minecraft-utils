package com.asylumsw.bukkit.utils;

import java.util.Properties;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jonathan
 */
public class AppleTree {

	protected final static double DEFAULT_APPLE_DROP_CHANCE = 0.50;
	protected final static double DEFAULT_GOLDEN_APPLE_DROP_CHANCE = 0.005;
	protected static double APPLE_DROP_CHANCE;
	protected static double GOLDEN_APPLE_DROP_CHANCE;

	public static void reportDropChances(Player player) {
		player.sendMessage(ChatColor.GRAY+"Apple Drop Chance: "+String.valueOf(APPLE_DROP_CHANCE));
		player.sendMessage(ChatColor.GRAY+"Golden Apple Drop Chance: "+String.valueOf(GOLDEN_APPLE_DROP_CHANCE));
	}

	public static void setDropChances(Properties props) {
		if( null != props.get("apple.drop") ) {
			APPLE_DROP_CHANCE = Double.parseDouble(Utils.props.get("apple.drop").toString());
		}
		else {
			APPLE_DROP_CHANCE = DEFAULT_APPLE_DROP_CHANCE;
		}

		if( null != props.get("apple.golden.drop") ) {
			GOLDEN_APPLE_DROP_CHANCE = Double.parseDouble(Utils.props.get("apple.golden.drop").toString());
		}
		else {
			GOLDEN_APPLE_DROP_CHANCE = DEFAULT_GOLDEN_APPLE_DROP_CHANCE;
		}
	}

	public static void onLeavesDecay(LeavesDecayEvent event) {
		if (event.isCancelled()) return;
		
		Random generator = new Random();
		double chance = generator.nextDouble() * 100.0;
		Material itemToSpawn = null;

		if( GOLDEN_APPLE_DROP_CHANCE >= chance) {
			itemToSpawn = Material.GOLDEN_APPLE;
		}
		else if( APPLE_DROP_CHANCE+GOLDEN_APPLE_DROP_CHANCE >= chance) {
			itemToSpawn = Material.APPLE;
		}

		if (null == itemToSpawn) return;
		
		event.getBlock().getWorld().dropItemNaturally(
						new Location(event.getBlock().getWorld(),
						event.getBlock().getX(),
						event.getBlock().getY(), event.getBlock().getZ()),
						new ItemStack(itemToSpawn, 1));
	}
}
