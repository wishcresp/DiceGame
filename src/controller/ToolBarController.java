package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.MainFrame;

public class ToolBarController extends KeyAdapter implements ActionListener {

	private GameEngine gameEngine;
	private MainFrame mainFrame;
	
	/* Roll dice delay */
	private final int initialDelay = 0, finalDelay = 400, delayIncrement = 40;
	
	public ToolBarController(MainFrame mainFrame, GameEngine gameEngine) {
		this.mainFrame = mainFrame;
		this.gameEngine = gameEngine;
	}
	
	/* Pressing enter in the bet text field presses the button */
	public void keyPressed (KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				/* Presses the bet button and returns focus to the mainFrame */
				mainFrame.getToolBar().clickBetBtn();
				break;
			case KeyEvent.VK_RIGHT:
				mainFrame.getToolBar().focusBetField();
				break;
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_TAB:
				mainFrame.getSideBar().focusPlayerList();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "placeBet":
				placeBet();
				break;
			case "rollDice":
				rollDice();
				break;
			case "nextPlayer":
				nextPlayer();
				break;
			case "nextRound":
				nextRound();
		}
	}
	
	/* Gets the players bet input, sets it in the gameEngine and updates the GUI */
	private void placeBet() {
		Player player = mainFrame.getSideBar().getSelectedPlayer(gameEngine);
		try {
			/* If string is empty */
			if ("".equals(mainFrame.getToolBar().getBetText()))
				throw new GameControllerException("Error: Please enter a bet.");
			
			/* Checks if player has any points left to continue */
			if (player.getPoints() < 1)
				throw new GameControllerException("Player " + player.getPlayerId() +
						" has no points. Player must be removed to continue.");
			
			int bet = Integer.parseInt(mainFrame.getToolBar().getBetText());
			
			/* Prevents negative bets. Bets of 0 are allowed if players wish
			 * to sit out of the round. */
			if (bet < 0)
				throw new GameControllerException("Error: Bet must be at least 0.");
			
			/* Place the bet */
			if (!gameEngine.placeBet(player, bet))
				throw new GameControllerException("Error: Not enough points for bet.");
			
			/* Updates GUI */
			mainFrame.getToolBar().refresh(mainFrame, gameEngine);
			mainFrame.getStatusBar().refresh(mainFrame, gameEngine);
			mainFrame.getSideBar().refreshGameDetails(mainFrame, gameEngine);
			/* Resets focus to playerList */
			mainFrame.getSideBar().focusPlayerList();
			
		} catch (GameControllerException ex) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(new JFrame(), "Error: Bet must be an Integer.");
		}
	}
	
	/* Rolls the dice for the player or house */
	private void rollDice() {
		
		/* Long roll method called in a new thread */
		new Thread() {
			@Override
			public void run() {
				/* Roll Player */
				if (mainFrame.getSideBar().playerIsSelected(gameEngine.getAllPlayers().size())) {
					
					gameEngine.rollPlayer(mainFrame.getSideBar().getSelectedPlayer(gameEngine),
							initialDelay, finalDelay, delayIncrement);
				
				/* Roll house*/
				} else {
					gameEngine.rollHouse(initialDelay, finalDelay, delayIncrement);	
				}
			}	
		}.start();
		
		/* Updates GUI */
		mainFrame.getStatusBar().clearLabel();
		mainFrame.getToolBar().refresh(mainFrame, gameEngine);
		mainFrame.getToolBar().setRollDiceBtnEnabled(false);
		/* If player is rolling */
		if (mainFrame.getSideBar().playerIsSelected(gameEngine.getAllPlayers().size())) {
			mainFrame.getStatusBar().setLabel(
					"Player " + mainFrame.getSideBar()
					.getSelectedPlayer(gameEngine).getPlayerId()
					+ " is rolling.");
		/* If house is rolling */
		} else {
			mainFrame.getStatusBar().setLabel("House is rolling.");
		}
	}
	
	/* Selects the next player via button as a quick alternative to using the player list */
	private void nextPlayer() {
		int index = mainFrame.getSideBar().getSelectedIndex() + 1;
		/* Updates GUI */
		mainFrame.getSideBar().setSelectedPlayer(index);
		mainFrame.getStatusBar().refresh(mainFrame, gameEngine);
	}
	
	/* Clears each player's bet and roll for the next round */
	private void nextRound() {
		for (Player player : gameEngine.getAllPlayers()) {
			player.setRollResult(null);
			player.placeBet(0);
		}
		
		/* Resets the house result stored in the mainFrame */
		mainFrame.setHouseResult(null);
		/* Updates GUI*/
		mainFrame.getToolBar().setNextRoundBtnEnabled(false);
		mainFrame.getSideBar().setSelectedPlayer(0);
		mainFrame.getSideBar().refreshPlayerList(gameEngine);
		mainFrame.getSideBar().refreshGameDetails(mainFrame, gameEngine);
		mainFrame.getStatusBar().refresh(mainFrame, gameEngine);
	}
}
