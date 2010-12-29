
/**
 *
 * @author jonathan
 */
public class PlayerList extends Mod {

	public boolean parseCommand(Player player, String[] tokens) {
		String command = tokens[0];
		if( command.equalsIgnoreCase("!who") ) {
			listPlayers(player, player.isAdmin());
			return true;
		}
		return false;
	}

	protected void listPlayers(Player player, boolean includeInvisible) {
		player.sendChat(buildPlayerListMessage("Online Players: "));
	}

	protected String buildPlayerListMessage(String label) {
		String playerList = Color.Gray.getFormat() + label;

		for( Player thisPlayer:Server.getPlayers() ) {
			playerList += Color.LightBlue.getFormat() + thisPlayer.getName()
							+ Color.Gray.getFormat() + ", ";
		}

		return playerList;
	}

	@Override
	public String toString() {
		return "!who";
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
