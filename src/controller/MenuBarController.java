package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import loader.GameLoaderException;
import loader.GameLoaderText;
import loader.interfaces.GameLoader;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.MainFrame;

public class MenuBarController implements ActionListener {

	private GameEngine gameEngine;
	private MainFrame mainFrame;
	private GameLoader loader;
	
	private static final String filePath = "players.txt";
	
	public MenuBarController(MainFrame mainFrame, GameEngine gameEngine) {
		this.mainFrame = mainFrame;
		this.gameEngine = gameEngine;
		loader = new GameLoaderText();
	}

	/* When the user chooses an option from the menu */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "loadPlayers":
				removeAllPlayers();
				loadFile();
				break;
			case "savePlayers":
				saveFile();
				break;
			case "addPlayer":
				addPlayer();
				break;
			case "removePlayer":
				removePlayer();
				break;	
			case "removeAllPlayers":
				removeAllPlayers();
				break;
			case "exit":
				exit();
				break;
			case "rules":
				openRules();
				break;
			case "shortcuts":
				openShortcuts();
				break;
			case "about":
				openAbout();
		}	
	}
	
	/* Loads every player in the file */
	private void loadFile() {
		try {
			/* For each player that was loaded from file */
			for (Player player : loader.loadAllPlayers(filePath)) {
				/* Get the details and add the player */
				String id = player.getPlayerId();
				String name = player.getPlayerName();
				int points = player.getPoints();
				gameEngine.addPlayer(new SimplePlayer(id, name, points));
			}
			
		} catch (GameLoaderException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
		}
		
		/* Update the GUI to show the new players */
		mainFrame.getSideBar().refreshPlayerList(gameEngine);
		mainFrame.getToolBar().refresh(mainFrame, gameEngine);
	}
	
	/* Saves a file of players */
	private void saveFile() {
		try {
			/* Only save if players exist */
			if (gameEngine.getAllPlayers().size() > 0) {
				
				loader.saveAllPlayers(filePath, gameEngine.getAllPlayers());
				mainFrame.getStatusBar().setLabel("File successfully saved.");

			} else {
				throw new GameControllerException("There are no players in the system. "
						+ "File was not saved.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
		}
	}
	
	/* Removes the currently selected player in the player list component */
	private void removePlayer() {
		/* If players exist and the selection is not the house */
		if (gameEngine.getAllPlayers().size() > 0
		&& gameEngine.getAllPlayers().size() != 
		mainFrame.getSideBar().getSelectedIndex()) {
			/* Remove the player and refresh the GUI */
			gameEngine.removePlayer(mainFrame.getSideBar().getSelectedPlayer(gameEngine));
			mainFrame.getSideBar().refreshPlayerList(gameEngine);
			mainFrame.getToolBar().refresh(mainFrame, gameEngine);			
		}
	}
	
	/* Shows the add player dialog box for input */
	private void addPlayer() {
		mainFrame.setHouseResult(null);
		mainFrame.getAddPlayerDialog().centerDialogToMainWindow(mainFrame);
		mainFrame.getAddPlayerDialog().setVisible(true);
	}
	
	/* Removes all players in the gameEngine */
	private void removeAllPlayers() {		
		/* Adds players to a new collection to remove from gameEngine. */
		for (Player player: new ArrayList<>(gameEngine.getAllPlayers())) {
			gameEngine.removePlayer(player);
		}
		/* Resets the house result and updates GUI */
		mainFrame.setHouseResult(null);
		mainFrame.getAddPlayerDialog().clearPlayerInput();
		mainFrame.getSideBar().refreshPlayerList(gameEngine);
		mainFrame.getToolBar().refresh(mainFrame, gameEngine);	
	}	
	
	/* Creates a dialog box with rule information */
	private void openRules() {
		String message = "Rules\n"
				+ " - Players must bet before they can roll\n"
				+ " - Players can bet then roll in any order and at the same time\n"
				+ " - All players must roll before the house\n"
				+ " - Negative bets are not allowed\n"
				+ " - Players can not continue if their points reach 0\n"
				+ " - If a player has points left, they can place a bet of 0 to sit out\n";
		JOptionPane.showMessageDialog(new JFrame(), message, "Rules",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/* Creates a dialog box with keyboard shortcut information */
	private void openShortcuts() {
		String message = "Keyboard Shortcuts\n"
				+ " - UP/DOWN to switch players\n"
				+ " - RIGHT/TAB selects the bid entry field\n"
				+ " - ESCAPE/TAB returns to player selection\n"
				+ " - ENTER presses the currently active button";
		JOptionPane.showMessageDialog(new JFrame(), message, "Shortcuts",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/* Creates a dialog containing 'about' information */
	private void openAbout() {
		String message = "SADI Assigment 2 - Dice Game. 2018. Made by Sean Martin.";
		JOptionPane.showMessageDialog(new JFrame(), message, "About", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/* Exit with save option */
	private void exit() {
		/* If players exist, offer to save file on exit */
		if (gameEngine.getAllPlayers().size() > 0) {
			String message = "Would you like to save players?";
			if(JOptionPane.showConfirmDialog(new JFrame(), message, "EXIT",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				
				saveFile();
			}
		}
		/* Exit */
		System.exit(0);
	}
	
}
