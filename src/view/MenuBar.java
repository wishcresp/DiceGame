package view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import controller.MenuBarController;
import model.interfaces.GameEngine;

public class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	
	private JMenu file, players, help;
	private JMenuItem savePlayers, loadPlayers, exit, addPlayer, removePlayer,
			removeAllPlayers, rules, shortcuts, about;
	
	public MenuBar() {
		
		/* Menu's*/
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
				KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		savePlayers.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		addPlayer.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		removePlayer.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		removeAllPlayers.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_R, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		rules.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		
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
		
		/* Disable focus */
		this.setFocusable(false);
		
		this.add(file);
		this.add(players);
		this.add(help);
	}
	
	public void addListeners(MainFrame mainFrame, GameEngine gameEngine) {		
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
