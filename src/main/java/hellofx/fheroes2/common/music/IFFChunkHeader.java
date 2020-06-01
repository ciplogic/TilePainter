package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorReader;

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
}
