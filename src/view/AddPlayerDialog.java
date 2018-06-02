package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.AddPlayerDialogController;
import model.interfaces.GameEngine;

public class AddPlayerDialog extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField nameField;
	private JTextField pointsField;
	private JButton addPlayerBtn;
	
	private final String defaultPoints = "1000";
	
	AddPlayerDialog() {
		/* Main Panel */
		JPanel panel = new JPanel();
			
		/* Enter name */
		JLabel nameLabel = new JLabel("Name:");
		nameField = new JTextField(20);
		
		/* Enter points */
		JLabel pointsLabel = new JLabel("Points:");
		pointsField = new JTextField(defaultPoints, 5);
		
		/* Button */
		addPlayerBtn = new JButton("Add Player");
		addPlayerBtn.setActionCommand("addPlayer");
		
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(pointsLabel);
		panel.add(pointsField);
		panel.add(addPlayerBtn);		
		
		this.getRootPane().setDefaultButton(addPlayerBtn);
		this.add(panel);
		this.setSize(300, 100);
		this.setTitle("Add Player");
	}
	
	/* Centers the JFrame to MainFrame */
	public void centerDialogToMainWindow(MainFrame mainFrame) {
		this.setLocationRelativeTo(mainFrame);
	}
	
	/* Adds controller for confirm button */
	void addListeners(MainFrame mainFrame, GameEngine gameEngine) {
		addPlayerBtn.addActionListener(new AddPlayerDialogController(mainFrame, gameEngine));
	}
	
	/* Returns entered name */
	public String getNameFieldText() {
		return this.nameField.getText();
	}
	
	/* Resets input for new player entry */
	public void clearPlayerInput() {
		this.nameField.setText("");
		this.pointsField.setText(defaultPoints);
	}
	
	/* Returns entered points */
	public String getPointFieldText() {
		return this.pointsField.getText();
	}

}
