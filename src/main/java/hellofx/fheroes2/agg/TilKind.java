package hellofx.fheroes2.agg;

public class TilKind {
    public static final int UNKNOWN = 0;
    public static final int CLOF32 = 1;
    public static final int GROUND32 = 2;
    public static final int STON = 3;
    public static final int LASTTIL = 4;

    private static final String[] TilNames = {"UNKNOWN", "CLOF32.TIL", "GROUND32.TIL", "STON.TIL"};
    private static final short[] TilSizes = {0, 4, 432, 36, 0};

    public static String GetString(int til) {
        if (til >= LASTTIL) return "CUSTOM";
        return TilNames[til];
    }

    public static int GetTileCount(int tile) {
        return TilSizes[tile];
    }
}
