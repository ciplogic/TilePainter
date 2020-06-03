package hellofx.fheroes2.common.music;

import hellofx.fheroes2.common.H2IntPair;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;

import java.util.Comparator;

public class meta_t {

    byte command = 0;
    byte quantity = 0;
    int duration = 0;

    meta_t() {
    }

    meta_t(int c, int q, int d) {
        command = (byte) c;
        quantity = (byte) q;
        duration = (d);

    }

    public static Comparator<? super meta_t> getComparator() {
        return new MetaDurationComparator();
    }

    public static H2IntPair unpackValue(ByteArrayListReader evnt, int p) {
        var res = new H2IntPair();
        var ptr = p;

        while ((evnt.get(p) & 0x80) != 0) {
            if (4 <= p - ptr) {
                throw new RuntimeException("unpack delta mistake");
            }

            res.first |= 0x0000007F & evnt.get(p);
            res.first <<= 7;
            ++p;
        }

        res.first += evnt.get(p);
        res.second = p - ptr + 1;

        return res;
    }

    public static byte[] packValue(int delta) {
        byte c1 = (byte) (delta & 0x0000007F);
        byte c2 = (byte) ((delta & 0x00003F80) >> 7);
        byte c3 = (byte) ((delta & 0x001FC000) >> 14);
        byte c4 = (byte) ((delta & 0x0FE00000) >> 21);

        var res = new ByteArrayList();
        if (c4 != 0) {
            res.add((byte) (c4 | 0x80));
            res.add((byte) (c3 | 0x80));
            res.add((byte) (c2 | 0x80));
            res.add(c1);
        } else if (c3 != 0) {
            res.add((byte) (c3 | 0x80));
            res.add((byte) (c2 | 0x80));
            res.add(c1);
        } else if (c2 != 0) {
            res.add((byte) (c2 | 0x80));
            res.add(c1);
        } else
            res.add(c1);

        return res.toByteArray();
    }

    public void decrease_duration(int delta) {
        duration -= delta;
    }

    private static class MetaDurationComparator implements Comparator<meta_t> {
        @Override
        public int compare(meta_t o1, meta_t o2) {
            return o1.duration - o2.duration;
        }
    }
}

