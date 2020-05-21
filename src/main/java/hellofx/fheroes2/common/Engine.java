package hellofx.fheroes2.common;

import hellofx.fheroes2.agg.AggFile;
import hellofx.fheroes2.agg.TilKind;

public class Engine {

    SurfaceList[] surfaceLists = new SurfaceList[TilKind.LASTTIL];

    public void loadTiles(AggFile agg) {
        for (var i = 0; i < TilKind.LASTTIL; i++) {
            SurfaceList surface = new SurfaceList();
            surface.loadFromTil(agg, i);
            surfaceLists[i] = surface;
        }
    }
}
