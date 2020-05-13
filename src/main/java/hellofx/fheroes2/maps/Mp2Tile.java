package hellofx.fheroes2.maps;

import hellofx.fheroes2.serialize.ByteVectorReader;

public class Mp2Tile {
    public static short tileIndex; // tile (ocean, grass, snow, swamp, lava, desert, dirt, wasteland, beach)
    public static byte objectName1; // level 1.0
    public static byte indexName1; // index level 1.0 or 0xFF
    public static byte quantity1; // count
    public static byte quantity2; // count
    public static byte objectName2; // level 2.0
    public static byte indexName2; // index level 2.0 or 0xFF
    public static byte shape; // shape reflect % 4, 0 none, 1 vertical, 2 horizontal, 3 any
    public static byte generalObject; // zero or object
    public static short indexAddon; // zero or index addons_t
    public static int uniqNumber1; // level 1.0
    public static int uniqNumber2; // level 2.0

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