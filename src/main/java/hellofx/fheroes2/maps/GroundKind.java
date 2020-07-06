package hellofx.fheroes2.maps;

public class GroundKind {
   public static final int UNKNOWN = 0x0000;
   public static final int DESERT = 0x0001;
   public static final int SNOW = 0x0002;
   public static final int SWAMP = 0x0004;
   public static final int WASTELAND = 0x0008;
   public static final int BEACH = 0x0010;
   public static final int LAVA = 0x0020;
   public static final int DIRT = 0x0040;
   public static final int GRASS = 0x0080;
   public static final int WATER = 0x0100;
   public static final int ALL = DESERT | SNOW | SWAMP | WASTELAND | BEACH | LAVA | DIRT | GRASS;
}
