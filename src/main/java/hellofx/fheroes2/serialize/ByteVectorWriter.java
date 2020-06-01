package hellofx.fheroes2.serialize;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;

public class ByteVectorWriter {
    ByteArrayList _data;

    public ByteVectorWriter(int preSize) {
        _data = new ByteArrayList(preSize);
    }

    public byte[] data() {
        return _data.toByteArray();
    }

    public void putLE16(int v) {
        put8(v);
        put8(v >> 8);
    }

    public void putBE16(int v) {
        put8(v >> 8);
        put8(v);
    }

    public void putLE32(int v) {
        putLE16(v);
        putLE16(v >> 16);
    }

    public void putBE32(int v) {
        putBE16(v >> 16);
        putBE16(v);
    }

    private void put8(int v) {
        _data.add((byte) v);
    }
}
