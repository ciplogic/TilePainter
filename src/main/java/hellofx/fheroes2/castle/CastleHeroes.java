package hellofx.fheroes2.castle;

import hellofx.fheroes2.common.H2Pair;
import hellofx.fheroes2.heroes.Heroes;

public class CastleHeroes extends H2Pair<Heroes, Heroes> {
    public CastleHeroes(Heroes guest, Heroes guard) {
        super(guest, guard);
    }

    public boolean FullHouse() {
        return first != null && second != null;
    }

    public Heroes Guest() {
        return first;
    }

    public Heroes Guard() {
        return second;
    }

    public boolean IsValid() {
        return first != null || second != null;
    }

    public Heroes GuestFirst() {
        return first != null ? first : second;
    }
}
