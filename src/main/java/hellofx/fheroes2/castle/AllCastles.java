package hellofx.fheroes2.castle;

import hellofx.fheroes2.common.H2Point;

public class AllCastles extends VecCastles {

    public Castle Get(H2Point center) {
        return _items.stream()
                .filter(castle -> castle.isPosition(center))
                .findFirst().orElse(null);

    }
}
