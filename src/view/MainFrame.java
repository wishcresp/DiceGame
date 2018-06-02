package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import model.interfaces.GameEngine;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final MenuBar menuBar;
	private final ToolBar toolBar;
	private final SideBar sideBar;
	private final DicePanel dicePanel;
	private final StatusBar statusBar;
	private final AddPlayerDialog addPlayerDialog;
	
	public MainFrame() {
		menuBar = new MenuBar();
		toolBar = new ToolBar();
		sideBar = new SideBar();
		dicePanel = new DicePanel();
		statusBar = new StatusBar();
		addPlayerDialog = new AddPlayerDialog();
		initializeMainWindow();
		
		/* Initially set the dice scale */
		dicePanel.scaleDice();
		/* Initially focus player list */
		sideBar.focusPlayerList();
	}
	
	/* Initialises all frames, panels and components of the GUI */
	private void initializeMainWindow() {
		
		/* Creates menu */
		this.setJMenuBar(menuBar);
		
		/* Creates main layout */
		this.setLayout(new BorderLayout());
		
		/* Tool Bar*/
		this.add(toolBar, BorderLayout.NORTH);
		
		/* Creates main panel and layout */	
		JSplitPane centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		centerPane.add(sideBar, JSplitPane.LEFT);	
		centerPane.add(dicePanel, JSplitPane.RIGHT);
		this.add(centerPane, BorderLayout.CENTER);
		
		/* Status Bar */
		this.add(statusBar, BorderLayout.SOUTH);
		
		/* Disables focus */
		this.setFocusable(false);
		
		/* Setup and add panes to main window */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 500);
		
		/* Centers the JFrame */
		this.setLocationRelativeTo(null);
		this.setTitle("DiceGame");
		this.setVisible(true);
	}
	
	/* Passes gameEngine and mainFrame references to each frame/panel and adds listeners.
	 * Called by main in client */
	public void addListeners(GameEngine gameEngine) {
		menuBar.addListeners(this, gameEngine);
		toolBar.addListeners(this, gameEngine);
		sideBar.addListeners(this, gameEngine);
		dicePanel.addListeners(this);
		addPlayerDialog.addListeners(this, gameEngine);
	}
	
	/* Getters for main panels */	
	public ToolBar getToolBar() {
		return this.toolBar;
	}

	public StatusBar getStatusBar() {
		return this.statusBar;
	}

	public DicePanel getDicePanel() {
		return this.dicePanel;
	}
	
	public SideBar getSideBar() {
		return this.sideBar;
	}
	
	public AddPlayerDialog getAddPlayerDialog() {
		return this.addPlayerDialog;
	}

}
