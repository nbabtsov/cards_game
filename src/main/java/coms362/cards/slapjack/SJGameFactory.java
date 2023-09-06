package coms362.cards.slapjack;

import coms362.cards.abstractcomp.*;
import coms362.cards.fiftytwo.PartyRole;
import coms362.cards.model.PlayerFactory;
import coms362.cards.model.TableBase;
import coms362.cards.streams.RemoteTableGateway;

public class SJGameFactory implements GameFactory, PlayerFactory, ViewFactory {
	@Override
	public Rules createRules() {
		return new SJRules();
	}

	@Override
	public Table createTable() {
		return new TableBase(this);
	}

	@Override
	public View createView(PartyRole role, Integer num, String socketId, RemoteTableGateway gw ) {
		return new SJPlayerView(num, socketId, gw);
	}

	@Override
	public Player createPlayer( Integer position, String socketId) {
		return new SJPlayer(position, socketId);
	}

	@Override
	public PlayerFactory createPlayerFactory() {
		return this;
	}
}
