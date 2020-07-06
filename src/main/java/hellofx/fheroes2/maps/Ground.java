package hellofx.fheroes2.maps;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.heroes.SkillLevel;
import hellofx.fheroes2.kingdom.World;

public class Ground {
    public static int GetPenalty(int index, int direct, int level) {
        var world = World.Instance;
        Tiles tile = world.GetTiles(index);

        //            none   basc   advd   expr
        //    Desert  2.00   1.75   1.50   1.00
        //    Snow    1.75   1.50   1.25   1.00
        //    Swamp   1.75   1.50   1.25   1.00
        //    Cracked 1.25   1.00   1.00   1.00
        //    Beach   1.25   1.00   1.00   1.00
        //    Lava    1.00   1.00   1.00   1.00
        //    Dirt    1.00   1.00   1.00   1.00
        //    Grass   1.00   1.00   1.00   1.00
        //    Water   1.00   1.00   1.00   1.00
        //    Road    0.75   0.75   0.75   0.75

        if (tile.isRoad(direct))
            // road priority: need small value
            return 59;

        int result = 100;

        switch (tile.GetGround()) {
            case GroundKind.DESERT:
                switch (level) {
                    case SkillLevel.EXPERT:
                        break;
                    case SkillLevel.ADVANCED:
                        result += 50;
                        break;
                    case SkillLevel.BASIC:
                        result += 75;
                        break;
                    default:
                        result += 100;
                        break;
                }
                break;

            case GroundKind.SNOW:
            case GroundKind.SWAMP:
                switch (level) {
                    case SkillLevel.EXPERT:
                        break;
                    case SkillLevel.ADVANCED:
                        result += 25;
                        break;
                    case SkillLevel.BASIC:
                        result += 50;
                        break;
                    default:
                        result += 75;
                        break;
                }
                break;

            case GroundKind.WASTELAND:
            case GroundKind.BEACH:
                result += SkillLevel.NONE == level ? 25 : 0;
                break;

            default:
                break;
        }

        if (0 != (direct & (Direction.TOP_RIGHT | Direction.BOTTOM_RIGHT | Direction.BOTTOM_LEFT | Direction.TOP_LEFT)))
            result = result * 155 / 100;

        return result;
    }
}
