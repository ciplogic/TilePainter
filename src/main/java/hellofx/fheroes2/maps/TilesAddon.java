package hellofx.fheroes2.maps;

import hellofx.fheroes2.agg.IcnKind;
import hellofx.fheroes2.common.H2IntPair;

public class TilesAddon {

    public int uniq;
    public byte level;
    public byte object;
    public byte index;
    public byte tmp;

    public TilesAddon() {
    }

    public TilesAddon(int lv, int gid, int obj, int ii) {
        uniq = gid;
        level = (byte) lv;
        object = (byte) obj;
        index = (byte) ii;
        tmp = (0);
    }

    public static H2IntPair ColorRaceFromHeroSprite(TilesAddon addon) {
        return null;
    }

    public static boolean ForceLevel1(TilesAddon ta) {
        // broken ship: left roc
        return IcnKind.OBJNWAT2 == Mp2.GetICNObject(ta.object) && ta.index == 11;
    }

    public static boolean ForceLevel2(TilesAddon ta) {
        return false;
    }
}
