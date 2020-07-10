package hellofx.fheroes2.system;

import hellofx.fheroes2.castle.Castle;
import hellofx.fheroes2.common.H2Pair;
import hellofx.fheroes2.heroes.Heroes;

import static hellofx.fheroes2.system.FocusKind.FOCUS_CASTLE;

public class Focus extends H2Pair<Integer, Object> {
    public void Reset() {
    }

    public Heroes GetHeroes() {
        if (first != FocusKind.FOCUS_HEROES || second == null) {
            return null;
        }
        return (Heroes) (second);
    }

    public Castle GetCastle() {
        return first == FOCUS_CASTLE && second != null ? (Castle) second : null;
    }
}
