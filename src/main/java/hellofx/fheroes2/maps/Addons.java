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
        var clean = new ArrayList<TilesAddon>();
        for (var addon : _items) {
            if (!addon.isUniq(uniq))
                clean.add(addon);
        }
        clean.trimToSize();
        _items = clean;
    }

    public void Refresh() {
        var clones = new ArrayList<TilesAddon>(_items.size());
        for (var it : _items) {
            clones.add(it.clone());
        }
        _items.clear();
        _items.addAll(clones);
    }

    public List<TilesAddon> _items = new ArrayList<>();
}
