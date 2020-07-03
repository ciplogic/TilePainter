package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.maps.objects.MapObjectSimple;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class MapObjects {
    public Int2ObjectOpenHashMap<MapObjectSimple> _items = new Int2ObjectOpenHashMap<>();

    public void add(MapObjectSimple obj) {
        _items.put(obj.GetUID(), obj);
    }

    public MapObjectSimple get(int id) {
        if (_items.containsKey(id))
            return _items.get(id);
        return null;
    }
}
