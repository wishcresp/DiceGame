package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Timer;

import view.MainFrame;

public class DicePanelController extends ComponentAdapter implements ActionListener {

	private MainFrame mainFrame;
	/* Timer limits resizing updates to reduce CPU load */
	private Timer timer;
	private final int delay = 125;
	
	public DicePanelController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		timer = new Timer(delay, this);
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		/* Restart the timer if already running (while resizing) to prevent updates */
		if (timer.isRunning())
			timer.restart();
		else
			timer.start();
	}
	
	/* Is triggered when timer manages to reach the delay and scales the dice */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* Scale the dice and stop the timer */
		mainFrame.getDicePanel().scaleDice();
		timer.stop();
	}

}
