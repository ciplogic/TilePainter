package hellofx.fheroes2.common.music;

import hellofx.fheroes2.serialize.ByteVectorWriter;

public class Music {
    public static byte[] Xmi2Mid(byte[] buf) {
        XmiData xmi = new XmiData(buf);
        ByteVectorWriter sb = new ByteVectorWriter(16 * 4096);

        if (xmi.isvalid()) {
            MidData mid = new MidData(xmi.tracks, 64);
            mid.writeTo(sb);
        }

        return sb.data();
    }
}
