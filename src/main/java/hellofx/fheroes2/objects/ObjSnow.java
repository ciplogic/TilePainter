package hellofx.fheroes2.objects;

import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.*;

public class ObjSnow {

    static int[] disabled = {22, 26, 27, 28, 30, 32, 34, 35, 37, 38, 39, 81, 82, 83, 84, 197, 198};
    static int[] restricted = {
            2, 12, 41, 42, 43, 44, 45, 49, 50, 55, 56, 57, 60, 64, 65, 68, 71, 74, 77, 80,
            85, 86, 87, 88, 89, 90, 91, 92, 94, 95, 132, 149, 151, 159, 177, 184, 199, 200, 202, 208,
            210
    };
    static int[] shadows = {
            21, 25, 29, 31, 33, 36, 40, 48, 54, 59, 63, 67, 70, 73, 76, 79,
            104, 108, 120, 124, 137, 140, 142, 144, 148, 193, 203, 207
    };

    public static int GetPassable(int index) {

        if (isShadow(index))
            return DIRECTION_ALL;
        if (isAction(index) ||
                contains(disabled, index))
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
                return Mp2Kind.OBJ_CAVE;
            case 13:
                return Mp2Kind.OBJ_LEANTO;
            case 128:
                return Mp2Kind.OBJ_WINDMILL;
            case 138:
                return Mp2Kind.OBJ_WATCHTOWER;
            case 141:
                return Mp2Kind.OBJ_OBELISK;
            case 143:
                return Mp2Kind.OBJ_SIGN;
            case 150:
                return Mp2Kind.OBJ_ALCHEMYTOWER;
            case 160:
                return Mp2Kind.OBJ_GRAVEYARD;
            case 191:
                return Mp2Kind.OBJ_WATERWHEEL;
            case 194:
                return Mp2Kind.OBJ_MAGICWELL;
            case 201:
                return Mp2Kind.OBJ_SAWMILL;
            case 209:
                return Mp2Kind.OBJ_GRAVEYARD;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }

}
