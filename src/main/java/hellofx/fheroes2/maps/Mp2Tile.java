package hellofx.fheroes2.maps;

import hellofx.fheroes2.serialize.ByteVectorReader;

public class Mp2Tile {
    public short tileIndex; // tile (ocean, grass, snow, swamp, lava, desert, dirt, wasteland, beach)
    public short objectName1; // level 1.0
    public short indexName1; // index level 1.0 or 0xFF
    public short quantity1; // count
    public short quantity2; // count
    public short objectName2; // level 2.0
    public short indexName2; // index level 2.0 or 0xFF
    public short shape; // shape reflect % 4, 0 none, 1 vertical, 2 horizontal, 3 any
    public short generalObject; // zero or object
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
                ", indexName2=" + (indexName2) +
                ", shape=" + shape +
                ", generalObject=" + (generalObject) +
                ", indexAddon=" + indexAddon +
                ", uniqNumber1=" + uniqNumber1 +
                ", uniqNumber2=" + uniqNumber2 +
                '}';
    }

    public int loadFromMp2Stream(ByteVectorReader fs) {
        tileIndex = (short) fs.getLE16();
        objectName1 = (short) fs.get();
        indexName1 = (short) fs.get();
        quantity1 = (short) fs.get();
        quantity2 = (short) fs.get();
        objectName2 = (short) fs.get();
        indexName2 = (short) fs.get();
        shape = (short) fs.get();
        generalObject = (short) fs.get();


        // offset first addon
        var offsetAddonsBlock = fs.getLE16();

        uniqNumber1 = fs.getLE32();
        uniqNumber2 = fs.getLE32();

        return offsetAddonsBlock;
    }
}
