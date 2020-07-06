package hellofx.fheroes2.army;

import static hellofx.common.Utilities.StringLower;
import static hellofx.fheroes2.system.Translate.StringReplace;
import static hellofx.fheroes2.system.Translate.tr;

public class armysize_t {
    public static final int ARMY_FEW = 1;
    public static final int ARMY_SEVERAL = 5;
    public static final int ARMY_PACK = 10;
    public static final int ARMY_LOTS = 20;
    public static final int ARMY_HORDE = 50;
    public static final int ARMY_THRONG = 100;
    public static final int ARMY_SWARM = 250;
    public static final int ARMY_ZOUNDS = 500;
    public static final int ARMY_LEGION = 1000;

    public static int ArmyGetSize(int count) {
        if (ARMY_LEGION <= count) return ARMY_LEGION;
        if (ARMY_ZOUNDS <= count) return ARMY_ZOUNDS;
        if (ARMY_SWARM <= count) return ARMY_SWARM;
        if (ARMY_THRONG <= count) return ARMY_THRONG;
        if (ARMY_HORDE <= count) return ARMY_HORDE;
        if (ARMY_LOTS <= count) return ARMY_LOTS;
        if (ARMY_PACK <= count) return ARMY_PACK;
        if (ARMY_SEVERAL <= count) return ARMY_SEVERAL;

        return ARMY_FEW;
    }

    public static String TroopSizeString(Troop troop) {

        String str = "";

        switch (ArmyGetSize(troop.GetCount())) {
            default:
                str = tr("A few %{monster}");
                break;
            case ARMY_SEVERAL:
                str = tr("Several %{monster}");
                break;
            case ARMY_PACK:
                str = tr("A pack of %{monster}");
                break;
            case ARMY_LOTS:
                str = tr("Lots of %{monster}");
                break;
            case ARMY_HORDE:
                str = tr("A horde of %{monster}");
                break;
            case ARMY_THRONG:
                str = tr("A throng of %{monster}");
                break;
            case ARMY_SWARM:
                str = tr("A swarm of %{monster}");
                break;
            case ARMY_ZOUNDS:
                str = tr("Zounds of %{monster}");
                break;
            case ARMY_LEGION:
                str = tr("A legion of %{monster}");
                break;
        }

        str = StringReplace(str, "%{monster}", StringLower(troop._monster.GetMultiName()));
        return str;
    }
}
