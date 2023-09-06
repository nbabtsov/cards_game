package coms362.cards.slapjack;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.*;
import coms362.cards.model.Card;
import coms362.cards.model.Location;
import coms362.cards.model.Pile;

import java.util.Map;
import java.util.Random;

public class SJInitCmd implements Move {

	private final Table table;
	private final Map<Integer, Player> players;
	private final String title;

	public SJInitCmd(Map<Integer, Player> players, String game, Table table) {
		this.players = players;
		this.title = game;
		this.table = table;
	}

	public void apply(Table table) {
		Pile centerPile = new Pile(SJRules.CENTER_PILE, new Location(300, 300));
		//Random random = table.getRandom();
		try {
			for (String suit : Card.suits) {
				for (int i = 1; i <= 13; i++) {
					Card card = new Card();
					card.setSuit(suit);
					card.setRank(i);
					card.setX(300); //random.nextInt(200) +
					card.setY(300); //random.nextInt(200) +
//					card.setRotate(random.nextInt(360));
//					card.setFaceUp(true);
					centerPile.addCard(card);
				}
			}
			centerPile.setFaceUp(true); //works
			table.addPile(centerPile);
			
            Pile playerOnePile = new Pile(SJRules.PLAYER_ONE_PILE, new Location(100, 300));
            Pile playerTwoPile = new Pile(SJRules.PLAYER_TWO_PILE, new Location(500, 300));
            
			playerOnePile.setFaceUp(false);
            playerTwoPile.setFaceUp(false);
			
            table.addPile(playerOnePile);
			table.addPile(playerTwoPile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void apply(ViewFacade view) {
		view.send(new SetupTable());
		view.send(new SetGameTitleRemote(title));

		for (Player p : players.values()) {
			String role = (p.getPlayerNum() == 1) ? "Dealer" : "Player " + p.getPlayerNum();
			view.send(new SetBottomPlayerTextRemote(role, p));
		}

		view.send(new CreatePileRemote(table.getPile(SJRules.CENTER_PILE)));
//		view.send(new CreatePileRemote(table.getPile(SJRules.DISCARD_PILE)));
		DealButton dealButton = new DealButton("DEAL", new Location(0, 0));
		view.register(dealButton); //so we can find it later.
		view.send(new CreateButtonRemote(dealButton));
		//view.send(new CreateButtonRemote(Integer.toString(getNextId()), "reset", "RestartGame", "Reset", new Location(500,0)));
		//view.send(new CreateButtonRemote(Integer.toString(getNextId()), "clear", "ClearTable", "Clear Table", new Location(500,0)));
	}
}
