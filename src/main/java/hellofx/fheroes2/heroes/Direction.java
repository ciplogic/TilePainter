package hellofx.fheroes2.heroes;

import hellofx.fheroes2.maps.objects.Maps;

public class Direction {
    public static final int UNKNOWN = 0x0000;
    public static final int TOP_LEFT = 0x0001;
    public static final int TOP = 0x0002;
    public static final int TOP_RIGHT = 0x0004;
    public static final int RIGHT = 0x0008;
    public static final int BOTTOM_RIGHT = 0x0010;
    public static final int BOTTOM = 0x0020;
    public static final int BOTTOM_LEFT = 0x0040;
    public static final int LEFT = 0x0080;
    public static final int CENTER = 0x0100;


    public static final int DIRECTION_TOP_ROW = (TOP_LEFT | TOP | TOP_RIGHT);
    public static final int DIRECTION_BOTTOM_ROW = (BOTTOM_LEFT | BOTTOM | BOTTOM_RIGHT);
    public static final int DIRECTION_CENTER_ROW = (LEFT | CENTER | RIGHT);
    public static final int DIRECTION_LEFT_COL = (TOP_LEFT | LEFT | BOTTOM_LEFT);
    public static final int DIRECTION_CENTER_COL = (TOP | CENTER | BOTTOM);
    public static final int DIRECTION_RIGHT_COL = (TOP_RIGHT | RIGHT | BOTTOM_RIGHT);
    public static final int DIRECTION_ALL = (DIRECTION_TOP_ROW | DIRECTION_BOTTOM_ROW | DIRECTION_CENTER_ROW);
    public static final int DIRECTION_AROUND = (DIRECTION_TOP_ROW | DIRECTION_BOTTOM_ROW | LEFT | RIGHT);

    public static final int DIRECTION_TOP_RIGHT_CORNER = (TOP | TOP_RIGHT | RIGHT);
    public static final int DIRECTION_TOP_LEFT_CORNER = (TOP | TOP_LEFT | LEFT);
    public static final int DIRECTION_BOTTOM_RIGHT_CORNER = (BOTTOM | BOTTOM_RIGHT | RIGHT);
    public static final int DIRECTION_BOTTOM_LEFT_CORNER = (BOTTOM | BOTTOM_LEFT | LEFT);
    final static int[] directs = {TOP_LEFT, TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT};

    public static int[] All() {
        return directs;
    }

    public static int Get(int from, int to) {
        var directions = All();
        for (var direction : directions) {
            if (to == Maps.GetDirectionIndex(from, direction))
                return direction;
        }
        return to == from ? CENTER : UNKNOWN;

    }
}
