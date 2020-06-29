package hellofx.fheroes2.objects;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;

public class ObjGra2 {

    static int[] disabled = {54, 55, 56, 57, 58, 65, 66, 67, 68};
    static int[] restricted = {
            5, 7, 31, 33, 34, 37, 38, 40, 41, 43, 45, 47, 49, 59,
            60, 61, 62, 63, 69, 70, 71, 72, 73, 74, 75, 77, 78, 83, 84, 85, 89, 90, 93, 95,
            96, 97, 99, 100, 101, 103, 104, 106, 107, 109, 110, 112, 114, 115, 116, 121, 122,
            123, 125, 126, 127, 129, 130, 131, 135, 136, 139, 140, 142, 144, 146, 148, 149, 150,
    };
    static int[] shadows2 = {
            0, 4, 29, 32, 36, 39, 42, 44, 46, 48, 50, 76, 79, 82, 88, 92, 94, 98, 102, 105,
            108, 111, 113, 120, 124, 128, 134, 138, 141, 143, 145, 147
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
        return contains(shadows2, index);
    }

    static int GetActionObject(int index) {
        switch (index) {
            case 4:
                return Mp2Kind.OBJ_HILLFORT;
            case 7:
                return Mp2Kind.OBJ_HALFLINGHOLE;
            case 21:
                return Mp2Kind.OBJ_TREECITY;
            case 55:
                return Mp2Kind.OBJ_WINDMILL;
            case 84:
                return Mp2Kind.OBJ_ARCHERHOUSE;
            case 92:
                return Mp2Kind.OBJ_GOBLINHUT;
            case 114:
                return Mp2Kind.OBJ_DWARFCOTT;
            case 125:
            case 126:
                return Mp2Kind.OBJ_ORACLE;
            case 129:
                return Mp2Kind.OBJ_OBELISK;
            default:
                return Mp2Kind.OBJ_ZERO;
        }
    }

}
