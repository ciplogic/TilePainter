package hellofx.fheroes2.objects;

import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.*;

public class ObjXlc2 {

    static int[] restricted = {3, 8, 28, 46, 92, 102};
    static int[] shadows = {2, 10, 47, 83};

    public static int GetPassable(int index) {

        if (isShadow(index))
            return DIRECTION_ALL;
        if (isAction(index) ||
                (110 < index && index < 136))
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
            case 4:
                return Mp2Kind.OBJ_STABLES;
            case 9:
                return Mp2Kind.OBJ_JAIL;
            case 37:
                return Mp2Kind.OBJ_MERMAID;
            case 101:
                return Mp2Kind.OBJ_SIRENS;
            default:
                break;
        }
        return Mp2Kind.OBJ_ZERO;
    }
}
