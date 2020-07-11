package hellofx.fheroes2.castle;

import hellofx.fheroes2.common.H2Point;

public class AllCastles extends VecCastles {

    public Castle Get(H2Point center) {
        return _items.stream()
                .filter(castle -> castle.isPosition(center))
                .findFirst().orElse(null);

    }

    public void Init() {
        if (_items.size() != 0)
            clear();
    }

    private void clear() {
        _items.clear();
    }

    public void Scoute(int colors) {

        for (var it : _items)
            if ((colors & it.GetColor()) != 0) it.Scoute();
    }
}
