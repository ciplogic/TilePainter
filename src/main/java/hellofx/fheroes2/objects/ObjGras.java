package hellofx.fheroes2.objects;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;

public class ObjGras {

    static int[] restricted = {2, 3, 6, 8, 22, 59};
    static int[] shadows1 = {5, 19, 20, 31, 33, 47, 51, 70, 77, 91, 100, 107, 124, 128};

    public static int GetPassable(int index) {
        if (isShadow(index))
            return Direction.DIRECTION_ALL;
        if (isAction(index))
            return 0;

        return contains(restricted, index)
                ? Direction.DIRECTION_CENTER_ROW | Direction.DIRECTION_BOTTOM_ROW
                : Direction.DIRECTION_ALL;
    }

    static boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    static boolean isShadow(int index) {
        return contains(shadows1, index);
    }

    static int GetActionObject(int index) {
        switch (index) {
            case 6:
                return Mp2Kind.OBJ_ABANDONEDMINE;
            case 30:
                return Mp2Kind.OBJ_FAERIERING;
            default:
                return Mp2Kind.OBJ_ZERO;
        }
    }

}
