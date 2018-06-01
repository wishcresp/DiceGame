package loader.interfaces;

import java.util.Collection;

import loader.GameLoaderException;
import model.interfaces.Player;

public interface GameLoader {
	
	// load all players from a file and return them as a Collection<Player>
	public abstract Collection<Player> loadAllPlayers(String path) throws GameLoaderException;
	
	// save all players from a Collection<Player> to a file
	public abstract void saveAllPlayers(String path, Collection<Player>players) throws GameLoaderException;
	
	// append a single player to an existing file
	public abstract void appendPlayer(String path, Player player) throws GameLoaderException;
}
