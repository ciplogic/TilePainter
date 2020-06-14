package hellofx.fheroes2.maps;

import hellofx.fheroes2.serialize.ByteVectorReader;

import static hellofx.fheroes2.serialize.ByteVectorReader.*;

public class Mp2Addon {
    public short indexAddon; // zero or next addons_t
    public byte objectNameN1; // level 1.N
    public byte indexNameN1; // level 1.N or 0xFF
    public byte quantityN; //
    public byte objectNameN2; // level 2.N
    public byte indexNameN2; // level 1.N or 0xFF
    public int uniqNumberN1; // level 1.N
    public int uniqNumberN2; // level 2.N

    public static Mp2Addon loadFromMp2Stream(ByteVectorReader fs) {
        var res = new Mp2Addon();
        res.indexAddon = (short) fs.getLE16();
        res.objectNameN1 = (byte) (fs.get() * 2);
        res.indexNameN1 = (byte) fs.get();
        res.quantityN = (byte) fs.get();
        res.objectNameN2 = (byte) fs.get();
        res.indexNameN2 = (byte) fs.get();
        res.uniqNumberN1 = fs.getLE32();
        res.uniqNumberN2 = fs.getLE32();
        return res;
    }

    @Override
    public String toString() {
        return "Mp2Addon{" +
                "indexAddon=" + toUShort(indexAddon) +
                ", objectNameN1=" + toByte(objectNameN1) +
                ", indexNameN1=" + toByte(indexNameN1) +
                ", quantityN=" + toByte(quantityN) +
                ", objectNameN2=" + toByte(objectNameN2) +
                ", indexNameN2=" + toByte(indexNameN2) +
                ", uniqNumberN1=" + toUnsignedInt(uniqNumberN1) +
                ", uniqNumberN2=" + toUnsignedInt(uniqNumberN2) +
                '}';
    }
}
