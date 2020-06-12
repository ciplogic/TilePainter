package hellofx.fheroes2.maps;

import hellofx.fheroes2.kingdom.H2Color;

public class ObjectColor {
    public int obj;
    public int color;

    public ObjectColor() {
        obj = Mp2Kind.OBJ_ZERO;
        color = H2Color.NONE;
    }

    public ObjectColor(int obj, int col) {
        this.obj = obj;
        this.color = col;
    }
}
