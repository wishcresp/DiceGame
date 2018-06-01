package model;

import model.interfaces.Player;
import model.interfaces.DicePair;

public class SimplePlayer implements Player {

	private String playerId;
	private String playerName;
	private int points;
	private int bet;
	private DicePair rollResult;
	
	public SimplePlayer(String playerId, String playerName, int initialPoints)
	{
		this.playerId = playerId;
		this.playerName = playerName;
		this.points = initialPoints;
	}
	
	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String getPlayerId() {
		return this.playerId;
	}

	@Override
	public boolean placeBet(int bet) {
		if (bet <= this.points)
		{
			this.bet = bet;
			return true;
		}
		return false;
	}

	@Override
	public int getBet() {
		return this.bet;
	}

	@Override
	public DicePair getRollResult() {
		return this.rollResult;
	}

	@Override
	public void setRollResult(DicePair rollResult) {
		this.rollResult = rollResult;
	}
	
	@Override
	public String toString() {
		return "Player: " + this.playerId 
				+ " - "+ this.playerName + ": ";
	}

}
