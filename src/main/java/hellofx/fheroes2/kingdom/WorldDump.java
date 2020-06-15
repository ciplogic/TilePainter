package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.maps.Tiles;
import hellofx.fheroes2.serialize.FileUtils;

public class WorldDump {
    public static void toFile(String fileName, Tiles[] vec_tiles) {
        var sb = new StringBuilder();
        sb.append("[");
        for (var tile : vec_tiles) {
            String tileAsText = tile.toJsonRow();
            sb.append(tileAsText).append(",\n");
        }
        sb.append("]");

        FileUtils.writeFile(fileName, sb.toString().getBytes());
    }

    public static void writeField(StringBuilder sb, String field, String value) {
        sb.append("\"").append(field).append("\": ").append(value).append(",");
    }

    public static void writeField(StringBuilder sb, String field, int value) {
        writeFieldBare(sb, field, value);
        sb.append(",");
    }

    public static void writeFieldBare(StringBuilder sb, String field, int value) {
        sb.append("\"").append(field).append("\": ").append(value);
    }
}
