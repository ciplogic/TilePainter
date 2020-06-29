package hellofx.fheroes2.objects;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;

public class ObjDirt {
    static int[] disabled = {23, 24, 25, 26, 27, 28, 29, 30, 32, 33, 34, 35, 36, 37, 48, 49, 50, 51};
    static int[] restricted = {
            7, 9, 12, 13, 15, 16, 17, 18, 19, 20, 21, 22, 40, 41, 42, 43, 44, 45,
            53, 54, 55, 56, 57, 58, 60, 61, 63, 64, 66, 67, 69, 71, 73, 74, 76, 77, 79, 80, 82, 83, 85,
            86, 88, 89, 90, 92, 93, 98, 99, 101, 102, 104, 105, 118, 123, 127, 130, 133, 134, 137, 139,
            152, 189
    };
    static int[] shadows = {
            0, 1, 5, 6, 14, 47, 52, 59, 62, 65, 68, 70, 72, 75, 78, 81, 84,
            87, 91, 94, 97, 100, 103, 111, 114, 117, 126, 128, 136, 149, 150, 161, 165, 177, 181, 196,
            200
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
            case 8:
                return Mp2Kind.OBJ_ABANDONEDMINE;
            case 129:
                return Mp2Kind.OBJ_FAERIERING;
            case 135:
                return Mp2Kind.OBJ_HILLFORT;
            case 138:
                return Mp2Kind.OBJ_HALFLINGHOLE;
            case 151:
                return Mp2Kind.OBJ_TREECITY;
            case 185:
                return Mp2Kind.OBJ_WINDMILL;
            case 197:
            case 198:
                return Mp2Kind.OBJ_ORACLE;
            case 201:
                return Mp2Kind.OBJ_OBELISK;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }
}
