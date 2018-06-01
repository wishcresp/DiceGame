package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import view.ToolBar;

public class IconAnimationController implements ActionListener {
	
	private ToolBar toolBar;
	private int imageVal;
	private Timer timer;
	private final int delay = 125;
	
	/* Creates a timer to refresh the */
	public IconAnimationController(ToolBar toolBar) {
		this.toolBar = toolBar;
		imageVal = 0;
		timer = new Timer(delay, this);
		timer.start();
	}
	
	/* Timer manages GUI update for the rotating icon on the toolbar.
	 * Loops through images */
	@Override
	public void actionPerformed(ActionEvent e) {
		toolBar.setDiceImage(imageVal);
		imageVal = imageVal < 3 ? ++imageVal : 0;
	}

}
