package hellofx.fheroes2.common;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.TilKind;

public class Engine {

    public SurfaceList[] surfaces = new SurfaceList[TilKind.LASTTIL];

    public void loadTiles(Agg agg) {
        for (var i = 0; i < TilKind.LASTTIL; i++) {
            SurfaceList surface = new SurfaceList();
            surface.loadFromTil(agg, i);
            surfaces[i] = surface;
        }
    }
}
