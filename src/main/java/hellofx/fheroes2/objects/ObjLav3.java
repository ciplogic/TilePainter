package hellofx.fheroes2.objects;

import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.*;

public class ObjLav3 {
    static int[] shadows = {0, 15, 30, 45, 60, 75, 90, 105, 120, 135, 165, 180, 195, 210, 225, 243};

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

    static int GetActionObject(int index) {
        return Mp2Kind.OBJ_ZERO;
    }

    static boolean isShadow(int index) {
        return contains(shadows, index);
    }
}
