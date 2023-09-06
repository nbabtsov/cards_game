package coms362.cards.slapjack;

import coms362.cards.abstractcomp.*;
import coms362.cards.events.inbound.*;
import coms362.cards.fiftytwo.PartyRole;
import coms362.cards.model.Card;
import coms362.cards.model.Pile;

public class SJRules extends RulesDispatchBase implements Rules, RulesDispatch {

	public static final String CENTER_PILE = "centerPile";
	public static final String PLAYER_ONE_PILE = "playerOnePile";
	public static final String PLAYER_TWO_PILE = "playerTwoPile";

	public SJRules() {
		registerEvents();
	}

	public Move eval(Event nextE, Table table, Player player) {
		return nextE.dispatch(this, table, player);
	}

	public Move apply(CardEvent e, Table table, Player player) {
		Pile fromPile;
		if (player.getPlayerNum() == 1) {
			fromPile = table.getPile(PLAYER_ONE_PILE);
		}
		else { 
		    fromPile = table.getPile(PLAYER_TWO_PILE); 
		}
		Pile toPile = table.getPile(CENTER_PILE);
		Card c = fromPile.getCard(e.getId());
		if (c == null) {
			return new DropEventCmd();
		}
		return new SJMove(c, player, fromPile, toPile);
	}

	public Move apply(DealEvent e, Table table, Player player) {
		return new DealCommand(table, player);
	}

	public Move apply(InitGameEvent e, Table table, Player player) {
		return new SJInitCmd(table.getPlayerMap(), "Slapjack", table);
	}

	public Move apply(NewPartyEvent e, Table table, Player player) {
		if (e.getRole() == PartyRole.player) {
			return new CreatePlayerCmd(e.getPosition(), e.getSocketId());
		}
		return new DropEventCmd();
	}

	public Move apply(SetQuorumEvent e, Table table, Player player) {
		return new SetQuorumCmd(e.getQuorum());
	}

	public Move apply(ConnectEvent e, Table table, Player player) {
		Move rval = new DropEventCmd();
		System.out.println("Rules apply ConnectEvent " + e);
		if (!table.getQuorum().exceeds(table.getPlayers().size() + 1)) {
			if (e.getRole() == PartyRole.player) {
				rval = new CreatePlayerCmd(e.getPosition(), e.getSocketId());
			}
		}
		System.out.println("PickupRules connectHandler rval = " + rval);
		return rval;
	}

	/**
	 * We rely on Rules to register the appropriate input events with
	 * the unmarshaller. This avoids excessive complexity in the
	 * abstract factory and there is a natural dependency between
	 * the rules and the game input events.
	 */
	private void registerEvents() {
		EventUnmarshallers handlers = EventUnmarshallers.getInstance();
		handlers.registerHandler(InitGameEvent.kId, (Class) InitGameEvent.class);
		handlers.registerHandler(DealEvent.kId, (Class) DealEvent.class);
		handlers.registerHandler(CardEvent.kId, (Class) CardEvent.class);
		handlers.registerHandler(GameRestartEvent.kId, (Class) GameRestartEvent.class);
		handlers.registerHandler(NewPartyEvent.kId, (Class) NewPartyEvent.class);
	}
}
