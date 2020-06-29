package hellofx.fheroes2.objects;

import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.*;

public class ObjSwmp {

    static int[] disabled = {
            88, 89, 90, 91, 94, 95, 96, 97, 98, 108, 109, 110, 112,
            113, 115, 116, 118, 119, 122, 123, 143, 144
    };
    static int[] restricted = {
            32, 33, 67, 74, 82, 85, 100, 101, 102, 103, 104, 105,
            126, 128, 129, 131, 133, 134, 135, 137, 138, 139, 145, 146, 147, 148, 149, 150,
            151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 166, 167, 171, 172, 176, 177,
            179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194,
            196, 198, 199, 200, 201, 203, 205, 208, 209, 212, 213
    };
    static int[] shadows = {
            14, 21, 31, 43, 66, 83, 125, 127, 130, 132, 136, 141, 163, 170,
            175, 178, 195, 197, 202, 204, 207, 211, 215
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
            case 22:
                return Mp2Kind.OBJ_WITCHSHUT;
            case 81:
                return Mp2Kind.OBJ_XANADU;
            case 84:
                return Mp2Kind.OBJ_FAERIERING;
            case 140:
                return Mp2Kind.OBJ_SIGN;
            case 216:
                return Mp2Kind.OBJ_OBELISK;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }

}
