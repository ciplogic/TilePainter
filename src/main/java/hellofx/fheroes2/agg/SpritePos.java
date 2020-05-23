package hellofx.fheroes2.agg;

import hellofx.fheroes2.common.H2Point;

public class SpritePos extends Bitmap {
    public final H2Point pos;

    public SpritePos(Bitmap bitmap, H2Point pt) {
        super(bitmap);
        pos = new H2Point(pt);
    }
}
