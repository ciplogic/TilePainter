package hellofx.fheroes2.objects;

import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.heroes.Direction;

import static hellofx.common.Utilities.containsValue;

public class ObjMnts1 {
    static final int[] disabled2 = {6, 7, 8, 9, 14, 15, 16, 28, 29, 30, 31, 33, 34, 35, 47, 48, 56, 57, 64, 67, 68, 69, 82};
    static final int[] shadows1 = {0, 5, 11, 17, 21, 26, 32, 38, 42, 45, 49, 52, 55, 59, 62, 65, 68, 71, 74, 75, 79, 80};

    public static int GetPassable(int icn, int index) {
        //TODO

        if (isShadow(index)) return Direction.DIRECTION_ALL;
        // fix: disable passable: invalid top sprite
        if (icn == IcnKind.MTNGRAS &&
                (25 == index || 43 == index || 44 == index || 53 == index || 54 == index || 78 == index))
            return 0;

        return containsValue(disabled2, index)
                ? 0
                : Direction.DIRECTION_CENTER_ROW | Direction.DIRECTION_BOTTOM_ROW;
    }

    public static boolean isShadow(int index) {
        return containsValue(shadows1, index);
    }
}
