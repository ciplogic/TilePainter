package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorReader;

public class GroupChunkHeader {

    public int ID = 0; // 4 byte ASCII string, either 'FORM', 'CAT ' or 'LIST'
    public int length = 0;
    public int type = 0; // 4 byte ASCII string

    public void readFrom(ByteVectorReader sb) {
        ID = sb.getBE32();
        length = sb.getBE32();
        type = sb.getBE32();
    }
}
