package hellofx.fheroes2.objects;

import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.*;

public class ObjLava {
    static int[] disabled = {
            2, 3, 4, 5, 12, 13, 14, 15, 18, 27, 28, 29, 30, 31, 32, 39, 40,
            41, 46, 47, 48, 53, 54, 57, 60, 61, 64, 65, 69, 70, 120, 121
    };

    static int[] restricted = {
            6, 7, 8, 9, 16, 17, 19, 20, 33, 34, 35, 36, 37, 38, 42, 43, 44,
            50, 51, 52, 55, 56, 58, 59, 62, 66, 67, 68, 72, 73, 76, 77, 88, 98, 114, 122, 123, 125
    };
    static int[] shadows = {10, 11, 45, 49, 77, 109, 113, 116};

    public static int GetPassable(int index) {

        if (isAction(index) || contains(disabled, index))
            return 0;

        return contains(restricted, index)
                ? DIRECTION_CENTER_ROW | DIRECTION_BOTTOM_ROW
                : DIRECTION_ALL;
    }

    static boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    static int GetActionObject(int index) {
        switch (index) {
            case 110:
                return Mp2Kind.OBJ_OBELISK;
            case 115:
                return Mp2Kind.OBJ_DAEMONCAVE;
            case 117:
                return Mp2Kind.OBJ_SIGN;
            case 124:
                return Mp2Kind.OBJ_SAWMILL;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }

    boolean isShadow(int index) {
        return contains(shadows, index);
    }
}
