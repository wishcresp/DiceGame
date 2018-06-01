package view;

import java.awt.FlowLayout;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.DicePanelController;
import model.interfaces.DicePair;

public class DicePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ImageIcon diceIcon1, diceIcon2;
	private JLabel diceLabel1, diceLabel2, diceTotal;
	
	private final int spacing = 10;
	private final String defaultTotalText = "Waiting to Roll";
	private final String diceImages[] = {"res/dice_0.png", "res/dice_1.png", "res/dice_2.png",
		"res/dice_3.png", "res/dice_4.png", "res/dice_5.png", "res/dice_6.png"};

	public DicePanel() {
		
		/* Dice Images and total */
		diceIcon1 = new ImageIcon(diceImages[0]);
		diceIcon2 = new ImageIcon(diceImages[0]);
		
		diceLabel1 = new JLabel(diceIcon1);
		diceLabel2 = new JLabel(diceIcon2);
		diceTotal = new JLabel(defaultTotalText);
		diceTotal.setFont(new Font(null, Font.BOLD, 20));
		diceTotal.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		/* Layout */
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		/* Dice 1 */
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, spacing, spacing);
		this.add(diceLabel1, c);
		
		/* Dice 2 */
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, spacing, spacing, 0);
		this.add(diceLabel2, c);
		
		/* Dice Total */
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0, 0, 0, 0);
		this.add(diceTotal, c);
		
		/* Not focusable */
		this.setFocusable(false);
	}
	
	/* Adds a listener for panel resizing */
	public void addListeners(MainFrame mainFrame) {
		this.addComponentListener(new DicePanelController(mainFrame));
	}
	
	/* Updates the rolling dice panel */
	public void refresh(MainFrame mainFrame, DicePair dicePair) {
		if (dicePair != null) {
			updateDice(dicePair.getDice1(), dicePair.getDice2());
			mainFrame.getToolBar().setRollDiceBtnEnabled(false);
			diceTotal.setText("Total: " + getDiceTotal(dicePair));
		} else {
			updateDice(0, 0);
			diceTotal.setText(defaultTotalText);
		}
	}
	
	/* Changes the displayed dice images */
	private void updateDice(int dice1, int dice2) {
		diceIcon1 = new ImageIcon(diceImages[dice1]);
		diceIcon2 = new ImageIcon(diceImages[dice2]);
		diceLabel1.setIcon(diceIcon1);
		diceLabel2.setIcon(diceIcon2);
		scaleDice();
	}
	
	/* Scales the dice images depending on the panel size */
	public void scaleDice() {
		/* Compares height and width to determine the scale to apply 
		 * to the dice images (dice width ~= 2 * dice height) */
		int size = this.getParent().getWidth() > 
				this.getParent().getHeight() * 2 ?
				this.getParent().getHeight() / 3 :
				this.getParent().getWidth() / 6;
		
		/* Scales each dice image */
		diceLabel1.setIcon(new ImageIcon(diceIcon1.getImage()
				.getScaledInstance(size, size, Image.SCALE_FAST)));
		diceLabel2.setIcon(new ImageIcon(diceIcon2.getImage()
				.getScaledInstance(size, size, Image.SCALE_FAST)));
	}
	
	/* Calculates and returns the dice total */
	public int getDiceTotal(DicePair dicePair) {
		return dicePair.getDice1() + dicePair.getDice2();
	}
	
}
