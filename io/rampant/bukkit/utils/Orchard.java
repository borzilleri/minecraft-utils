package io.rampant.bukkit.utils;

import java.util.Properties;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jonathan
 */
public class Orchard {

	protected final static double DEFAULT_APPLE_DROP_CHANCE = 0.30;
	protected final static double DEFAULT_GOLDEN_APPLE_DROP_CHANCE = 0.005;
	protected final static double DEFAULT_COCOA_DROP_CHANCE = 0.20;
	protected static double APPLE_DROP_CHANCE;
	protected static double GOLDEN_APPLE_DROP_CHANCE;
	protected static double COCOA_DROP_CHANCE;

	protected final static byte leafTypeNormal = 0;
	protected final static byte leafTypeRedwood = 1;
	protected final static byte leafTypeBirch = 2;

	protected static byte treeTypeApple;
	protected static byte treeTypeCocoa;
	protected static byte default_treeTypeApple = leafTypeNormal;
	protected static byte default_treeTypeCocoa = leafTypeRedwood;

	public static void reportDropChances(CommandSender player) {
		player.sendMessage(ChatColor.GRAY+"Cocoa Bean Drop Chance:"+String.valueOf(COCOA_DROP_CHANCE));
		player.sendMessage(ChatColor.GRAY+"Apple Drop Chance: "+String.valueOf(APPLE_DROP_CHANCE));
		player.sendMessage(ChatColor.GRAY+"Golden Apple Drop Chance: "+String.valueOf(GOLDEN_APPLE_DROP_CHANCE));
	}

	public static void init(Properties props) {
			APPLE_DROP_CHANCE = DEFAULT_APPLE_DROP_CHANCE;

			GOLDEN_APPLE_DROP_CHANCE = DEFAULT_GOLDEN_APPLE_DROP_CHANCE;

			COCOA_DROP_CHANCE = DEFAULT_COCOA_DROP_CHANCE;

		try {
			treeTypeApple = Byte.parseByte(props.getProperty("tree.apple"));
		}
		catch ( EnumConstantNotPresentException e ) {
			treeTypeApple = default_treeTypeApple;
		}

		try {
			treeTypeCocoa = Byte.parseByte(props.getProperty("tree.cocoa"));
		}
		catch ( Exception e ) {
			treeTypeCocoa = default_treeTypeCocoa;
		}

	}

	public static void onLeavesDecay(LeavesDecayEvent event) {
		if (event.isCancelled()) return;

		byte leafType = event.getBlock().getData();
		if( treeTypeApple == leafType ) {
			dropApples(event.getBlock());
		}
		else if( treeTypeCocoa == leafType ) {
			dropCocoaBeans(event.getBlock());
		}
	}

	protected static void dropApples(Block block) {
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
		block.getWorld().dropItemNaturally(
						new Location(block.getWorld(),
						block.getX(),block.getY(),block.getZ()),
						new ItemStack(itemToSpawn, 1));
	}
	protected static void dropCocoaBeans(Block block) {
		Random generator = new Random();
		double chance = generator.nextDouble() * 100.0;
		ItemStack itemToSpawn = null;

		if( COCOA_DROP_CHANCE >= chance) {
			itemToSpawn.setType(Material.INK_SACK);
			itemToSpawn.setDurability((short)3);
		}
		if (null == itemToSpawn) return;
		block.getWorld().dropItemNaturally(
						new Location(block.getWorld(),
						block.getX(),block.getY(),block.getZ()),
						itemToSpawn);

	}
}
