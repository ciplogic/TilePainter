package hellofx.fheroes2.objects;

import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.*;

public class ObjMult {

    static int[] restricted = {2, 4, 58, 63, 64, 65, 70, 72, 73, 89, 104};
    static int[] shadows2 = {
            1, 3, 15, 25, 45, 54, 57, 61, 67, 68, 75, 77, 79, 81, 83,
            97, 98, 105, 113, 115, 121, 122, 124
    };

    public static int GetPassable(int index) {
        if (isShadow(index))
            return DIRECTION_ALL;
        if (isAction(index))
            return 0;

        return contains(restricted, index)
                ? DIRECTION_CENTER_ROW | DIRECTION_BOTTOM_ROW
                : DIRECTION_ALL;
    }

    static boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    static boolean isShadow(int index) {
        return contains(shadows2, index);
    }

    static int GetActionObject(int index) {
        switch (index) {
            case 35:
                return Mp2Kind.OBJ_PEASANTHUT;
            case 59:
                return Mp2Kind.OBJ_FORT;
            case 62:
                return Mp2Kind.OBJ_GAZEBO;
            case 69:
                return Mp2Kind.OBJ_WITCHSHUT;
            case 71:
                return Mp2Kind.OBJ_MERCENARYCAMP;
            case 74:
                return Mp2Kind.OBJ_RUINS;
            case 76:
                return Mp2Kind.OBJ_SHRINE1;
            case 78:
                return Mp2Kind.OBJ_SHRINE2;
            case 80:
                return Mp2Kind.OBJ_SHRINE3;
            case 82:
                return Mp2Kind.OBJ_IDOL;
            case 84:
            case 85:
                return Mp2Kind.OBJ_STANDINGSTONES;
            case 88:
                return Mp2Kind.OBJ_TEMPLE;
            case 111:
                return Mp2Kind.OBJ_TRADINGPOST;
            case 114:
                return Mp2Kind.OBJ_TREEHOUSE;
            case 116:
                return Mp2Kind.OBJ_WATCHTOWER;
            case 123:
                return Mp2Kind.OBJ_TREEKNOWLEDGE;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }

}
