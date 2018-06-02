package view;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import controller.MenuBarController;
import model.interfaces.GameEngine;

class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	
	private JMenu file, players, help;
	private JMenuItem savePlayers, loadPlayers, exit, addPlayer, removePlayer,
			removeAllPlayers, rules, shortcuts, about;
	
	MenuBar() {
		
		/* Menus */
		file = new JMenu("File");
		players = new JMenu("Manage Players");
		help = new JMenu("Help");
		
		/* Menu items */
		loadPlayers = new JMenuItem("Open Players");
		loadPlayers.setActionCommand("loadPlayers");
		savePlayers = new JMenuItem("Save Players");
		savePlayers.setActionCommand("savePlayers");
		addPlayer = new JMenuItem("Add Player");
		addPlayer.setActionCommand("addPlayer");
		removePlayer = new JMenuItem("Remove Selected Player");
		removePlayer.setActionCommand("removePlayer");
		removeAllPlayers = new JMenuItem("Remove All Players");
		removeAllPlayers.setActionCommand("removeAllPlayers");

		exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		rules = new JMenuItem("Rules");
		rules.setActionCommand("rules");
		shortcuts = new JMenuItem("Shortcuts");
		shortcuts.setActionCommand("shortcuts");
		about = new JMenuItem("About");
		about.setActionCommand("about");
		
		/* Menu keyboard shortcuts */
		loadPlayers.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		savePlayers.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		addPlayer.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		removePlayer.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		removeAllPlayers.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_R, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		rules.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		
		/* Construct menu */
		file.add(loadPlayers);
		file.add(savePlayers);
		file.add(exit);
		players.add(addPlayer);
		players.add(removePlayer);
		players.add(removeAllPlayers);
		help.add(rules);
		help.add(shortcuts);
		help.add(about);
		this.add(file);
		this.add(players);
		this.add(help);
		
		/* Disable focus */
		this.setFocusable(false);
	}
	
	void addListeners(MainFrame mainFrame, GameEngine gameEngine) {
		MenuBarController listener = new MenuBarController(mainFrame, gameEngine);
		savePlayers.addActionListener(listener);
		loadPlayers.addActionListener(listener);
		addPlayer.addActionListener(listener);
		removePlayer.addActionListener(listener);
		removeAllPlayers.addActionListener(listener);
		exit.addActionListener(listener);
		rules.addActionListener(listener);
		shortcuts.addActionListener(listener);
		about.addActionListener(listener);
	}
}
