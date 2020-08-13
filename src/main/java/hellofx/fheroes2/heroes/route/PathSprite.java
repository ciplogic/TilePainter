package hellofx.fheroes2.heroes.route;

import hellofx.fheroes2.heroes.Direction;

public class PathSprite {
    public static int GetIndexSprite(int from, int to, int mod) {
        // ICN::ROUTE
        // start index 1, 25, 49, 73, 97, 121 (size arrow path)
        int index = 1;

        switch (mod) {
            case 200:
                index = 121;
                break;
            case 175:
                index = 97;
                break;
            case 150:
                index = 73;
                break;
            case 125:
                index = 49;
                break;
            case 100:
                index = 25;
                break;

            default:
                break;
        }

        switch (from) {
            case Direction.TOP:
                switch (to) {
                    case Direction.TOP:
                        index += 8;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 17;
                        break;
                    case Direction.RIGHT:
                        index += 18;
                        break;
                    case Direction.LEFT:
                        index += 6;
                        break;
                    case Direction.TOP_LEFT:
                        index += 7;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 5;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 19;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.TOP_RIGHT:
                switch (to) {
                    case Direction.TOP:
                        index += 0;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 9;
                        break;
                    case Direction.RIGHT:
                        index += 18;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 19;
                        break;
                    case Direction.TOP_LEFT:
                        index += 7;
                        break;
                    case Direction.BOTTOM:
                        index += 20;
                        break;
                    case Direction.LEFT:
                        index += 6;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.RIGHT:
                switch (to) {
                    case Direction.TOP:
                        index += 0;
                        break;
                    case Direction.BOTTOM:
                        index += 20;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 19;
                        break;
                    case Direction.RIGHT:
                        index += 10;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 1;
                        break;
                    case Direction.TOP_LEFT:
                        index += 7;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 21;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.BOTTOM_RIGHT:
                switch (to) {
                    case Direction.TOP_RIGHT:
                        index += 1;
                        break;
                    case Direction.RIGHT:
                        index += 2;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 11;
                        break;
                    case Direction.BOTTOM:
                        index += 20;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 21;
                        break;
                    case Direction.TOP:
                        index += 0;
                        break;
                    case Direction.LEFT:
                        index += 22;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.BOTTOM:
                switch (to) {
                    case Direction.RIGHT:
                        index += 2;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 3;
                        break;
                    case Direction.BOTTOM:
                        index += 12;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 21;
                        break;
                    case Direction.LEFT:
                        index += 22;
                        break;
                    case Direction.TOP_LEFT:
                        index += 16;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 1;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.BOTTOM_LEFT:
                switch (to) {
                    case Direction.BOTTOM_RIGHT:
                        index += 3;
                        break;
                    case Direction.BOTTOM:
                        index += 4;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 13;
                        break;
                    case Direction.LEFT:
                        index += 22;
                        break;
                    case Direction.TOP_LEFT:
                        index += 23;
                        break;
                    case Direction.TOP:
                        index += 16;
                        break;
                    case Direction.RIGHT:
                        index += 2;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.LEFT:
                switch (to) {
                    case Direction.TOP:
                        index += 16;
                        break;
                    case Direction.BOTTOM:
                        index += 4;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 5;
                        break;
                    case Direction.LEFT:
                        index += 14;
                        break;
                    case Direction.TOP_LEFT:
                        index += 23;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 17;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 3;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.TOP_LEFT:
                switch (to) {
                    case Direction.TOP:
                        index += 16;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 17;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 5;
                        break;
                    case Direction.LEFT:
                        index += 6;
                        break;
                    case Direction.TOP_LEFT:
                        index += 15;
                        break;
                    case Direction.BOTTOM:
                        index += 4;
                        break;
                    case Direction.RIGHT:
                        index += 18;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            default:
                index = 0;
                break;
        }

        return index;
    }

}
