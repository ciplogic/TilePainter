package hellofx.fheroes2.objects;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;
import static hellofx.fheroes2.heroes.Direction.*;

public class ObjWat2 {

    static int[] disabled = {11, 12, 19, 22};
    static int[] restricted = {2, 20};

    public static int GetPassable(int index) {

        if (isShadow(index))
            return DIRECTION_ALL;
        if (10 == index)
            return Direction.CENTER | Direction.TOP | Direction.LEFT | Direction.TOP_LEFT;
        if (22 == index)
            return DIRECTION_CENTER_ROW | Direction.BOTTOM | Direction.BOTTOM_LEFT;
        if (isAction(index) || contains(disabled, index))
            return 0;

        return contains(restricted, index)
                ? DIRECTION_CENTER_ROW | DIRECTION_BOTTOM_ROW
                : DIRECTION_ALL;
    }

    static boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    static boolean isShadow(int index) {
        return index == 1;
    }


    static int GetActionObject(int index) {
        switch (index) {
            case 21:
                return Mp2Kind.OBJ_DERELICTSHIP;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }

}
