package hellofx.fheroes2.objects;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.maps.Mp2Kind;

import static hellofx.common.Utilities.contains;

public class ObjCrck {
    static final int[] shadows = {
            0, 2, 9, 12, 13, 15, 20, 23, 28, 33, 36, 39,
            45, 48, 51, 54, 56, 73, 75, 79, 190, 201, 237
    };
    static int[] disabled = {
            58, 59, 63, 64, 65, 76, 77, 78, 80, 91, 102, 113, 124,
            135, 182, 183, 185, 221, 222, 223, 227, 228, 229, 230, 238, 241, 242, 245
    };
    static int[] restricted = {
            5, 6, 10, 11, 14, 16, 17, 18, 21, 22, 24, 25, 29, 30, 31, 32, 34,
            35, 37, 38, 40, 41, 42, 43, 46, 49, 52, 55, 57, 62, 67, 68, 69, 71, 72, 136, 148, 159,
            170, 181, 186, 187, 188, 202, 224, 225, 226, 231, 232, 233, 234, 235, 243, 244, 246
    };

    public static int GetPassable(int index) {

        if (isShadow(index))
            return Direction.DIRECTION_ALL;
        if (184 == index)
            return Direction.CENTER | Direction.BOTTOM_RIGHT | Direction.DIRECTION_TOP_ROW;
        if (isAction(index) || contains(disabled, index))
            return 0;

        return contains(restricted, index)
                ? Direction.DIRECTION_CENTER_ROW | Direction.DIRECTION_BOTTOM_ROW
                : Direction.DIRECTION_ALL;
    }

    private static boolean isAction(int index) {
        return Mp2Kind.OBJ_ZERO != GetActionObject(index);
    }

    private static int GetActionObject(int index) {

    /*
	artesian spring: 3, 4
	wagon: 74
	troll bridge: 189
	market: 213
	watering hole: 217, 218, 219, 220
	obelisk: 238
	saw mill: 245
    */

        switch (index) {
            case 3:
            case 4:
                return Mp2Kind.OBJ_ARTESIANSPRING;
            case 74:
                return Mp2Kind.OBJ_WAGON;
            case 189:
                return Mp2Kind.OBJ_TROLLBRIDGE;
            case 213:
                return Mp2Kind.OBJ_TRADINGPOST;
            case 217:
            case 218:
            case 219:
            case 220:
                return Mp2Kind.OBJ_WATERINGHOLE;
            case 238:
                return Mp2Kind.OBJ_OBELISK;
            case 245:
                return Mp2Kind.OBJ_SAWMILL;
        }

        return Mp2Kind.OBJ_ZERO;
    }

    static boolean isShadow(int index) {
        return contains(shadows, i -> i == index);
    }
}
