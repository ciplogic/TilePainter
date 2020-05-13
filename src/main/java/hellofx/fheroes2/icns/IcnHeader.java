package hellofx.fheroes2.icns;

import hellofx.fheroes2.serialize.ByteVectorReader;

public class IcnHeader {
    public short height;
    public int offsetData;
    public short offsetX;
    public short offsetY;
    public byte type;
    public short width;

    public static IcnHeader ReadData(ByteVectorReader st) {
        var result = new IcnHeader();
        result.offsetX = (short) st.getLE16();
        result.offsetY = (short) st.getLE16();
        result.width = (short) st.getLE16();
        result.height = (short) st.getLE16();
        result.type = (byte) st.get();
        result.offsetData = st.getLE32();

        return result;
    }

    @Override
    public String toString() {
        return "IcnHeader{" +
                "type=" + type +
                ", width=" + width +
                ", height=" + height +
                ", offsetData=" + offsetData +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                '}';
    }
}
