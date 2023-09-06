package coms362.cards.app;

import java.util.Arrays;
import java.util.List;

import coms362.cards.abstractcomp.GameFactory;
import coms362.cards.fiftytwo.P52MPGameFactory;
import coms362.cards.fiftytwo.sp.P52SPGameFactory;
import coms362.cards.slapjack.SJGameFactory;

public class GameFactoryFactory {

    // TODO: list of games is hardcoded for now
    static final String PU52MP = "PU52MP";
    static final String PU52SP = "PU52SP";
    static final String SJ = "SJ";

    String[] gameIds = {PU52MP, PU52SP, SJ};
    List<String> supported = Arrays.asList(gameIds);

    public GameFactory getGameFactory(String selector) {
        switch (selector) {
            case PU52MP:
                return new P52MPGameFactory();
            case PU52SP:
                return new P52SPGameFactory();
            case SJ:
                return new SJGameFactory();
            default:
                return null;
        }
    }

    public boolean isValidSelection(String gameId) {
        return supported.contains(gameId);
    }

}
