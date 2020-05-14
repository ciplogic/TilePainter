package hellofx.fheroes2.kingdom;

public class RaceKind {
    public static final int NONE = 0x00;
    public static final int KNGT = 0x01;
    public static final int BARB = 0x02;
    public static final int SORC = 0x04;
    public static final int WRLK = 0x08;
    public static final int WZRD = 0x10;
    public static final int NECR = 0x20;
    public static final int MULT = 0x40;
    public static final int RAND = 0x80;
    public static final int ALL = KNGT | BARB | SORC | WRLK | WZRD | NECR;
}
