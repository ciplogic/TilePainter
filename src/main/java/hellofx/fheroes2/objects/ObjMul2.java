package hellofx.fheroes2.objects;

import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.*;

public class ObjMul2 {

    static int[] disabled = {46, 76, 77, 124, 125, 126, 221, 213};

    static int[] restricted = {
            16, 18, 19, 25, 27, 51, 52, 53, 55, 57, 78, 79, 81, 98, 105, 128, 136, 187, 207, 209, 214,
            215, 217
    };
    static int[] shadows1 = {
            14, 17, 20, 24, 34, 36, 42, 43, 49, 50, 60, 71, 72, 113, 115, 118, 121, 123, 127,
            161, 164, 180, 181, 189, 199, 200, 202, 206
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

        return contains(shadows1, index);
    }

    static int GetActionObject(int index) {
        switch (index) {
            case 15:
                return Mp2Kind.OBJ_FOUNTAIN;
            case 26:
                return Mp2Kind.OBJ_ALCHEMYTOWER;
            case 54:
                return Mp2Kind.OBJ_DRAGONCITY;
            case 58:
                return Mp2Kind.OBJ_GRAVEYARD;
            case 73:
                return Mp2Kind.OBJ_LIGHTHOUSE;
            case 80:
                return Mp2Kind.OBJ_SAWMILL;
            case 112:
                return Mp2Kind.OBJ_WATERWHEEL;
            case 114:
                return Mp2Kind.OBJ_SIGN;
            case 116:
            case 119:
            case 122:
                return Mp2Kind.OBJ_STONELIGHTS;
            case 129:
                return Mp2Kind.OBJ_WAGONCAMP;
            case 162:
            case 165:
                return Mp2Kind.OBJ_MAGICWELL;
            case 188:
                return Mp2Kind.OBJ_FREEMANFOUNDRY;
            case 190:
                return Mp2Kind.OBJ_MAGICGARDEN;
            case 201:
                return Mp2Kind.OBJ_OBSERVATIONTOWER;
            case 208:
                return Mp2Kind.OBJ_GRAVEYARD;
            case 216:
                return Mp2Kind.OBJ_SAWMILL;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }
}
