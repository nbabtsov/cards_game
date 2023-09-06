package coms362.cards.slapjack;

import coms362.cards.abstractcomp.Player;
import coms362.cards.model.Location;
import coms362.cards.model.Pile;

public class SJPlayer implements Player {

	private int score = 0;
	private final int playerNum;
	private String socketId = null;
	private String pileName = "";
	private Pile pile;

	public SJPlayer(int i) {
		this.playerNum = i;
		if (playerNum == 1) {
			pileName = "playerOnePile";
		}
		else {
			pileName = "playerTwoPile";
		}
	}

	public SJPlayer(Integer position, String socketId) {
		playerNum = position;
		this.socketId = socketId;
		if (position == 1) {
			pileName = "playerOnePile";
		}
		else {
			pileName = "playerTwoPile";
		}
	}

	public int addToScore(int amount) {
		return score += amount;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public String getSocketId(){
		return socketId;
	}

	@Override
	public int getScore() {
		if (pile == null) {
			return 0;
		}
		else {
			return pile.getCards().size();
		}
	}

	public Pile getPile() {
		return pile;
	}

	public void setPile(Pile pile) {
		this.pile = pile;
	}

	public String getPileName() {
		return pileName;
	}

	@Override
	public String toString(){
		return String.format("Player: pos=%d, socket=%s, score=%d%n" , playerNum, socketId, score);
	}
}
