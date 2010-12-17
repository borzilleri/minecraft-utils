import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

/**
 *
 * @author jonathan
 */
public class MOTD extends Mod {
	protected String motdFileName = "motd.txt";
	protected LinkedList<String> motd;

	protected boolean parseCommand(Player player, String[] tokens) {
		String command = tokens[0];

		if( command.equalsIgnoreCase("!motd") ) {
			sendMotd(player);
			return true;
		}
		return false;
	}
	
	protected void sendMotd(Player player) {
		if( 0 == motd.size() ) {
			player.sendChat("[motd] No Message of the day!", ColorEnum.LightPurple);
			return;
		}
		
		for(String line: motd) {
			player.sendChat("[motd] "+line, ColorEnum.LightPurple);
		}
	}


	@Override
	public boolean onPlayerChat(Player player, String chat) {
		String[] tokens = chat.split(" ");
		return parseCommand(player, tokens);
	}
	@Override
	public boolean onPlayerCommand(Player player, String[] tokens) {
		return parseCommand(player, tokens);
	}
	
	@Override
	public void onPlayerLogin(Player player) {
		sendMotd(player);
	}

	@Override
	public String toString() {
		return "!motd";
	}

	@Override
	public void activate() {
		motd = new LinkedList<String>();
		loadMotd();
	}

	protected void loadMotd() {
		File motdFile = new File(motdFileName);
		if( motdFile.exists() ) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(motdFile));
				String line;
				while( (line = reader.readLine()) != null ) {
					if( ! line.startsWith("#") ) {
						motd.add(line);
					}
				}
			}
			catch( Exception e ) {
			}
		}
	}
}
