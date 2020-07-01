package hellofx.fheroes2.castle;

import hellofx.fheroes2.common.H2Rect;

public class BuildingInfo {
    Castle castle;
    int building;
    String description;
    H2Rect area = new H2Rect();
    int bcond;

    public BuildingInfo(Castle c, /*building_t*/ int b) {
        //TODO
    }

    public boolean IsDwelling() {
        switch (building) {
            case building_t.DWELLING_MONSTER1:
            case building_t.DWELLING_MONSTER2:
            case building_t.DWELLING_MONSTER3:
            case building_t.DWELLING_MONSTER4:
            case building_t.DWELLING_MONSTER5:
            case building_t.DWELLING_MONSTER6:
            case building_t.DWELLING_UPGRADE2:
            case building_t.DWELLING_UPGRADE3:
            case building_t.DWELLING_UPGRADE4:
            case building_t.DWELLING_UPGRADE5:
            case building_t.DWELLING_UPGRADE6:
            case building_t.DWELLING_UPGRADE7:
                return true;
            default:
                break;
        }
        return false;
    }
}
