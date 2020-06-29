package hellofx.fheroes2.objects;

import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.DIRECTION_ALL;

public class ObjXlc3 {

    static int[] shadows = {
            0, 9, 20, 29, 41, 59, 65, 71, 77, 83, 89, 95, 101,
            108, 109, 112, 113, 116, 117, 120, 121, 124, 125, 128, 129, 132, 133, 136, 137
    };

    public static int GetPassable(int index) {
        if (isShadow(index))
            return DIRECTION_ALL;
        if (isAction(index))
            return 0;

        return DIRECTION_ALL;
    }

    static boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    static boolean isShadow(int index) {
        return contains(shadows, index);
    }

    static int GetActionObject(int index) {
        switch (index) {
            case 30:
                return Mp2Kind.OBJ_HUTMAGI;
            case 50:
                return Mp2Kind.OBJ_EYEMAGI;
            case 60:
            case 66:
            case 72:
            case 78:
            case 84:
            case 90:
            case 96:
            case 102:
                return Mp2Kind.OBJ_BARRIER;
            case 110:
            case 114:
            case 118:
            case 122:
            case 126:
            case 130:
            case 134:
            case 138:
                return Mp2Kind.OBJ_TRAVELLERTENT;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }

}
