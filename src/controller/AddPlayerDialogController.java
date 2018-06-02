package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import view.MainFrame;

public class AddPlayerDialogController implements ActionListener {
	
	private GameEngine gameEngine;
	private MainFrame mainFrame;
	
	public AddPlayerDialogController(MainFrame mainFrame, GameEngine gameEngine) {
		this.mainFrame = mainFrame;
		this.gameEngine = gameEngine;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("addPlayer"))
			loadPlayerFromGUI();
	}

	/* Loads player from the 'Add player dialog' gui frame and adds it to the gameEngine */
	private void loadPlayerFromGUI() {
		try {
			/* New id = the number of players + 1
			 * Testing for duplicate ID's was not implemented since
			 * the game engine also does not reject them */
			String id = String.valueOf(gameEngine.getAllPlayers().size() + 1);
			String name = mainFrame.getAddPlayerDialog().getNameFieldText();
			int points = Integer.parseInt(mainFrame.getAddPlayerDialog().getPointFieldText());
			
			/* Checks for empty names */
			if ("".equals(name))
				throw new GameControllerException("Error: Please enter a name.");
			
			/* Ensures entered points is at least 1 */
			if (points < 1)
				throw new GameControllerException("Points must be greater than 0.");
			
			/* Adds player to the gameEngine */
			gameEngine.addPlayer(new SimplePlayer(id, name, points));
			
			/* Updates the GUI */
			mainFrame.getAddPlayerDialog().clearPlayerInput();
			mainFrame.getAddPlayerDialog().setVisible(false);
			
			/* Updates only player list. Details updates anyway on first player 
			 * load due to selection change */
			mainFrame.getSideBar().refreshPlayerList(gameEngine);
			
		} catch (GameControllerException ex) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(new JFrame(), "Error: Points must be an Integer.");
		}
	}
	
}
