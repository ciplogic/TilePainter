package hellofx.fheroes2.common.music;

import static hellofx.fheroes2.common.music.MusicConsts.TAG_MTRK;

public class MidTrack {
    IFFChunkHeader mtrk;
    MidEvents events;


    public MidTrack(XmiTrack t) {
        mtrk = new IFFChunkHeader(TAG_MTRK, 0);
        events = new MidEvents(t);
        mtrk.length = events.size();
    }
}
