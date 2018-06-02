package view.observers;

import javax.swing.SwingUtilities;
import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.MainFrame;
import view.observers.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback {
	
	private MainFrame mainFrame;
	
	public GameEngineCallbackGUI(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	/* All methods return to the event dispatch thread */
	@Override
	public void intermediateResult(Player player, DicePair dicePair, GameEngine gameEngine) {
		
		SwingUtilities.invokeLater(() -> {
			/* If a player is selected (not house) */
			if (mainFrame.getSideBar().playerIsSelected(gameEngine.getAllPlayers().size())) {
				/* If the player selected is the one rolling */
				if (player.equals(mainFrame.getSideBar().getSelectedPlayer(gameEngine))) {
					mainFrame.getDicePanel().refresh(mainFrame, dicePair);
				}
			}
		});
			
	}

	@Override
	public void result(Player player, DicePair result, GameEngine gameEngine) {
		
		SwingUtilities.invokeLater(() -> {
			/* If a player is selected (not house) */
			if (mainFrame.getSideBar().playerIsSelected(gameEngine.getAllPlayers().size())) {
				/* If the player selected is the one rolling */
				if (player.equals(mainFrame.getSideBar().getSelectedPlayer(gameEngine))) {
					/* Refresh game details, not player list*/
					mainFrame.getSideBar().refreshGameDetails(mainFrame, gameEngine);
					mainFrame.getDicePanel().refresh(mainFrame, result);
					mainFrame.getToolBar().refresh(mainFrame, gameEngine);
					mainFrame.getStatusBar().refresh(mainFrame, gameEngine);
				/* If a different player is selected */
				} else {
					/* Append text to status bar */
					mainFrame.getStatusBar().appendLabel(
							"Player " + player.getPlayerId() + " has rolled.");
				}
			/* If house is selected, refresh toolbar in case all players have rolled*/
			} else {
				mainFrame.getToolBar().refresh(mainFrame, gameEngine);
				mainFrame.getStatusBar().refresh(mainFrame, gameEngine);
			}
		});
		
	}

	@Override
	public void intermediateHouseResult(DicePair dicePair, GameEngine gameEngine) {
		
		SwingUtilities.invokeLater(() -> {
			if (!mainFrame.getSideBar().playerIsSelected(gameEngine.getAllPlayers().size())) {
				mainFrame.getDicePanel().refresh(mainFrame, dicePair);
			}
		});
		
	}
	
	@Override
	public void houseResult(DicePair result, GameEngine gameEngine) {
		
		SwingUtilities.invokeLater(() -> {
			/* Ensures no additional players have been added before reporting
			 * final results */
			if (mainFrame.getSideBar().allPlayersRolled(gameEngine)) {
				
				/* Update GUI is house is selected */
				if (!mainFrame.getSideBar().playerIsSelected(
						gameEngine.getAllPlayers().size())) {
					
					mainFrame.getSideBar().refreshGameDetails(mainFrame, gameEngine);
					mainFrame.getDicePanel().refresh(mainFrame, result);
					mainFrame.getStatusBar().refresh(mainFrame, gameEngine);
				}
			}
			/* Updates toolbar on any screen */
			mainFrame.getToolBar().refresh(mainFrame, gameEngine);
		});
		
	}
	
}
