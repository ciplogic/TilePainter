package hellofx.fheroes2.maps;

import hellofx.fheroes2.serialize.ByteVectorReader;

import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class Mp2Tile {
    public short tileIndex; // tile (ocean, grass, snow, swamp, lava, desert, dirt, wasteland, beach)
    public byte objectName1; // level 1.0
    public byte indexName1; // index level 1.0 or 0xFF
    public byte quantity1; // count
    public byte quantity2; // count
    public byte objectName2; // level 2.0
    public byte indexName2; // index level 2.0 or 0xFF
    public byte shape; // shape reflect % 4, 0 none, 1 vertical, 2 horizontal, 3 any
    public byte generalObject; // zero or object
    public short indexAddon; // zero or index addons_t
    public int uniqNumber1; // level 1.0
    public int uniqNumber2; // level 2.0

    @Override
    public String toString() {
        return "Mp2Tile{" +
                "tileIndex=" + tileIndex +
                ", objectName1=" + objectName1 +
                ", indexName1=" + indexName1 +
                ", quantity1=" + quantity1 +
                ", quantity2=" + quantity2 +
                ", objectName2=" + objectName2 +
                ", indexName2=" + toByte(indexName2) +
                ", shape=" + shape +
                ", generalObject=" + toByte(generalObject) +
                ", indexAddon=" + indexAddon +
                ", uniqNumber1=" + uniqNumber1 +
                ", uniqNumber2=" + uniqNumber2 +
                '}';
    }

    public int loadFromMp2Stream(ByteVectorReader fs) {
        tileIndex = (short) fs.getLE16();
        objectName1 = (byte) fs.get();
        indexName1 = (byte) fs.get();
        quantity1 = (byte) fs.get();
        quantity2 = (byte) fs.get();
        objectName2 = (byte) fs.get();
        indexName2 = (byte) fs.get();
        shape = (byte) fs.get();
        generalObject = (byte) fs.get();


        // offset first addon
        var offsetAddonsBlock = fs.getLE16();

        uniqNumber1 = fs.getLE32();
        uniqNumber2 = fs.getLE32();

        return offsetAddonsBlock;
    }
}
