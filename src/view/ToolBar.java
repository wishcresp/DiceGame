package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import controller.IconAnimationController;
import controller.ToolBarController;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class ToolBar extends JToolBar {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel diceImgAnimation;
	private JTextField betField;
	private JButton betBtn, rollBtn, nextPlayerBtn, nextRoundBtn;
	
	private final String defaultBetText = "100";
	private final int iconSize = 40;
	
	private String diceImages[] = {
			"res/dice_blur_1.png",
			"res/dice_blur_2.png",
			"res/dice_blur_3.png",
			"res/dice_blur_4.png"};
	
	ToolBar() {
		
		JLabel title = new JLabel("Dice Game");
		title.setFont(new Font(null, Font.ITALIC, 24));
		title.setFocusable(false);
		
		/* Dice animation */
		diceImgAnimation = new JLabel();
		diceImgAnimation.setFocusable(false);
		this.addSeparator(new Dimension(5, 0));
		this.add(diceImgAnimation);
		new IconAnimationController(this);
		
		/* Title */
		this.addSeparator(new Dimension(5, 0));
		this.add(title);
		
		/* Enter bet field */
		betField = new JTextField("");
		betField.setEditable(false);
		betField.setMinimumSize(new Dimension(40, 25));
		betField.setMaximumSize(new Dimension(250, 25));
		betField.setFont(new Font(null, Font.PLAIN, 14));
		betField.setHorizontalAlignment(SwingConstants.CENTER);
		betField.setFocusTraversalKeysEnabled(false);
		
		/* Make bet button */
		betBtn = new JButton("Place bet");
		betBtn.setActionCommand("placeBet");
		setBetInputEnabled(false);
		
		/* Roll dice button */
		rollBtn = new JButton("Roll Dice");
		rollBtn.setActionCommand("rollDice");
		setRollDiceBtnEnabled(false);
		
		/* Next player button*/
		nextPlayerBtn = new JButton("Next Player");
		nextPlayerBtn.setActionCommand("nextPlayer");
		setNextPlayerBtnEnabled(false);
		
		/* Next round button*/
		nextRoundBtn = new JButton("Next Round");
		nextRoundBtn.setActionCommand("nextRound");
		setNextRoundBtnEnabled(false);
		
		/* Disable focus */
		this.setFocusable(false);
		
		this.addSeparator(new Dimension(30, 0));
		this.add(betField);
		this.add(betBtn);
		this.add(rollBtn);
		this.add(nextPlayerBtn);
		this.add(nextRoundBtn);
	}
	
	void addListeners(MainFrame mainFrame, GameEngine gameEngine) {
		ToolBarController listener = new ToolBarController(mainFrame, gameEngine);
		betBtn.addActionListener(listener);
		rollBtn.addActionListener(listener);
		nextPlayerBtn.addActionListener(listener);
		nextRoundBtn.addActionListener(listener);
		betField.addKeyListener(listener);
		
		/* Initially sets the active button for enter key presses */
		setActiveButton(mainFrame, gameEngine);
	}
	
	/* Updates components in the tool bar */
	public void refresh(MainFrame mainFrame, GameEngine gameEngine) {
		/* If a player is selected */
		if (mainFrame.getSideBar().playerIsSelected(gameEngine.getAllPlayers().size())) {
			
			Player player = mainFrame.getSideBar().getSelectedPlayer(gameEngine);	
			/* If the player has not bet, enable the bet input */
			setBetInputEnabled(player.getBet() == 0);
			/* If the player has not rolled and has bet, enable the roll button */
			setRollDiceBtnEnabled(player.getBet() != 0 && player.getRollResult() == null);
			/* If the player has rolled, enable the next player button*/
			setNextPlayerBtnEnabled(player.getRollResult() != null);

			/* Sets the text in the bet field */
			if (player.getBet() != 0)
				betField.setText(String.valueOf(player.getBet()));
			else
				betField.setText(defaultBetText);	
		
		/* If house is selected */
		} else {
			/* Enable if players exist and have all rolled,
			 * and the house has not rolled */
			rollBtn.setEnabled(gameEngine.getAllPlayers().size() > 0
					&& mainFrame.getSideBar().allPlayersRolled(gameEngine) 
					&& gameEngine.getHouseResult() == null);
			/* Disable other buttons */
			setBetInputEnabled(false);
			nextPlayerBtn.setEnabled(false);
			betField.setText("");
		}
		
		/* Enables the next round button when house has rolled */
		setNextRoundBtnEnabled(gameEngine.getHouseResult() != null);
		
		/* Sets the active button for enter key presses */
		setActiveButton(mainFrame, gameEngine);
	}
	
	/* Sets the visible button to default to allow ENTER key presses to push it */
	private void setActiveButton(MainFrame mainFrame, GameEngine gameEngine) {
		this.getRootPane().setDefaultButton(getActiveButton(mainFrame, gameEngine));
	}
	
	/* Gets the visible button */
	private JButton getActiveButton(MainFrame mainFrame, GameEngine gameEngine) {
		JButton activeBtn;
		/* If player is selected */
		if (mainFrame.getSideBar().playerIsSelected(gameEngine.getAllPlayers().size())) {
			Player player = mainFrame.getSideBar().getSelectedPlayer(gameEngine);
			/* If player has not bet */
			activeBtn = player.getBet() == 0 ? betBtn :
				/* If player has bet and not rolled */
				player.getRollResult() == null ? rollBtn : nextPlayerBtn;
		
		/* If house is selected */
		} else {
			/* If house has not rolled  */
			activeBtn = gameEngine.getHouseResult() == null ? rollBtn : nextRoundBtn;
		}
		return activeBtn;
	}
	
	/* Animation sets the animated dice icon in the tool bar */
	public void setDiceImage(int i) {
		ImageIcon diceIcon = new ImageIcon(diceImages[i]);
		diceIcon = new ImageIcon(diceIcon.getImage()
				.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
		diceImgAnimation.setIcon(diceIcon);
	}
	
	/* Click the place bet button */
	public void clickBetBtn() {
		betBtn.doClick();
	}
	
	/* Focus on bet entry field */
	public void focusBetField() {
		betField.requestFocus();
	}
	
	public String getBetText() {
		return betField.getText();
	}
	
	/* Enables bet entry */
	private void setBetInputEnabled(boolean enable) {
		betBtn.setEnabled(enable);
		betBtn.setFocusable(enable);
		betField.setEditable(enable);
		betField.setFocusable(enable);
	}
	
	public void setRollDiceBtnEnabled(boolean enable) {
		rollBtn.setEnabled(enable);
		rollBtn.setFocusable(enable);
	}
	
	private void setNextPlayerBtnEnabled(boolean enable) {
		nextPlayerBtn.setEnabled(enable);
		nextPlayerBtn.setFocusable(enable);
	}
	
	public void setNextRoundBtnEnabled(boolean enable) {
		nextRoundBtn.setEnabled(enable);
		nextRoundBtn.setFocusable(enable);
	}

}
