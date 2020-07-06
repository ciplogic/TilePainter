package hellofx.fheroes2.heroes.route;

import java.util.HashMap;
import java.util.Map;

public class PathMap {
    Map<Integer, cell_t> _values = new HashMap<>();

    public void clear() {
        _values.clear();

    }

    public cell_t get(int index) {
        return _values.get(index);
    }
}
