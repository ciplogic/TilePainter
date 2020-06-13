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

    public void Remove(int uniq) {
        Addons clean = new Addons();
        for (var addon : _items) {
            if (!addon.isUniq(uniq))
                clean._items.add(addon);
        }
        _items = clean._items;
    }

    public List<TilesAddon> _items = new ArrayList<>();
}
