package hellofx.fheroes2.common.music;

import java.util.ArrayList;
import java.util.List;

public class MidTracks {

    List<MidTrack> _items = new ArrayList<>();

    public MidTracks(XmiTracks tracks) {
        for (var track : tracks._items)
            _items.add(new MidTrack(track));

    }
}
