package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorWriter;

import java.util.ArrayList;
import java.util.List;

public class MidTracks {

    List<MidTrack> _items = new ArrayList<>();

    public MidTracks(XmiTracks tracks) {
        for (var track : tracks._items)
            _items.add(new MidTrack(track));

    }

    public void writeTo(ByteVectorWriter sb) {
        for (var item : _items) {
            item.writeTo(sb);
        }
    }
}
