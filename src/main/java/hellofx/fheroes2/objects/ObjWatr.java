package hellofx.fheroes2.objects;

import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.*;

public class ObjWatr {

    static int[] disabled = {11, 12, 19, 22};
    static int[] restricted = {69, 182, 183, 185, 186, 187, 248};
    static int[] shadows = {12, 38, 52, 55, 118, 166, 188, 240};

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
            case 62:
                return Mp2Kind.OBJ_MAGELLANMAPS;
            case 195:
                return Mp2Kind.OBJ_BUOY;
            case 202:
            case 206:
            case 210:
            case 214:
            case 218:
            case 222:
                return Mp2Kind.OBJ_WHIRLPOOL;
            case 241:
                return Mp2Kind.OBJ_SHIPWRECK;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }

}
