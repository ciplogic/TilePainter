package hellofx.fheroes2.objects;

import hellofx.common.Utilities;
import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.fheroes2.heroes.Direction.*;

public class ObjLav2 {

    static int[] shadows = {0, 7, 14, 29, 33, 44, 55, 78};

    public static int GetPassable(int index) {
        if (isShadow(index))
            return DIRECTION_ALL;
        if (isAction(index))
            return 0;

        return DIRECTION_CENTER_ROW | DIRECTION_BOTTOM_ROW;
    }

    static boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    static boolean isShadow(int index) {
        return Utilities.contains(shadows, index);
    }

    static int GetActionObject(int index) {
        return Mp2Kind.OBJ_ZERO;
    }
}
