package view.observers;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.observers.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton example implementation of GameEngineCallback showing Java logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.observers.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback
{
	private Logger logger = Logger.getLogger("assignment1");

	public GameEngineCallbackImpl()
	{
		// FINE shows rolling output, INFO only shows result
		logger.setLevel(Level.FINE);
	}

	@Override
	public void intermediateResult(Player player, DicePair dicePair, GameEngine gameEngine)
	{
		String toPrint = player.toString() + "ROLLING " + dicePair.toString();
		
		// final results logged at Level.FINE
		logger.log(Level.FINE, toPrint);
	}

	@Override
	public void result(Player player, DicePair result, GameEngine gameEngine)
	{		
		String toPrint = player.toString() + "*RESULT* " + result.toString();
		// final results logged at Level.INFO
		logger.log(Level.INFO, toPrint);
	}
	
	@Override
	public void intermediateHouseResult(DicePair dicePair,
			GameEngine gameEngine) {
		String toPrint = "House: ROLLING " + dicePair.toString();
		
		// final results logged at Level.FINE
		logger.log(Level.FINE, toPrint);
	}

	@Override
	public void houseResult(DicePair result, GameEngine gameEngine) {
		String toPrint = "House: *RESULT* " + result.toString();
		
		// final results logged at Level.FINE
		logger.log(Level.INFO, toPrint);
		
		/* Logs final scores after house has rolled */
		playerPoints(gameEngine);
	}
	
	/* Logs final scores */
	private void playerPoints(GameEngine gameEngine) {
		
		for (Player player : gameEngine.getAllPlayers()) {
			String toPrint =
					"Player id: " + player.getPlayerId() +
					", Name: " + player.getPlayerName() + 
					", Points: " + player.getPoints();
			logger.log(Level.INFO, toPrint);
		}
	}
}
