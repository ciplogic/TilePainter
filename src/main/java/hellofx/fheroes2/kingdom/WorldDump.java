package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.maps.Tiles;
import hellofx.fheroes2.serialize.FileUtils;

public class WorldDump {
    public static void toFile(String fileName, Tiles[] vec_tiles) {
        var sb = new StringBuilder();
        sb.append("[");
        for (var tile : vec_tiles) {
            String tileAsText = vecToRow(tile);
            sb.append(tileAsText).append(",\n");
        }

        FileUtils.writeFile(fileName, sb.toString().getBytes());
    }

    private static String vecToRow(Tiles tile) {
        return tile.toString();
    }
}
