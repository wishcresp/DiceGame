package view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import controller.SideBarController;
import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class SideBar extends JSplitPane {

	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<String> model;
	private JList<String> playerList;
	private JTextArea gameDetails;
	
	public SideBar() {
		
		/* Initialize the SplitPane */
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.setMinimumSize(new Dimension(175,0));
		
		/* Create the player selection list */
		model = new DefaultListModel<>();
		playerList = new JList<>(model);
		playerList.setVisibleRowCount(5);
		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerList.setFont(new Font(null, Font.BOLD, 14));
		playerList.requestFocus();
		playerList.setFocusTraversalKeysEnabled(false);
		
		/* Details pane cannot be edited or focused on */
		gameDetails = new JTextArea();
		gameDetails.setEditable(false);
		gameDetails.setFocusable(false);
		gameDetails.setFont(new Font(null, Font.PLAIN, 14));
		
		/* Disable focus */
		this.setFocusable(false);
		
		this.add(playerList, JSplitPane.TOP);
		this.add(new JScrollPane(gameDetails), JSplitPane.BOTTOM);
		
		/* Initial Element in Player List */
		model.addElement("House");
		playerList.setSelectedIndex(0);
	}
	
	/* Called from MainPanel which is called from main after the MainFrame is constructed */
	public void addListeners(MainFrame mainFrame, GameEngine gameEngine) {
		SideBarController listener = new SideBarController(mainFrame, gameEngine);
		playerList.addListSelectionListener(listener);
		playerList.addKeyListener(listener);
	}
	
	/* Updates player list in side bar */
	public void refreshPlayerList(GameEngine gameEngine) {
		model.removeAllElements();
		addPlayersToList(gameEngine);
		setSelectedPlayer(0);
		resetToPreferredSizes();
	}
	
	/* Updates game details panel in side bar */
	public void refreshGameDetails(MainFrame mainFrame, GameEngine gameEngine) {
		clearGameDetails();
		/* If player is selected */
		if (playerIsSelected(gameEngine.getAllPlayers().size())) {
			printPlayerDetails(mainFrame, getSelectedPlayer(gameEngine));
		/* If house is selected */
		} else {
			printHouseDetails(mainFrame);
			/* If house has rolled */
			if (mainFrame.getHouseResult() != null) {
				gameDetails.append("\n");
				printResults(mainFrame, gameEngine);
			}
		}
	}
	
	private void clearGameDetails() {
		gameDetails.setText("");
	}
	
	public void setSelectedPlayer(int index) {
		playerList.setSelectedIndex(index);
	}
	
	public int getSelectedIndex() {
		return playerList.getSelectedIndex();
	}
	
	/* Player is selected when the last index is not selected (house)
	 * and when there is at least one player (not -1) */
	public boolean playerIsSelected(int playerCount) {
		return playerList.getSelectedIndex() != playerCount
				&& playerList.getSelectedIndex() != -1;
	}
	
	/* Converts player collection to an ArrayList and gets the player at the index
	 * of the selected JList element. */
	public Player getSelectedPlayer(GameEngine gameEngine) {
		return new ArrayList<>(gameEngine.getAllPlayers())
				.get(playerList.getSelectedIndex());
	}
	
	/* Checks if all players have rolled */
	public boolean allPlayersRolled(GameEngine gameEngine) {
		for (Player search : gameEngine.getAllPlayers()) {
			if (search.getRollResult() == null) {
				return false;
			}
		}
		return true;
	}
	
	/* Prints player details to text area in UI */
	private void printPlayerDetails(MainFrame mainFrame, Player player) {
		clearGameDetails();
		String [] lines = {
			"Name: " + player.getPlayerName(),
			"ID: " + player.getPlayerId(),
			"Points: " + player.getPoints()
		};
		writeToGameDetails(lines);	
		
		/* If player has bet */
		if (player.getBet() != 0) {
			lines = new String[] {"Bet: " + player.getBet()};
			writeToGameDetails(lines);
		}
			
		/* If player has rolled */
		if (player.getRollResult() != null) {
			DicePair rollResult = player.getRollResult();
			lines = new String[] {
				"Dice 1: " + rollResult.getDice1(),
				"Dice 2: " + rollResult.getDice2(),
				"Total: " + mainFrame.getDicePanel().getDiceTotal(rollResult)};
			writeToGameDetails(lines);
		}
			
	}
	
	/* Prints house details to text area in UI */
	private void printHouseDetails(MainFrame mainFrame) {
		clearGameDetails();
		String [] lines = {
			"Name: House"
		};
		writeToGameDetails(lines);
		
		if (mainFrame.getHouseResult() != null) {
			DicePair houseResult = mainFrame.getHouseResult();
			lines = new String[] {
				"Dice 1: " + houseResult.getDice1(),
				"Dice 2: " + houseResult.getDice2(),
				"Total: " + mainFrame.getDicePanel().getDiceTotal(houseResult)};
			writeToGameDetails(lines);
		}
	}
	
	/* Prints results after house has rolled */
	private void printResults(MainFrame mainFrame, GameEngine gameEngine) {
		
		String [] lines;
		
		for (Player player : gameEngine.getAllPlayers()) {
			String result;
			int playerRoll = mainFrame.getDicePanel().getDiceTotal(player.getRollResult());
			int houseRoll = mainFrame.getDicePanel().getDiceTotal(mainFrame.getHouseResult());
			
			/* Prints player result */
			result = playerRoll > houseRoll ? "Wins"
					: playerRoll < houseRoll ? "Loses"
					: "Draws";
			
			lines = new String[] {
				player.toString() 
				+ "\n" + result 
				+ "\nPoints: " + player.getPoints() + "\n"};
			
			writeToGameDetails(lines);
		}
	}
	
	/* Write string in array to Game Details */
	private void writeToGameDetails(String [] lines) {
		for (String line : lines) {
			gameDetails.append(line + "\n");
		}
	}
	
	/* Appends players to player selection list */
	private void addPlayersToList(GameEngine gameEngine) {
		for (Player player : gameEngine.getAllPlayers()) {
			String details = "Player " + player.getPlayerId() 
					+ " - " + player.getPlayerName();
			/* Adds formatted string to JList */
			model.addElement(details);		
		}
		/* Adds the house selection */
		model.addElement("House");
	}
	
	/* Set focus on player JList */
	public void focusPlayerList() {
		playerList.requestFocus();
	}
	
}
