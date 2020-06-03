package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorWriter;

import java.util.ArrayList;
import java.util.List;

public class MidEvents {
    public List<MidEvent> _items = new ArrayList<>();

    public MidEvents(XmiTrack t) {
        var evnt = new ByteArrayListReader(t.evnt);

        var pos = 0;
        var end = evnt.size();

        var delta = 0;
        List<meta_t> notesoff = new ArrayList<>();

        while (pos < end) {

            var cur = evnt.get(pos);
            // insert event: note off
            if (delta != 0) {
                // sort duration
                notesoff.sort(meta_t.getComparator());

                var it1 = 0;
                var it2 = notesoff.size();
                var delta2 = 0;

                // apply delta
                for (; it1 != it2; ++it1) {
                    var curNotesOff = notesoff.get(it1);

                    if (curNotesOff.duration <= delta) {
                        // note off
                        _items.add(new MidEvent(curNotesOff.duration - delta2, curNotesOff.command, curNotesOff.quantity, 0x7f));
                        //*it1).duration - delta2, (*it1).command, (*it1).quantity, 0x7F);
                        delta2 += curNotesOff.duration - delta2;
                    }
                }

                // remove end notes
                while (notesoff.size() != 0 && notesoff.get(0).duration <= delta)
                    notesoff.remove(0);

                // fixed delta
                if (delta2 != 0) delta -= delta2;

                // decrease duration
                for (var it : notesoff)
                    it.decrease_duration(delta);
            }

            // interval
            if (cur < 128) {
                delta += cur;
                ++pos;
                continue;
            }
            // command

            // end
            if (0xFF == cur && (0x2F == evnt.get(pos + 1))) {
                _items.add(new MidEvent(delta, evnt.get(pos), evnt.get(pos + 1), evnt.get(pos + 2)));
                break;
            }
            switch (evnt.get(pos) >> 4) {
                // meta
                case 0x0F: {
                    var pack = meta_t.unpackValue(evnt, pos + 2);
                    pos += pack.first + pack.second + 1;
                    delta = 0;
                }
                break;

                // key pressure
                case 0x0A:
                    // control change
                case 0x0B:
                    // pitch bend
                case 0x0E: {
                    _items.add(new MidEvent(delta, evnt.get(pos), evnt.get(pos + 1), evnt.get(pos + 2)));
                    pos += 3;
                    delta = 0;
                }
                break;

                // note off
                case 0x08:
                    // note on
                case 0x09: {
                    _items.add(new MidEvent(delta, evnt.get(pos), evnt.get(pos + 1), evnt.get(pos + 2)));
                    var pack = meta_t.unpackValue(evnt, pos + 3);
                    notesoff.add(new meta_t(evnt.get(pos) - 0x10, evnt.get(pos + 1), pack.first));
                    pos += 3 + pack.second;
                    delta = 0;
                }
                break;

                // program change
                case 0x0C:
                    // chanel pressure
                case 0x0D: {

                    _items.add(new MidEvent(delta, evnt.get(pos), evnt.get(pos + 1)));
                    pos += 2;
                    delta = 0;
                }
                break;

                // unused command
                default:
                    _items.add(new MidEvent(0, 0xFF, 0x2F, 0));
                    throw new RuntimeException("unknown st: 0x");

            }
        }

    }


    public int size() {
        var res = 0;
        for (var it : _items) {
            res += it.size();
        }
        return res;
    }

    public void writeTo(ByteVectorWriter sb) {
        for (var it : _items) {
            it.writeTo(sb);
        }
    }
}
