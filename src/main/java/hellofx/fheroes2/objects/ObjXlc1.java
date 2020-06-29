package hellofx.fheroes2.objects;

import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.*;

public class ObjXlc1 {
    static int[] disabled = {40, 49, 50};
    static int[] restricted = {69, 71, 75, 76, 85, 103, 117, 119, 126, 128, 134, 136};
    static int[] shadows = {1, 2, 59, 68, 72, 78, 79, 83, 84, 112, 116, 120, 124, 125, 129, 133};

    public static int GetPassable(int index) {
        if (isShadow(index))
            return DIRECTION_ALL;
        if (isAction(index) || contains(disabled, index))
            return 0;

        return contains(restricted, index)
                ? DIRECTION_CENTER_ROW | DIRECTION_BOTTOM_ROW
                : DIRECTION_ALL;
    }

    static boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    static boolean isShadow(int index) {
        return contains(shadows, index);
    }

    static int GetActionObject(int index) {
        switch (index) {
            case 3:
                return Mp2Kind.OBJ_ALCHEMYTOWER;
            case 70:
                return Mp2Kind.OBJ_ARENA;
            case 77:
                return Mp2Kind.OBJ_BARROWMOUNDS;
            case 94:
                return Mp2Kind.OBJ_EARTHALTAR;
            case 118:
                return Mp2Kind.OBJ_AIRALTAR;
            case 127:
                return Mp2Kind.OBJ_FIREALTAR;
            case 135:
                return Mp2Kind.OBJ_WATERALTAR;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }
}
