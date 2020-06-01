package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorWriter;

import static hellofx.fheroes2.common.music.MusicConsts.TAG_MTRK;

public class MidTrack {
    IFFChunkHeader mtrk;
    MidEvents events;


    public MidTrack(XmiTrack t) {
        mtrk = new IFFChunkHeader(TAG_MTRK, 0);
        events = new MidEvents(t);
        mtrk.length = events.size();
    }

    public void writeTo(ByteVectorWriter sb) {

        mtrk.writeTo(sb);
        events.writeTo(sb);
    }
}
