package client;

import javax.swing.SwingUtilities;
import model.GameEngineImpl;
import model.interfaces.GameEngine;
import view.MainFrame;
import view.observers.GameEngineCallbackGUI;
import view.observers.GameEngineCallbackImpl;

public class DiceGameClient {
	
	public static void main(String[] args) {
		
		/* Model */
		final GameEngine gameEngine = new GameEngineImpl();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				/* View */
				final MainFrame mainFrame = new MainFrame();
				/* Controllers */
				mainFrame.addListeners(gameEngine);
				/* GUI Observer/Callback */
				gameEngine.addGameEngineCallback(new GameEngineCallbackGUI(mainFrame));
			}			
		});
		
		/* Observer/Callback */
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
	}
}
