package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.serialize.ByteVectorWriter;

public class IFFChunkHeader {
    public int ID, length;

    public IFFChunkHeader(int id, int sz) {
        ID = id;
        length = sz;
    }

    public IFFChunkHeader() {
    }

    public void readFrom(ByteVectorReader sb) {
        ID = sb.getBE32();
        length = sb.getBE32();
    }

    public void writeTo(ByteVectorWriter sb) {
        sb.putBE32(ID);
        sb.putBE32(length);
    }
}
