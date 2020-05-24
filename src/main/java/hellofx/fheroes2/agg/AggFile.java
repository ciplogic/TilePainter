package hellofx.fheroes2.agg;

import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.serialize.FileUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class AggFile {
    private static final int FATSIZENAME = 15;

    private final Map<String, AggFat> fat = new TreeMap<>();

    private String filename;
    private ByteVectorReader stream;


    public boolean Open(String aggFileName) {
        filename = aggFileName;
        if (!FileUtils.Exists(aggFileName))
            return false;

        var fileContent = FileUtils.ReadAllBytes(filename);
        var size = fileContent.length;
        stream = new ByteVectorReader(fileContent);

        var count_items = stream.getLE16();

        stream.seek(size - FATSIZENAME * count_items);
        var vectorNames = new ArrayList<String>(count_items);

        for (var ii = 0; ii < count_items; ++ii) vectorNames.add(stream.toString(FATSIZENAME));

        stream.seek(2);
        fat.clear();
        for (var ii = 0; ii < count_items; ++ii) {
            var itemName = vectorNames.get(ii);
            var f = new AggFat();
            var crc = stream.getLE32();
            f.crc = crc;
            var offset = stream.getLE32();
            f.offset = offset;
            var sizeChunk = stream.getLE32();
            f.size = sizeChunk;
            fat.put(itemName, f);
        }

        return true;
    }

    public byte[] Read(String fileName) {
        var f = fat.get(fileName);
        if (f == null) return new byte[0];
        if (f.size == 0) return new byte[0];

        stream.seek(f.offset);
        var body = stream.getRaw(f.size);
        return body;
    }

}