package hellofx.fheroes2.common.music;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;

import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class ByteArrayListReader {
    private final ByteArrayList data;

    public ByteArrayListReader(ByteArrayList bal) {
        this.data = bal;
    }

    public int size() {
        return data.size();
    }

    public int get(int index) {
        return toByte(data.getByte(index));
    }
}
