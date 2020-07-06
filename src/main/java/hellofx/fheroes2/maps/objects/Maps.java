package hellofx.fheroes2.maps.objects;

import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.H2Size;
import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.maps.MapsIndexes;
import hellofx.fheroes2.maps.Mp2Kind;
import hellofx.fheroes2.maps.TilesAddon;

public class Maps {

    public static H2Point GetPoint(int index) {
        var world = World.Instance;
        return new H2Point(index % world.w, index / world.w);
    }

    public static boolean isValidAbsPoint(int x, int y) {
        var world = World.Instance;
        return 0 <= x && world.w > x && 0 <= y && world.h > y;
    }

    public static int GetIndexFromAbsPoint(H2Point pt) {
        return GetIndexFromAbsPoint(pt.x, pt.y);
    }

    public static int GetIndexFromAbsPoint(int px, int py) {
        var world = World.Instance;
        var res = py * world.w + px;

        if (px < 0 || py < 0) {
            System.out.println("Maps::GetIndexFromAbsPoint: error coods, " + "x: " + px + ", y: " + py);
            return -1;
        }

        return res;
    }

    public static void GetAroundIndexes(int center, MapsIndexes result) {
        result.clear();
        if (!isValidAbsIndex(center)) {
            return;
        }
        var directions = Direction.All();
        var world = World.Instance;
        var wSize = new H2Size(world.w, world.h);
        for (int direction : directions) {
            if (isValidDirection(center, direction, wSize))
                result.add(GetDirectionIndex(center, direction));
        }
    }

    public static int GetDirectionIndex(int from, int vector) {
        var world = World.Instance;
        switch (vector) {
            case Direction.TOP:
                return from - world.w;
            case Direction.TOP_RIGHT:
                return from - world.w + 1;
            case Direction.RIGHT:
                return from + 1;
            case Direction.BOTTOM_RIGHT:
                return from + world.w + 1;
            case Direction.BOTTOM:
                return from + world.w;
            case Direction.BOTTOM_LEFT:
                return from + world.w - 1;
            case Direction.LEFT:
                return from - 1;
            case Direction.TOP_LEFT:
                return from - world.w - 1;
            default:
                break;
        }

        return -1;
    }

    public static boolean isValidDirection(int from, int vector, H2Size world) {
        switch (vector) {
            case Direction.TOP:
                return from >= world.w;
            case Direction.RIGHT:
                return from % world.w < world.w - 1;
            case Direction.BOTTOM:
                return from < world.w * (world.h - 1);
            case Direction.LEFT:
                return from % world.w != 0;

            case Direction.TOP_RIGHT:
                return from >= world.w && from % world.w < world.w - 1;

            case Direction.BOTTOM_RIGHT:
                return from < world.w * (world.h - 1) && from % world.w < world.w - 1;

            case Direction.BOTTOM_LEFT:
                return from < world.w * (world.h - 1) && from % world.w != 0;

            case Direction.TOP_LEFT:
                return from >= world.w && from % world.w != 0;

            default:
                break;
        }

        return false;
    }

    public static boolean isValidAbsIndex(int index) {
        var world = World.Instance;
        return index >= 0 && index < world.vec_tiles.length;
    }

    public static void MinimizeAreaForCastle(H2Point center) {
        var world = World.Instance;
        // reset castle ID
        for (var yy = -3; yy < 2; ++yy)
            for (var xx = -2; xx < 3; ++xx) {
                var tile = world.GetTiles(center.x + xx, center.y + yy);

                if (Mp2Kind.OBJN_RNDCASTLE == tile.GetObject() ||
                        Mp2Kind.OBJN_RNDTOWN == tile.GetObject() ||
                        Mp2Kind.OBJN_CASTLE == tile.GetObject())
                    tile.SetObject(Mp2Kind.OBJ_ZERO);
            }

        // set minimum area castle ID
        for (var yy = -1; yy < 1; ++yy)
            for (var xx = -2; xx < 3; ++xx) {
                var tile = world.GetTiles(center.x + xx, center.y + yy);

                // skip angle
                if (yy == -1 && (xx == -2 || xx == 2)) continue;

                tile.SetObject(Mp2Kind.OBJN_CASTLE);
            }

        // restore center ID
        world.GetTiles(center.x, center.y).SetObject(Mp2Kind.OBJ_CASTLE);
    }

    public static void UpdateRNDSpriteForCastle(H2Point center, int race, boolean castle) {
        MapsIndexes coords = new MapsIndexes();

        // T0
        if (castle) coords.add(GetIndexFromAbsPoint(center.x, center.y - 3));
        // T1
        coords.add(GetIndexFromAbsPoint(center.x - 2, center.y - 2));
        coords.add(GetIndexFromAbsPoint(center.x - 1, center.y - 2));
        coords.add(GetIndexFromAbsPoint(center.x, center.y - 2));
        coords.add(GetIndexFromAbsPoint(center.x + 1, center.y - 2));
        coords.add(GetIndexFromAbsPoint(center.x + 2, center.y - 2));
        // T2
        coords.add(GetIndexFromAbsPoint(center.x - 2, center.y - 1));
        coords.add(GetIndexFromAbsPoint(center.x - 1, center.y - 1));
        coords.add(GetIndexFromAbsPoint(center.x, center.y - 1));
        coords.add(GetIndexFromAbsPoint(center.x + 1, center.y - 1));
        coords.add(GetIndexFromAbsPoint(center.x + 2, center.y - 1));
        // B1
        coords.add(GetIndexFromAbsPoint(center.x - 2, center.y));
        coords.add(GetIndexFromAbsPoint(center.x - 1, center.y));
        coords.add(GetIndexFromAbsPoint(center.x, center.y));
        coords.add(GetIndexFromAbsPoint(center.x + 1, center.y));
        coords.add(GetIndexFromAbsPoint(center.x + 2, center.y));
        // B2
        coords.add(GetIndexFromAbsPoint(center.x - 2, center.y + 1));
        coords.add(GetIndexFromAbsPoint(center.x - 1, center.y + 1));
        coords.add(GetIndexFromAbsPoint(center.x, center.y + 1));
        coords.add(GetIndexFromAbsPoint(center.x + 1, center.y + 1));
        coords.add(GetIndexFromAbsPoint(center.x + 2, center.y + 1));

        var world = World.Instance;
        var tile_center = world.GetTiles(center.x, center.y);

        // correct only RND town and castle
        switch (tile_center.GetObject()) {
            case Mp2Kind.OBJ_RNDTOWN:
            case Mp2Kind.OBJ_RNDCASTLE:
                break;

            default:
                return;
        }

        // modify all rnd sprites
        for (var it : coords) {
            if (!isValidAbsIndex(it))
                continue;
            TilesAddon addon = world.GetTiles(it).FindObject(Mp2Kind.OBJ_RNDCASTLE);
            if (addon == null)
                continue;
            addon.object = (short) (addon.object - 12);

            switch (race) {
                case RaceKind.KNGT:
                    addon.index = addon.index;
                    break;
                case RaceKind.BARB:
                    addon.index = (short) (addon.index + 32);
                    break;
                case RaceKind.SORC:
                    addon.index = (short) (addon.index + 64);
                    break;
                case RaceKind.WRLK:
                    addon.index = (short) (addon.index + 96);
                    break;
                case RaceKind.WZRD:
                    addon.index = (short) (addon.index + 128);
                    break;
                case RaceKind.NECR:
                    addon.index = (short) (addon.index + 160);
                    break;
                default:
                    break;
            }
        }
    }

    static boolean TileIsObject(World world, int index, int obj) {
        return obj == world.GetTiles(index).GetObject();
    }

    static boolean TileIsObject(int index, int obj) {
        return TileIsObject(World.Instance, index, obj);
    }

    public static void MapsIndexesFilteredObject(MapsIndexes indexes, int obj) {
        MapsIndexes result = new MapsIndexes();
        var world = World.Instance;
        for (var tile : indexes) {
            int iTile = tile;
            if (TileIsObject(world, tile, obj)) {
                result.add(iTile);
            }
        }
        indexes.clear();
        indexes.addAll(result);
    }

    public static int GetApproximateDistance(int index1, int index2) {
        var sz = GetPoint(index1).sub(GetPoint(index2));
        return Integer.max(sz.x, sz.y);
    }
}
