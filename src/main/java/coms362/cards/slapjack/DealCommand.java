package coms362.cards.slapjack;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.CreateCardRemote;
import coms362.cards.events.remote.HideButtonRemote;
import coms362.cards.events.remote.UpdateCardRemote;
import coms362.cards.model.Card;
import coms362.cards.model.Location;
import coms362.cards.model.Pile;

import java.util.*;

public class DealCommand implements Move {
	private final Table table;
//	private final Player player;

	public DealCommand(Table table, Player player) {
		this.table = table;
//		this.player = player;
	}

	public void apply(Table table) {
		// TODO Auto-generated method stub
	}

	public void apply(ViewFacade views) {
		try {
			String remoteId = views.getRemoteId(DealButton.kSelector);
			views.send(new HideButtonRemote(remoteId));
			Pile center = table.getPile(SJRules.CENTER_PILE);

			if (center == null) {
				return;
			}

            int i = 0;
        	List<Card> shuffledCards = getShuffledCards(center);

			for (Card c : shuffledCards) {
				String pile = i < 26 ? SJRules.PLAYER_ONE_PILE : SJRules.PLAYER_TWO_PILE;
				Location location = table.getPile(pile).getLocation();
			    c.setX(location.getX());
                c.setY(location.getY());
                table.addToPile(pile, c);
                table.removeFromPile(SJRules.CENTER_PILE, c);
			    i++;
				String outVal = "";
				views.send(new CreateCardRemote(c));
				views.send(new UpdateCardRemote(c));
				System.out.println(outVal);
			}
			table.getPile(SJRules.CENTER_PILE).setFaceUp(true);
			((SJPlayer) table.getPlayer(1)).setPile(table.getPile(SJRules.PLAYER_ONE_PILE));
			((SJPlayer) table.getPlayer(2)).setPile(table.getPile(SJRules.PLAYER_TWO_PILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List getShuffledCards(Pile pile){ //shuffle method
		Collection<Card> cards = pile.getCards();
        ArrayList<Card> list = new ArrayList<>(cards);
		Collections.shuffle(list, new Random(13));
		return list;
	}
}

