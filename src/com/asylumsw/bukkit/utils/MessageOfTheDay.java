/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asylumsw.bukkit.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import org.bukkit.ChatColor;

/**
 *
 * @author jonathan
 */
public class MessageOfTheDay {

	protected final static String motdPrefix = "[motd] ";
	protected final static String motdFileName = "motd.txt";
	protected static LinkedList<String> motd;

	protected static void init() {
		if (null == motd) {
			motd = new LinkedList<String>();
		}
		motd.clear();

		File rulesFile = new File(motdFileName);
		if (rulesFile.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(rulesFile));
				String line;
				while ((line = reader.readLine()) != null) {
					if (!line.startsWith("#")) {
						motd.add(line);
					}
				}
			}
			catch (Exception e) {
			}
		}

	}

	public static String getMotd() {
		if (null == motd) init();

		if (0 == motd.size()) {
			return ChatColor.GRAY + motdPrefix
							+ ChatColor.DARK_AQUA + "Welcome!";
		}

		String message = "";
		for (String line : motd) {
			message += ChatColor.GRAY + motdPrefix
							+ ChatColor.DARK_AQUA + line;
		}

		return message;
	}

}
