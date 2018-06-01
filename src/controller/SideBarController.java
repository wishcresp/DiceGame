package controller;

import view.MainFrame;
import model.interfaces.GameEngine;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SideBarController extends KeyAdapter implements ListSelectionListener {
	
	private GameEngine gameEngine;
	private MainFrame mainFrame;

	public SideBarController(MainFrame mainFrame, GameEngine gameEngine) {
		this.mainFrame = mainFrame;
		this.gameEngine = gameEngine;
	}
	
	/* Focus the bet field */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_TAB:
				mainFrame.getToolBar().focusBetField();
		}			
	}	
	
	/* Updates the GUI when the user changes the selection in the player list */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		/* Ignores while value is adjusting. Ensures called only once */
		if (!e.getValueIsAdjusting()) {
			mainFrame.getSideBar().refreshGameDetails(mainFrame, gameEngine);
			mainFrame.getToolBar().refresh(mainFrame, gameEngine);
			mainFrame.getStatusBar().refresh(mainFrame, gameEngine);
			
			/* If player is selected, draw player dice pair */
			if (mainFrame.getSideBar().playerIsSelected(gameEngine.getAllPlayers().size())) {
				mainFrame.getDicePanel().refresh(mainFrame, mainFrame.getSideBar()
						.getSelectedPlayer(gameEngine).getRollResult());
			/* Else draw houses dice pair*/
			} else {
				mainFrame.getDicePanel().refresh(mainFrame, mainFrame.getHouseResult());
			}
		}
	}
	
}
