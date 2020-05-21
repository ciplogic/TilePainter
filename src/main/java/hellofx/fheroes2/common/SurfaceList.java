package hellofx.fheroes2.common;

import hellofx.fheroes2.agg.AggFile;
import hellofx.fheroes2.agg.TilKind;
import hellofx.fheroes2.serialize.ByteVectorReader;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class SurfaceList {
    public List<Image> _surfaces = new ArrayList<>();

    public SurfaceList() {

    }

    public boolean loadFromTil(AggFile agg, int tile) {
        var tileName = TilKind.GetString(tile);
        byte[] body = agg.Read(tileName);

        if (body.length == 0)
            return false;
        var st = new ByteVectorReader(body);

        int count = st.getLE16();
        int width = st.getLE16();
        int height = st.getLE16();
        int tile_size = width * height;
        int body_size = 6 + count * tile_size;

        var max = TilKind.GetTileCount(tile);

        // check size
        if (body.length == body_size && count <= max) {
            for (var ii = 0; ii < count; ++ii) {
                var bitmap = agg.RenderImageFromArray(body, 6 + ii * tile_size, width, height);
                var image = bitmap.doublePictureAa().toImage();
                //var image = bitmap.doublePicture().toImage();
                _surfaces.add(image);
            }

            return true;
        }

        return false;
    }
}
