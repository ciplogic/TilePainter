package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorWriter;

import static hellofx.fheroes2.common.music.meta_t.packValue;
import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class MidEvent {
    public byte[] pack = new byte[0];
    public byte[] data = new byte[4];

    public MidEvent() {
    }

    public MidEvent(int delta, int st, int d1, int d2) {
        data[0] = (byte) st;
        data[1] = (byte) d1;
        data[2] = (byte) d2;
        data[3] = 2;
        pack = packValue(delta);
    }

    public MidEvent(int delta, int st, int d1) {
        data[0] = (byte) st;
        data[1] = (byte) d1;
        data[2] = 0;
        data[3] = 1;
        pack = packValue(delta);
    }

    public int size() {
        return pack.length + toByte(data[3]) + 1;
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

