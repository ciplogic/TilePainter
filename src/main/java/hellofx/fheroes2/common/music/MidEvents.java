package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorWriter;

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

    public void writeTo(ByteVectorWriter sb) {
        for (var it : _items) {
            it.writeTo(sb);
        }
    }
}
