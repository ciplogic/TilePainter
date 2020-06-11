package hellofx.fheroes2.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Addons {
    @Override
    public String toString() {
        String joined = _items.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        return "Addons[" + joined + ']';
    }

    public List<TilesAddon> _items = new ArrayList<>();
}
