package hellofx.fheroes2.maps;

import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.common.H2IntPair;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.kingdom.RaceKind;

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

    public static H2IntPair ColorRaceFromHeroSprite(TilesAddon ta) {
        //TODO

        H2IntPair res = new H2IntPair();

        if (7 > ta.index)
            res.first = H2Color.BLUE;
        else if (14 > ta.index)
            res.first = H2Color.GREEN;
        else if (21 > ta.index)
            res.first = H2Color.RED;
        else if (28 > ta.index)
            res.first = H2Color.YELLOW;
        else if (35 > ta.index)
            res.first = H2Color.ORANGE;
        else
            res.first = H2Color.PURPLE;

        res.second = RaceKind.NONE;
        switch (ta.index % 7) {
            case 0 -> res.second = RaceKind.KNGT;
            case 1 -> res.second = RaceKind.BARB;
            case 2 -> res.second = RaceKind.SORC;
            case 3 -> res.second = RaceKind.WRLK;
            case 4 -> res.second = RaceKind.WZRD;
            case 5 -> res.second = RaceKind.NECR;
            case 6 -> res.second = RaceKind.RAND;
        }
        return res;
    }

    public static boolean ForceLevel1(TilesAddon ta) {
        // broken ship: left roc
        return IcnKind.OBJNWAT2 == Mp2.GetICNObject(ta.object) && ta.index == 11;
    }

    public static boolean ForceLevel2(TilesAddon ta) {
        return false;
    }
}
