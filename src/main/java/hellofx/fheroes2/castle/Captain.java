package hellofx.fheroes2.castle;

import hellofx.fheroes2.heroes.HeroBase;
import hellofx.fheroes2.heroes.HeroType;

public class Captain extends HeroBase {
    private final Castle home;

    public Captain(Castle castle) {
        super(HeroType.CAPTAIN, castle.race);
        home = castle;
    }

}
