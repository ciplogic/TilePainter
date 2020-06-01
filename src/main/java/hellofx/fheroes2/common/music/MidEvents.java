package hellofx.fheroes2.common.music;

import java.util.ArrayList;
import java.util.List;

public class MidEvents {
    public List<MidEvent> _items = new ArrayList<>();

    public MidEvents(XmiTrack t) {
    }

    public int size() {
        var res = 0;
        for (var it : _items)
            res += it.size();
        return res;
    }
}
