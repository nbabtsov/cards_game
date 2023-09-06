package coms362.cards.slapjack;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.*;
import coms362.cards.model.Card;
import coms362.cards.model.Pile;

public class SJMove implements Move {

	private final Card c;
	private final Player p;
	private final Pile fromPile;
	private final Pile toPile;

	public SJMove(Card c, Player p, Pile fromPile, Pile toPile){
		this.c = c;
		this.p = p;
		this.fromPile = fromPile;
		this.toPile = toPile;
	}

	public void apply(Table table) {
		if (p.getPlayerNum() == 1) {
			table.removeFromPile(SJRules.PLAYER_ONE_PILE, c);
		}
		else {
			table.removeFromPile(SJRules.PLAYER_TWO_PILE, c);
		}
		table.addToPile(SJRules.CENTER_PILE, c);
	}

	public void apply(ViewFacade view) {
		view.send(new HideCardRemote(c));
		view.send(new RemoveFromPileRemote(fromPile, c));
		view.send(new AddToPileRemote(toPile, c));
		view.send(new ShowCardRemote(c));
		view.send(new ShowPlayerScore(p, null));
	}
}
