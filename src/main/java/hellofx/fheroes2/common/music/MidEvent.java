package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorWriter;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;

public class MidEvent {
    public ByteArrayList pack = new ByteArrayList();
    public byte[] data = new byte[4];

    public MidEvent() {
    }

    public MidEvent(int delta, byte st, byte d1, byte d2) {
        data[0] = st;
        data[1] = d1;
        data[2] = d2;
        data[3] = 2;
        pack = packValue(delta);
    }

    public MidEvent(int delta, byte st, byte d1) {
        data[0] = st;
        data[1] = d1;
        data[2] = 0;
        data[3] = 1;
        pack = packValue(delta);
    }

    public static ByteArrayList packValue(int delta) {
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

        return res;
    }

    public int size() {
        return pack.size() + data[3] + 1;
    }

    public void writeTo(ByteVectorWriter sb) {

        for (var it : pack)
            sb.put8(it);
        sb.put8(data[0]);
        if (2 == data[3])
            sb.put8(data[1]).put8(data[2]);
        else if (1 == data[3])
            sb.put8(data[1]);
    }
}

