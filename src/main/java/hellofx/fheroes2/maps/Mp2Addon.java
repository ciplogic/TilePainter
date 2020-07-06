package hellofx.fheroes2.maps;

import hellofx.fheroes2.serialize.ByteVectorReader;

import static hellofx.fheroes2.serialize.ByteVectorReader.toUShort;
import static hellofx.fheroes2.serialize.ByteVectorReader.toUnsignedInt;

public class Mp2Addon {
    public short indexAddon; // zero or next addons_t
    public short objectNameN1; // level 1.N
    public short indexNameN1; // level 1.N or 0xFF
    public short quantityN; //
    public short objectNameN2; // level 2.N
    public short indexNameN2; // level 1.N or 0xFF
    public int uniqNumberN1; // level 1.N
    public int uniqNumberN2; // level 2.N

    public static Mp2Addon loadFromMp2Stream(ByteVectorReader fs) {
        var res = new Mp2Addon();
        res.indexAddon = (short) fs.getLE16();
        res.objectNameN1 = (short) (fs.get() * 2);
        res.indexNameN1 = (short) fs.get();
        res.quantityN = (short) fs.get();
        res.objectNameN2 = (short) fs.get();
        res.indexNameN2 = (short) fs.get();
        res.uniqNumberN1 = fs.getLE32();
        res.uniqNumberN2 = fs.getLE32();
        return res;
    }

    @Override
    public String toString() {
        return "Mp2Addon{" +
                "indexAddon=" + toUShort(indexAddon) +
                ", objectNameN1=" + (objectNameN1) +
                ", indexNameN1=" + (indexNameN1) +
                ", quantityN=" + (quantityN) +
                ", objectNameN2=" + (objectNameN2) +
                ", indexNameN2=" + (indexNameN2) +
                ", uniqNumberN1=" + toUnsignedInt(uniqNumberN1) +
                ", uniqNumberN2=" + toUnsignedInt(uniqNumberN2) +
                '}';
    }
}
