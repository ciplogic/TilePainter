package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorReader;

import static hellofx.fheroes2.common.music.MusicConsts.*;

public class XmiData {
    public XmiTracks tracks = new XmiTracks();

    public XmiData(byte[] buf) {
        ByteVectorReader sb = new ByteVectorReader(buf);

        GroupChunkHeader group = new GroupChunkHeader();
        IFFChunkHeader iff = new IFFChunkHeader();

        // FORM XDIR
        group.readFrom(sb);
        if (group.ID != TAG_FORM || group.type != TAG_XDIR) {
            throw new RuntimeException("parse H2ERROR: " + "form xdir");

        }
        // INFO
        iff.readFrom(sb);
        if (iff.ID != TAG_INFO || iff.length != 2) {
            throw new RuntimeException("parse H2ERROR: " + "info");
        }
        int numTracks = sb.getLE16();

        // CAT XMID

        group.readFrom(sb);
        if (group.ID != TAG_CAT0 || group.type != TAG_XMID) {
            throw new RuntimeException("parse H2ERROR: " + "cat xmid");
        }
        for (int track = 0; track < numTracks; ++track) {
            var xmiTrack = new XmiTrack();

            var timb = xmiTrack.timb;
            var evnt = xmiTrack.evnt;

            group.readFrom(sb);
            // FORM XMID
            if (group.ID != TAG_FORM || group.type != TAG_XMID) {
                throw new RuntimeException("unknown tag: " + group.ID + " (expected FORM), " + group.type + " (expected XMID)");
            }
            iff.readFrom(sb);
            // [TIMB]
            if (iff.ID == TAG_TIMB) {
                timb.addElements(0, sb.getRaw(iff.length));
                if (timb.size() != iff.length) {
                    throw new RuntimeException("parse H2ERROR: " + "out of range");
                }
                iff.readFrom(sb);
            }

            // [RBRN]
            if (iff.ID == TAG_RBRN) {
                sb.skip(iff.length);
                iff.readFrom(sb);
            }

            // EVNT
            if (iff.ID != TAG_EVNT) {
                throw new RuntimeException("parse H2ERROR: " + "evnt");
            }

            evnt.addElements(0, sb.getRaw(iff.length));

            if (evnt.size() != iff.length) {
                throw new RuntimeException("parse H2ERROR: " + "out of range");
            }
            tracks._items.add(xmiTrack);
        }
    }

    public boolean isvalid() {
        return tracks._items.size() != 0;
    }
}
