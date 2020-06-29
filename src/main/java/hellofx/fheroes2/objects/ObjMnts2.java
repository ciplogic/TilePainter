package hellofx.fheroes2.objects;

import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.heroes.Direction;

import static hellofx.common.Utilities.contains;

public class ObjMnts2 {
    static final int[] disabled1 = {
            6, 7, 8, 9, 14, 15, 16, 28, 29, 30, 31, 33, 34, 35, 50, 51, 52, 65, 77, 78, 87, 94, 99,
            112
    };
    static final int[] shadows2 = {
            0, 5, 11, 17, 21, 26, 32, 38, 42, 46, 47, 53, 57, 58, 62,
            68, 72, 75, 79, 82, 85, 89, 92, 95, 98, 101, 104, 105, 109, 110
    };

    public static int GetPassable(int icn, int index) {

        if (isShadow(index)) return Direction.DIRECTION_ALL;
        // fix: disable passable: invalid top sprite
        if (icn == IcnKind.MTNDIRT &&
                (73 == index || 84 == index || 86 == index))
            return 0;

        return contains(disabled1, index)
                ? 0
                : Direction.DIRECTION_CENTER_ROW | Direction.DIRECTION_BOTTOM_ROW;
    }

    public static boolean isShadow(int index) {
        return contains(shadows2, index);
    }
}
