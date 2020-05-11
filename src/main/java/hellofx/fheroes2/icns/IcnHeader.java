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
        result.offsetData = (short) st.getLE16();
        result.offsetX = (short) st.getLE32();
        result.offsetY = (short) st.getLE16();
        result.type = (byte) st.get();
        result.width = (short) st.getLE16();


        return result;
    }

    @Override
    public String toString() {
        return "IcnHeader{" +
                "height=" + height +
                ", offsetData=" + offsetData +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", type=" + type +
                ", width=" + width +
                '}';
    }
}
