package hellofx.fheroes2.objects;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.fheroes2.heroes.Direction.*;

public class ObjTwba {


    public static int GetPassable(int index0) {
        int index = index0 % 10;

        // 2, 12, 22, 32, 42, 52, 62, 72
        if (2 == index)
            return Direction.CENTER | Direction.BOTTOM;
        if (index < 5)
            return 0;
        // 7, 17, 27, 37, 47, 57, 67, 77
        if (7 == index)
            return DIRECTION_CENTER_ROW | DIRECTION_BOTTOM_ROW | Direction.TOP;
        if (4 < index)
            return DIRECTION_CENTER_ROW | DIRECTION_BOTTOM_ROW;

        return DIRECTION_ALL;
    }

    boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    boolean isShadow(int index) {
        return false;
    }

    int GetActionObject(int index) {
        return Mp2Kind.OBJ_ZERO;
    }

}
