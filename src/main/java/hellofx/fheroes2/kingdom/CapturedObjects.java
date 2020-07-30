package hellofx.fheroes2.kingdom;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class CapturedObjects {
    public Int2ObjectOpenHashMap<CapturedObject> _items = new Int2ObjectOpenHashMap<>();

    public void Set(int index, int obj, int col) {
        CapturedObject co = Get(index);

        if (co.GetColor() != col && co.guardians.IsValid())
            co.guardians.Reset();

        co.Set(obj, col);
    }

    public void SetColor(int index, int col) {
        Get(index).SetColor(col);
    }

    private CapturedObject Get(int index) {
        var res = _items.get(index);
        if (res == null) {
            res = new CapturedObject();
            _items.put(index, res);
        }
        return res;
    }

    public int GetColor(int index) {
        var it = _items.get(index);
        return it != null ? it.GetColor() : H2Color.NONE;
    }
}
