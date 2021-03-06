package hellofx.fheroes2.resource;

import hellofx.fheroes2.common.Rand;

public class Resource {
    public static int Rand(boolean withGold) {
        return switch (Rand.Get(1, withGold ? 7 : 6)) {
            case 1 -> ResourceKind.WOOD;
            case 2 -> ResourceKind.MERCURY;
            case 3 -> ResourceKind.ORE;
            case 4 -> ResourceKind.SULFUR;
            case 5 -> ResourceKind.CRYSTAL;
            case 6 -> ResourceKind.GEMS;
            case 7 -> ResourceKind.GOLD;
            default -> ResourceKind.UNKNOWN;
        };

    }

    public static int Rand() {
        return Rand(false);
    }

    public static int FromIndexSprite(int index) {
        return switch (index) {
            case 1 -> ResourceKind.WOOD;
            case 3 -> ResourceKind.MERCURY;
            case 5 -> ResourceKind.ORE;
            case 7 -> ResourceKind.SULFUR;
            case 9 -> ResourceKind.CRYSTAL;
            case 11 -> ResourceKind.GEMS;
            case 13 -> ResourceKind.GOLD;
            default -> ResourceKind.UNKNOWN;
        };

    }

    public static int GetIndexSprite2(int index) {
        //TODO
        return 0;
    }

    public static int GetIndexSprite(int resource) {
        return switch (resource) {
            case ResourceKind.WOOD -> 1;
            case ResourceKind.MERCURY -> 3;
            case ResourceKind.ORE -> 5;
            case ResourceKind.SULFUR -> 7;
            case ResourceKind.CRYSTAL -> 9;
            case ResourceKind.GEMS -> 11;
            case ResourceKind.GOLD -> 13;
            default -> 0;
        };
    }
}
