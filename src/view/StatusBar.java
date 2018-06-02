package view;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class StatusBar extends JToolBar {
	
	private static final long serialVersionUID = 1L;

	private JLabel message;
	private final String defaultLabel = "Please add players from the menu to begin.";
	private final String delimiter = "::";
	private String [] messages = {
			"Place a bet for Player ::.",
			"Player :: is ready to roll.",
			"Player :: has rolled.",
			"Waiting for players to roll.",
			"House is ready to roll.",
			"The round has ended. Bets were resolved. Ready to start new round."
	};
	
	StatusBar() {
		message = new JLabel(defaultLabel, JLabel.CENTER);
		message.setFont(new Font(null, Font.ITALIC + Font.BOLD, 14));
		this.add(message);
		
		/* Layout to center label */
		setLayout(new GridLayout(1, 1));
		
		/* Disable focus */
		this.setFocusable(false);
	}
	
	/* Updates the status bar and prints the appropriate message */
	public void refresh(MainFrame mainFrame, GameEngine gameEngine) {
		/* Clears previous message */
		clearLabel();
		/* Player collection is empty */
		if (gameEngine.getAllPlayers().size() == 0) {
			setLabel(defaultLabel);
		/* Player is selected */
		} else if (mainFrame.getSideBar().playerIsSelected(gameEngine.getAllPlayers().size())) {
			Player player = mainFrame.getSideBar().getSelectedPlayer(gameEngine);
			int message =
					/* If player has not bet */
					player.getBet() == 0 ? 0 :
					/* If player has not rolled */
					player.getRollResult() == null ? 1 :
					/* If player has rolled */
					2;
			setLabel(messages[message], player);
		/* House is selected */
		} else {
			/* If all players have rolled */
			int message = !mainFrame.getSideBar().allPlayersRolled(gameEngine) ? 3
					/* If house has not rolled */
					: gameEngine.getHouseResult() == null ? 4
					/* If house has rolled */
					: 5;
			setLabel(messages[message]);
		}
	}

	/* Appends label to status bar with player name */
	private void setLabel(String s, Player player) {
		String [] tokens = s.split(delimiter);
		message.setText(tokens[0] + player.getPlayerId() + tokens[1]);
	}
	
	public void setLabel(String s) {
		message.setText(s);
	}
	
	public void appendLabel(String s) {
		message.setText(message.getText() + " " + s);
	}
	
	public void clearLabel() {
		message.setText("");
	}
	
}
