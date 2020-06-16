package hellofx.fheroes2.army;

import hellofx.fheroes2.monster.Monster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Troops {
    public List<Troop> _items = new ArrayList<>();

    public boolean IsValid() {
        for (Troop troop : _items) {
            if (troop.IsValid())
                return true;
        }
        return false;
    }

    public void assign(Troop[] troops) {
        _items.clear();
        _items.addAll(Arrays.asList(troops));
    }

    public void Assign(Troop[] troops) {
        _items.clear();
        _items.addAll(Arrays.asList(troops));
    }

    public void Clean() {
        for (var it : _items) {
            it.Reset();
        }
    }

    public void JoinTroop(Monster mons1, int count) {
        _items.add(new Troop(mons1, count));
    }
}
