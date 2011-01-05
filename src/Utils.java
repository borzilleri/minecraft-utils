
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author jonathan
 */
public class Utils extends Mod {
	public static LinkedHashMap<String,String> commands;
	public static LinkedHashMap<String,String> commandHistory;

	@Override
	public void activate() {
		commands = new LinkedHashMap<String, String>();
		commandHistory = new LinkedHashMap<String, String>();
	}

	protected boolean parseCommand(Player player, String[] tokens) {
		String command = tokens[0];

		if( command.equalsIgnoreCase("!help") ) {
			if( 0 == commands.size() ) buildCommands();

			for(Map.Entry<String,String> modCmds:commands.entrySet() ) {
				player.sendChat(
								String.format("%s%s: %s%s", Color.LightGray, modCmds.getKey(),
								Color.White, modCmds.getValue()));
			}
			return true;
		}
		return false;
	}
	
	protected void buildCommands() {
		for(Mod mod: Server.mods ) {
			if( ! mod.toString().isEmpty() ) {
				commands.put(mod.getClass().getName(), mod.toString());
			}
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

	@Override
	public boolean onEntityDamage(DamageType d, Entity attacker, Entity defender, int damage) {
		if( DamageType.FIRE_TICK == d ) {
			return true;
		}
		return false;
	}
}
