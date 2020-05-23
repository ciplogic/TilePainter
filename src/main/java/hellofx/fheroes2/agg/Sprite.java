package hellofx.fheroes2.agg;

import hellofx.fheroes2.common.H2Point;

class Sprite extends SpritePos {

    public Sprite(Bitmap bitmap, int x, int y) {
        super(bitmap, new H2Point(x, y));
    }
}