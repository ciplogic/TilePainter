package hellofx.fheroes2.maps.objects;

import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.H2Size;
import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.maps.MapsIndexes;

public class Maps {

    public static H2Point GetPoint(int index) {
        var world = World.Instance;
        return new H2Point(index % world.w, index / world.w);
    }

    void GetAroundIndexes(int center, MapsIndexes result) {
        result.values.clear();
        if (!isValidAbsIndex(center)) {
            return;
        }
        var directions = Direction.All();
        var world = World.Instance;
        var wSize = new H2Size(world.w, world.h);
        for (int direction : directions) {
            if (isValidDirection(center, direction, wSize))
                result.values.add(GetDirectionIndex(center, direction));
        }
    }

    private int GetDirectionIndex(int center, int direction) {
        //TODO
        return 0;
    }

    private boolean isValidDirection(int from, int vector, H2Size world) {
        switch (vector) {
            case Direction.TOP:
                return from >= world.w;
            case Direction.RIGHT:
                return from % world.w < world.w - 1;
            case Direction.BOTTOM:
                return from < world.w * (world.h - 1);
            case Direction.LEFT:
                return (from % world.w) != 0;

            case Direction.TOP_RIGHT:
                return from >= world.w && from % world.w < world.w - 1;

            case Direction.BOTTOM_RIGHT:
                return from < world.w * (world.h - 1) && from % world.w < world.w - 1;

            case Direction.BOTTOM_LEFT:
                return (from < (world.w * (world.h - 1))) && (from % world.w) != 0;

            case Direction.TOP_LEFT:
                return from >= world.w && (from % world.w) != 0;

            default:
                break;
        }

        return false;
    }

    private boolean isValidAbsIndex(int index) {
        var world = World.Instance;
        return index > 0 && (index < (world.w * world.h));
    }

    public static void MinimizeAreaForCastle(H2Point getCenter) {
    }

    public static void UpdateRNDSpriteForCastle(H2Point center, int race, boolean isCastle) {
        //TODO
    }
}
