
import java.util.ArrayList;
import java.util.LinkedHashSet;

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
		player.sendChat(
			buildPlayerListMessage(Server.getVisiblePlayers(),"Online Players: "));
	}

	protected MessageBlock[] buildPlayerListMessage(ArrayList players, String label) {
		ArrayList<MessageBlock> playerList = new ArrayList<MessageBlock>();

		playerList.add(new MessageBlock(ColorEnum.Gray, label));
		for( Player thisPlayer:Server.getVisiblePlayers() ) {
			playerList.add(new MessageBlock(ColorEnum.LightBlue, thisPlayer.getName()));
		}
		return playerList.toArray(new MessageBlock[1]);
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
