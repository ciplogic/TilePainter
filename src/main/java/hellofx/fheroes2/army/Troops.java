package hellofx.fheroes2.army;

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
}
