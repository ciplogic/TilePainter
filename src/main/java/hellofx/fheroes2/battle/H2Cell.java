package hellofx.fheroes2.battle;

import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.H2Rect;

public class H2Cell {
    public H2Rect pos = new H2Rect();
    public int object;
    public int direction;
    public int quality;
    public Unit troop;
    public H2Point[] coord = new H2Point[7];
    int index;
}
