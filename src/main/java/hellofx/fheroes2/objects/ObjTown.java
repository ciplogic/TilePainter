package hellofx.fheroes2.objects;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.fheroes2.heroes.Direction.DIRECTION_ALL;

public class ObjTown {

    public static int GetPassable(int index0) {
        var index = index0 % 32;

        // 13, 29, 45, 61, 77, 93, 109, 125, 141, 157, 173, 189
        if (13 == index || 29 == index)
            return Direction.CENTER | Direction.BOTTOM;
        // town/castle
        if ((5 < index && index < 13) || (13 < index && index < 16) ||
                (21 < index && index < 29) || 29 < index)
            return 0;

        return DIRECTION_ALL;
    }

    boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    boolean isShadow(int index) {
        return false;
    }

    int GetActionObject(int index) {
        switch (index % 32) {
            case 13:
            case 29:
                return Mp2Kind.OBJ_CASTLE;
            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }

}
