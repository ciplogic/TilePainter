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
}
