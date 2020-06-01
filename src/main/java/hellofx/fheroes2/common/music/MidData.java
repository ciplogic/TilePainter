package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorWriter;

import static hellofx.fheroes2.common.music.MusicConsts.TAG_MTHD;

public class MidData {

    IFFChunkHeader mthd;
    int format;
    int ppqn;
    MidTracks tracks;

    public MidData(XmiTracks t, int p) {
        mthd = new IFFChunkHeader(TAG_MTHD, 6);
        format = (0);
        ppqn = (p);
        tracks = new MidTracks(t);

    }

    public void writeTo(ByteVectorWriter sb) {
    }
}
