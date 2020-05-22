package hellofx.fheroes2.resource;

public class ResourceKind {
    public static final int UNKNOWN = 0x00;
    public static final int WOOD = 0x01;
    public static final int MERCURY = 0x02;
    public static final int ORE = 0x04;
    public static final int SULFUR = 0x08;
    public static final int CRYSTAL = 0x10;
    public static final int GEMS = 0x20;
    public static final int GOLD = 0x40;
    public static final int ALL = WOOD | MERCURY | ORE | SULFUR | CRYSTAL | GEMS | GOLD;
}
