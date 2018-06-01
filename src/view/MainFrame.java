package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import model.interfaces.GameEngine;
import model.interfaces.DicePair;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final MenuBar menuBar;
	private final ToolBar toolBar;
	private final SideBar sideBar;
	private final DicePanel dicePanel;
	private final StatusBar statusBar;
	private final AddPlayerDialog addPlayerDialog;
	
	/* Need to store a copy of the house result (not stored in GameEngine) */
	private DicePair houseResult;
	
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
	public void initializeMainWindow() {
		
		/* Creates menu */
		this.setJMenuBar(menuBar);
		
		/* Creates main layout */
		BorderLayout windowLayout = new BorderLayout();
		this.setLayout(windowLayout);
		
		/* Tool Bar*/
		this.add(toolBar);
		windowLayout.addLayoutComponent(toolBar, BorderLayout.NORTH);
		
		/* Creates main panel and layout */	
		JSplitPane centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		centerPane.add(sideBar, JSplitPane.LEFT);	
		centerPane.add(dicePanel, JSplitPane.RIGHT);
		windowLayout.addLayoutComponent(dicePanel, BorderLayout.CENTER);
		
		/* Status Bar */
		this.add(statusBar);
		windowLayout.addLayoutComponent(statusBar, BorderLayout.SOUTH);
		
		/* Disables focus */
		this.setFocusable(false);
		
		/* Setup and add panes to main window */
		this.add(centerPane);
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
	
	/* House Result is stored in MainFrame */
	public DicePair getHouseResult() {
		return this.houseResult;
	}
	
	public void setHouseResult(DicePair result) {
		this.houseResult = result;
	}

}
