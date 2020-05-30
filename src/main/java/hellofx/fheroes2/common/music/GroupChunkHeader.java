package hellofx.fheroes2.common.music;

public class GroupChunkHeader {

    public int ID = 0; // 4 byte ASCII string, either 'FORM', 'CAT ' or 'LIST'
    public int length = 0;
    public int type = 0; // 4 byte ASCII string

}
