package hellofx.fheroes2.heroes;

import hellofx.common.Utilities;
import hellofx.fheroes2.common.H2Point;

import java.util.ArrayList;
import java.util.List;

public class VecHeroes {
    public List<Heroes> _items = new ArrayList<>();

    public Heroes Get(int heroId) {
        //TODO
        return null;
    }

    public Heroes Get(H2Point center) {
        var hero = Utilities.find_if(_items, h -> h.mapPosition.isPosition(center));
        return hero;
    }
}
