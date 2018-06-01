package loader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import loader.interfaces.GameLoader;
import model.SimplePlayer;
import model.interfaces.Player;

public class GameLoaderText implements GameLoader {
	
	private final String delimiter = ",";
	private final String FOFMessage = "File not found. Please check 'players.txt' exists.";
	private final String IOMessage = "IO Exception. File may be locked or read/write protected. "
			+ "Try restarting your computer.";
	private final String NFMessage = "Error: Formatting of 'players.txt' is 'id,name,points'. "
			+ "Each player should be on a new line. Points must be an integer.";
	
	@Override
	public Collection<Player> loadAllPlayers(String path) throws GameLoaderException {
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String line;
			
			Collection<Player> players = new ArrayList<>();
			Player player;
			
			while ((line = in.readLine()) != null) {
				player = createPlayer(line);
				players.add(player);
			}
			in.close();
			return players;
			
		} catch (FileNotFoundException e) {
			throw new GameLoaderException(FOFMessage);
		} catch (IOException e) {
			throw new GameLoaderException(IOMessage);
		} catch (NumberFormatException e) {
			throw new GameLoaderException(NFMessage);
		}
	}
	
	private Player createPlayer(String line) throws NumberFormatException, GameLoaderException {
		Player player;
		String tokens[] = line.split(delimiter);
		if (tokens.length > 3)
			throw new GameLoaderException(NFMessage);
		player = new SimplePlayer(tokens[0], tokens[1], Integer.parseInt(tokens[2]));
		return player;
	}

	@Override
	public void saveAllPlayers(String path, Collection<Player> players) throws GameLoaderException {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path), 'w');
			for	(Player player : players) {
				writePlayer(out, player);
			}
			out.close();
		} catch (IOException e) {
			throw new GameLoaderException(IOMessage);
		}
		
	}

	@Override
	public void appendPlayer(String path, Player player) throws GameLoaderException {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path), 'a');
			writePlayer(out, player);
		} catch (IOException e) {
			throw new GameLoaderException(IOMessage);
		}
	}
	
	private void writePlayer(BufferedWriter out, Player player) throws IOException {
		out.write(String.format("%s,%s,%d\n",
				player.getPlayerId(),
				player.getPlayerName(),
				player.getPoints()));
	}
}
