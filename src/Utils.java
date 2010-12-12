
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author jonathan
 */
public class Utils extends Mod {
	public static LinkedHashMap<String,String> commands;

	@Override
	public void activate() {
		commands = new LinkedHashMap<String, String>();
	}

	protected boolean parseCommand(Player player, String[] tokens) {
		String command = tokens[0];

		if( command.equalsIgnoreCase("!help") ) {
			if( 0 == commands.size() ) buildCommands();

			for(Map.Entry<String,String> modCmds:commands.entrySet() ) {
				player.sendChat(
								String.format("%s: %s", modCmds.getKey(), modCmds.getValue()));
			}
			return true;
		}
		return false;
	}

	protected void buildCommands() {
		for(String addon:Server.getActiveAddons()) {
			String[] mod = addon.split("\\.");
			String theseCommands = Server.getAddon(addon).getMod().toString();

			if( theseCommands.isEmpty() ) continue;

			if( commands.containsKey(mod[0]) ) {
				theseCommands = String.format("%s, %s",
								commands.get(mod[0]), theseCommands);
			}
			commands.put(mod[0], theseCommands);
		}
	}

	@Override
	public String toString() {
		return "";
	}
	
	@Override
	public boolean onPlayerChat(Player player, String command) {
		String[] tokens = command.split(" ");
		return parseCommand(player, tokens);
	}
	@Override
	public boolean onPlayerCommand(Player player, String[] tokens) {
		return parseCommand(player, tokens);
	}

}
