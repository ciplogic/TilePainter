package hellofx.fheroes2.objects;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;

public class ObjDsrt {

    static int[] disabled = {61, 89, 90, 91, 92, 93, 125, 126};
    static int[] restricted = {
            3, 6, 9, 12, 14, 15, 17, 18, 20, 21, 22, 24, 26, 28, 30, 31, 32, 34,
            36, 39, 40, 42, 45, 48, 49, 51, 53, 72, 76, 81, 83, 94, 95, 97, 98, 99, 100, 110, 111, 112,
            116, 121, 127, 128, 130
    };
    static int[] shadows = {
            11, 13, 16, 19, 23, 25, 27, 29, 33, 35, 38, 41, 44, 46, 47,
            50, 52, 54, 71, 75, 77, 80, 86, 103, 115, 118
    };

    public static int GetPassable(int index) {

        if (isShadow(index))
            return Direction.DIRECTION_ALL;
        if (isAction(index) ||
                contains(disabled, index))
            return 0;

        return contains(restricted, index)
                ? Direction.DIRECTION_CENTER_ROW | Direction.DIRECTION_BOTTOM_ROW
                : Direction.DIRECTION_ALL;
    }

    static boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    static boolean isShadow(int index) {
        return contains(shadows, index);
    }

    static int GetActionObject(int index) {
        switch (index) {
            case 73:
                return Mp2Kind.OBJ_DESERTTENT;
            case 82:
                return Mp2Kind.OBJ_PYRAMID;
            case 84:
                return Mp2Kind.OBJ_SKELETON;
            case 87:
                return Mp2Kind.OBJ_SPHINX;
            case 96:
                return Mp2Kind.OBJ_CITYDEAD;
            case 101:
                return Mp2Kind.OBJ_EXCAVATION;
            case 104:
                return Mp2Kind.OBJ_OBELISK;
            case 108:
            case 109:
                return Mp2Kind.OBJ_OASIS;
            case 117:
                return Mp2Kind.OBJ_DAEMONCAVE;
            case 119:
                return Mp2Kind.OBJ_SIGN;
            case 122:
                return Mp2Kind.OBJ_GRAVEYARD;
            case 129:
                return Mp2Kind.OBJ_SAWMILL;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }

}
