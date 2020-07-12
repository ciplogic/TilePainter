package hellofx.fheroes2.castle;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Sprite;
import hellofx.fheroes2.kingdom.RaceKind;

import static hellofx.fheroes2.castle.building_t.*;

public class CastleDialog {
    public static boolean AllowFlashBuilding(int build) {
        switch (build) {
            case BUILD_TAVERN:
            case BUILD_SHRINE:
            case BUILD_SHIPYARD:
            case BUILD_WELL:
            case BUILD_STATUE:
            case BUILD_LEFTTURRET:
            case BUILD_RIGHTTURRET:
            case BUILD_MARKETPLACE:
            case BUILD_WEL2:
            case BUILD_MOAT:
            case BUILD_SPEC:
            case BUILD_CASTLE:
            case BUILD_CAPTAIN:
            case BUILD_MAGEGUILD1:
            case BUILD_MAGEGUILD2:
            case BUILD_MAGEGUILD3:
            case BUILD_MAGEGUILD4:
            case BUILD_MAGEGUILD5:
            case BUILD_TENT:
            case DWELLING_UPGRADE2:
            case DWELLING_UPGRADE3:
            case DWELLING_UPGRADE4:
            case DWELLING_UPGRADE5:
            case DWELLING_UPGRADE6:
            case DWELLING_UPGRADE7:
            case DWELLING_MONSTER1:
            case DWELLING_MONSTER2:
            case DWELLING_MONSTER3:
            case DWELLING_MONSTER4:
            case DWELLING_MONSTER5:
            case DWELLING_MONSTER6:
                return true;

            default:
                break;
        }

        return false;
    }

    public Sprite GetActualSpriteBuilding(Castle castle, int build) {
        var index = 0;
        // correct index (mage guild)
        switch (build) {
            case BUILD_MAGEGUILD1:
                index = 0;
                break;
            case BUILD_MAGEGUILD2:
                index = RaceKind.NECR == castle.GetRace() ? 6 : 1;
                break;
            case BUILD_MAGEGUILD3:
                index = RaceKind.NECR == castle.GetRace() ? 12 : 2;
                break;
            case BUILD_MAGEGUILD4:
                index = RaceKind.NECR == castle.GetRace() ? 18 : 3;
                break;
            case BUILD_MAGEGUILD5:
                index = RaceKind.NECR == castle.GetRace() ? 24 : 4;
                break;
            default:
                break;
        }

        return Agg.GetICN(Castle.GetICNBuilding(build, castle.GetRace()), index);
    }
}
