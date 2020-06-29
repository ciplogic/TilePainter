package hellofx.fheroes2.objects;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;

public class ObjTree {

    static int[] shadows = {0, 3, 7, 10, 13, 17, 20, 23, 26, 29, 32, 34};

    public static int GetPassable(int index) {
        if (isShadow(index)) return Direction.DIRECTION_ALL;

        return 5 == index || 15 == index || 22 == index || 27 == index ? 0 : Direction.DIRECTION_CENTER_ROW | Direction.DIRECTION_BOTTOM_ROW;
    }

    static boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    static boolean isShadow(int index) {

        return contains(shadows, index);
    }

    static int GetActionObject(int index) {
        return Mp2Kind.OBJ_ZERO;
    }

}
