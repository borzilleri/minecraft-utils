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
public class ServerRules {

	protected final static String rulesPrefix = "[rules] ";
	protected final static String rulesFileName = "rules.txt";
	protected static LinkedList<String> rules;

	protected static void init() {
		if (null == rules) {
			rules = new LinkedList<String>();
		}
		rules.clear();

		File rulesFile = new File(rulesFileName);
		if (rulesFile.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(rulesFile));
				String line;
				while ((line = reader.readLine()) != null) {
					if (!line.startsWith("#")) {
						rules.add(line);
					}
				}
			}
			catch (Exception e) {
			}
		}

	}

	public static String getRules() {
		if (null == rules) init();

		if (0 == rules.size()) {
			return ChatColor.GRAY + rulesPrefix
							+ ChatColor.DARK_RED + "Don't be a dick.";
		}

		String message = "";
		for (String line : rules) {
			message += ChatColor.GRAY + rulesPrefix
							+ ChatColor.DARK_RED + line;
		}

		return message;
	}
	
}
