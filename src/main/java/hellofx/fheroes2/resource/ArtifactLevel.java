package hellofx.fheroes2.resource;

public class ArtifactLevel {
    public static final int ART_NONE = 0;
    public static final int ART_LEVEL1 = 0x01;
    public static final int ART_LEVEL2 = 0x02;
    public static final int ART_LEVEL3 = 0x04;
    public static final int ART_LEVEL123 = ART_LEVEL1 | ART_LEVEL2 | ART_LEVEL3;
    public static final int ART_ULTIMATE = 0x08;
    public static final int ART_LOYALTY = 0x10;
    public static final int ART_NORANDOM = 0x20;
}
