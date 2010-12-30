import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

/**
 *
 * @author jonathan
 */
public class Rules extends Mod {
	protected String rulesFileName = "rules.txt";
	protected LinkedList<String> rules;

	protected boolean parseCommand(Player player, String[] tokens) {
		String command = tokens[0];

		if( command.equalsIgnoreCase("!rules") ) {
			sendRules(player);
			return true;
		}
		else if( command.equalsIgnoreCase("!help") ) {
			player.sendChat(Color.LightGray.getFormat() + this.getClass().getName() + ": " +
							Color.White.getFormat() + toString() );
			return true;
		}

		return false;
	}

	protected void sendRules(Player player) {
		if( 0 == rules.size() ) {
			player.sendChat("[rules] Don't be a dick.", Color.Rose);
			return;
		}

		for(String line: rules) {
			player.sendChat("[rules] "+line, Color.Rose);
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
	public String toString() {
		return "!rules";
	}

	@Override
	public void activate() {
		rules = new LinkedList<String>();
		loadRules();
	}

	protected void loadRules() {
		File rulesFile = new File(rulesFileName);
		if( rulesFile.exists() ) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(rulesFile));
				String line;
				while( (line = reader.readLine()) != null ) {
					if( ! line.startsWith("#") ) {
						rules.add(line);
					}
				}
			}
			catch( Exception e ) {
			}
		}
	}
}
