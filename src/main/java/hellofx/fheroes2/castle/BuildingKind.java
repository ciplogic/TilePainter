package hellofx.fheroes2.castle;

public class BuildingKind {
    public static final int BUILD_NOTHING = 0x00000000;
    public static final int BUILD_THIEVESGUILD = 0x00000001;
    public static final int BUILD_TAVERN = 0x00000002;
    public static final int BUILD_SHIPYARD = 0x00000004;
    public static final int BUILD_WELL = 0x00000008;
    public static final int BUILD_STATUE = 0x00000010;
    public static final int BUILD_LEFTTURRET = 0x00000020;
    public static final int BUILD_RIGHTTURRET = 0x00000040;
    public static final int BUILD_MARKETPLACE = 0x00000080;
    public static final int BUILD_WEL2 = 0x00000100;
    // Farm, Garbage He, Crystal Gar, Waterfall, Orchard, Skull Pile
    public static final int BUILD_MOAT = 0x00000200;
    public static final int BUILD_SPEC = 0x00000400;
    // Fortification, Coliseum, Rainbow, Dungeon, Library, Storm
    public static final int BUILD_CASTLE = 0x00000800;
    public static final int BUILD_CAPTAIN = 0x00001000;
    public static final int BUILD_SHRINE = 0x00002000;
    public static final int BUILD_MAGEGUILD1 = 0x00004000;
    public static final int BUILD_MAGEGUILD2 = 0x00008000;
    public static final int BUILD_MAGEGUILD3 = 0x00010000;
    public static final int BUILD_MAGEGUILD4 = 0x00020000;
    public static final int BUILD_MAGEGUILD5 = 0x00040000;
    public static final int BUILD_MAGEGUILD = BUILD_MAGEGUILD1 | BUILD_MAGEGUILD2 | BUILD_MAGEGUILD3 | BUILD_MAGEGUILD4 | BUILD_MAGEGUILD5;
    public static final int BUILD_TENT = 0x00080000;
    // deprecated
    public static final int DWELLING_MONSTER1 = 0x00100000;
    public static final int DWELLING_MONSTER2 = 0x00200000;
    public static final int DWELLING_MONSTER3 = 0x00400000;
    public static final int DWELLING_MONSTER4 = 0x00800000;
    public static final int DWELLING_MONSTER5 = 0x01000000;
    public static final int DWELLING_MONSTER6 = 0x02000000;
    public static final int DWELLING_MONSTERS = DWELLING_MONSTER1 | DWELLING_MONSTER2 | DWELLING_MONSTER3 | DWELLING_MONSTER4 | DWELLING_MONSTER5 | DWELLING_MONSTER6;
    public static final int DWELLING_UPGRADE2 = 0x04000000;
    public static final int DWELLING_UPGRADE3 = 0x08000000;
    public static final int DWELLING_UPGRADE4 = 0x10000000;
    public static final int DWELLING_UPGRADE5 = 0x20000000;
    public static final int DWELLING_UPGRADE6 = 0x40000000;
    public static final int DWELLING_UPGRADE7 = 0x80000000;
    // black dragon
    public static final int DWELLING_UPGRADES = DWELLING_UPGRADE2 | DWELLING_UPGRADE3 | DWELLING_UPGRADE4 | DWELLING_UPGRADE5 | DWELLING_UPGRADE6 | DWELLING_UPGRADE7;
}
