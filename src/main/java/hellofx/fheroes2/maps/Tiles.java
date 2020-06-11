package hellofx.fheroes2.maps;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Bitmap;
import hellofx.fheroes2.agg.Sprite;
import hellofx.fheroes2.agg.TilKind;
import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.monster.Monster;
import hellofx.fheroes2.monster.MonsterKind;
import hellofx.fheroes2.system.Settings;
import hellofx.framework.controls.Painter;

import java.util.List;
import java.util.function.Predicate;

import static hellofx.fheroes2.heroes.Direction.DIRECTION_BOTTOM_ROW;
import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;
import static hellofx.fheroes2.serialize.ByteVectorReader.toUShort;

public class Tiles {
    public Addons addons_level1 = new Addons();
    public Addons addons_level2 = new Addons(); // 16
    public int maps_index = 0;
    public short pack_sprite_index = 0;
    public short tile_passable = 0;
    public byte mp2_object = 0;
    public byte fog_colors = 0;
    public byte quantity1 = 0;
    public byte quantity2 = 0;
    public byte quantity3 = 0;
    private int maps_x;
    private int maps_y;

    public void Init(int index, Mp2Tile mp2) {
        tile_passable = Direction.DIRECTION_ALL;
        quantity1 = Mp2Tile.quantity1;
        quantity2 = Mp2Tile.quantity2;
        quantity3 = 0;
        fog_colors = H2Color.ALL;

        SetTile(Mp2Tile.tileIndex, Mp2Tile.shape);
        SetIndex(index);
        SetObject(Mp2Tile.generalObject);

        addons_level1._items.clear();
        addons_level2._items.clear();

        AddonsPushLevel1(mp2);
        AddonsPushLevel2(mp2);
    }

    private void AddonsPushLevel1(Mp2Tile mt) {
        if (Mp2Tile.objectName1 != 0 && toByte(Mp2Tile.indexName1) < 0xFF)
            AddonsPushLevel1(new TilesAddon(0, Mp2Tile.uniqNumber1, Mp2Tile.objectName1, Mp2Tile.indexName1));
    }

    private void AddonsPushLevel2(Mp2Tile mt) {
        if (Mp2Tile.objectName2 != 0 && toByte(Mp2Tile.indexName2) < 0xFF)
            AddonsPushLevel1(new TilesAddon(0, Mp2Tile.uniqNumber2, Mp2Tile.objectName2, Mp2Tile.indexName2));
    }

    private void SetIndex(int index) {
        maps_index = index;
        var point = GetPoint(index);
        maps_x = point.x;
        maps_y = point.y;
    }

    private void SetTile(int sprite_index, int shape) {
        pack_sprite_index = (short) PackTileSpriteIndex(sprite_index, shape);
    }

    private int PackTileSpriteIndex(int index, int shape) {
        return shape << 14 | 0x3FFF & index;
    }

    public void AddonsPushLevel1(Mp2Addon ma) {
        if (ma.objectNameN1 != 0 && toByte(ma.indexNameN1) < 0xFF)
            AddonsPushLevel1(new TilesAddon(ma.quantityN, ma.uniqNumberN1, ma.objectNameN1, ma.indexNameN1));

    }

    private void AddonsPushLevel1(TilesAddon ta) {
        if (TilesAddon.ForceLevel2(ta))
            addons_level2._items.add(ta);
        else
            addons_level1._items.add(ta);
    }

    public void AddonsPushLevel2(Mp2Addon mp2Addon) {
    }

    public void AddonsSort() {
    }

    public int GetQuantity1() {
        return toByte(quantity1);
    }

    public int GetQuantity2() {
        return toByte(quantity2);
    }

    public int GetQuantity3() {
        return toByte(quantity3);
    }

    public int GetObject() {
        return GetObject(true);
    }

    public void SetObject(int mp2Kind) {
        mp2_object = (byte) mp2Kind;
    }

    public int GetObject(boolean skip_hero) {
        if ((!skip_hero) && (Mp2Kind.OBJ_HEROES == toByte(mp2_object))) {
            var hero = GetHeroes();
            return hero != null ? hero.GetMapsObject() : Mp2Kind.OBJ_ZERO;
        }

        return toByte(mp2_object);
    }

    public Heroes GetHeroes() {
        //TODO
        return null;
    }

    public int GetIndex() {
        return maps_index;
    }

    boolean isStream(TilesAddon ta) {
        return IcnKind.STREAM == Mp2.GetICNObject(ta.object) ||
                (IcnKind.OBJNMUL2 == Mp2.GetICNObject(ta.object) && ta.index < 14);
    }

    boolean isRoad(TilesAddon ta) {
        return IcnKind.ROAD == Mp2.GetICNObject(ta.object);
    }

    boolean isWaterResource(TilesAddon ta) {
        return IcnKind.OBJNWATR == Mp2.GetICNObject(ta.object) &&
                (0 == ta.index || // buttle
                        19 == ta.index || // chest
                        45 == ta.index || // flotsam
                        111 == ta.index) // surviror
                ;
    }

    boolean isWhirlPool(TilesAddon ta) {
        return IcnKind.OBJNWATR == Mp2.GetICNObject(ta.object) &&
                (toByte(ta.index) >= 202 && toByte(ta.index) <= 225);
    }

    boolean isStandingStone(TilesAddon ta) {
        return IcnKind.OBJNMULT == Mp2.GetICNObject(ta.object) &&
                (ta.index == 84 || ta.index == 85);
    }

    boolean isResource(TilesAddon ta) {
        // OBJNRSRC
        return (IcnKind.OBJNRSRC == Mp2.GetICNObject(ta.object) && ((ta.index % 2) != 0)) ||
                // TREASURE
                IcnKind.TREASURE == Mp2.GetICNObject(ta.object);
    }

    boolean isRandomResource(TilesAddon ta) {
        // OBJNRSRC
        return IcnKind.OBJNRSRC == Mp2.GetICNObject(ta.object) && 17 == ta.index;
    }

    boolean isArtifact(TilesAddon ta) {
        // OBJNARTI (skip ultimate)
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.object) && ta.index > 0x10 && ((ta.index % 2) != 0);
    }

    boolean isRandomArtifact(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.object) && 0xA3 == toByte(ta.index);
    }

    boolean isRandomArtifact1(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.object) && 0xA7 == toByte(ta.index);
    }

    boolean isRandomArtifact2(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.object) && 0xA9 == toByte(ta.index);
    }

    boolean isRandomArtifact3(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.object) && 0xAB == toByte(ta.index);
    }

    boolean isUltimateArtifact(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.object) && 0xA4 == toByte(ta.index);
    }

    boolean isCampFire(TilesAddon ta) {
        // MTNDSRT
        return (IcnKind.OBJNDSRT == Mp2.GetICNObject(ta.object) && 61 == ta.index) ||
                // OBJNMULT
                (IcnKind.OBJNMULT == Mp2.GetICNObject(ta.object) && 131 == toByte(ta.index)) ||
                // OBJNSNOW
                (IcnKind.OBJNSNOW == Mp2.GetICNObject(ta.object) && 4 == ta.index);
    }

    boolean isMonster(TilesAddon ta) {
        // MONS32
        return IcnKind.MONS32 == Mp2.GetICNObject(ta.object);
    }

    boolean isArtesianSpring(TilesAddon ta) {
        return IcnKind.OBJNCRCK == Mp2.GetICNObject(ta.object) &&
                (ta.index == 3 || ta.index == 4);
    }

    boolean isSkeleton(TilesAddon ta) {
        return IcnKind.OBJNDSRT == Mp2.GetICNObject(ta.object) && ta.index == 84;
    }

    boolean isSkeletonFix(TilesAddon ta) {
        return IcnKind.OBJNDSRT == Mp2.GetICNObject(ta.object) && ta.index == 83;
    }

    boolean isOasis(TilesAddon ta) {
        return IcnKind.OBJNDSRT == Mp2.GetICNObject(ta.object) &&
                (ta.index == 108 || ta.index == 109);
    }

    boolean isWateringHole(TilesAddon ta) {
        return IcnKind.OBJNCRCK == Mp2.GetICNObject(ta.object) &&
                (ta.index >= 217 && ta.index <= 220);
    }

    boolean isJail(TilesAddon ta) {
        return IcnKind.X_LOC2 == Mp2.GetICNObject(ta.object) && 0x09 == ta.index;
    }

    boolean isEvent(TilesAddon ta) {
        // OBJNMUL2
        return IcnKind.OBJNMUL2 == Mp2.GetICNObject(ta.object) && 0xA3 == ta.index;
    }

    boolean isMine(TilesAddon ta) {
        // EXTRAOVR
        return IcnKind.EXTRAOVR == Mp2.GetICNObject(ta.object);
    }

    boolean isBoat(TilesAddon ta) {
        // OBJNWAT2
        return IcnKind.OBJNWAT2 == Mp2.GetICNObject(ta.object) && 0x17 == ta.index;
    }

    boolean isMiniHero(TilesAddon ta) {
        // MINIHERO
        return IcnKind.MINIHERO == Mp2.GetICNObject(ta.object);
    }

    boolean isCastle(TilesAddon ta) {
        // OBJNTOWN
        return IcnKind.OBJNTOWN == Mp2.GetICNObject(ta.object);
    }

    boolean isRandomCastle(TilesAddon ta) {
        // OBJNTWRD
        return IcnKind.OBJNTWRD == Mp2.GetICNObject(ta.object) && 32 > ta.index;
    }

    boolean isRandomMonster(TilesAddon ta) {
        // MONS32
        return IcnKind.MONS32 == Mp2.GetICNObject(ta.object) &&
                (0x41 < ta.index && 0x47 > ta.index);
    }

    boolean isBarrier(TilesAddon ta) {
        return IcnKind.X_LOC3 == Mp2.GetICNObject(ta.object) &&
                60 <= ta.index && 102 >= ta.index && 0 == ta.index % 6;
    }

    TilesAddon find_if(List<TilesAddon> addons, Predicate<TilesAddon> isFound) {
        return addons.stream().filter(isFound::test).findFirst().orElse(null);
    }


    public TilesAddon FindObjectConst(int objs) {
        TilesAddon it = null;
        switch (objs) {
            case Mp2Kind.OBJ_CAMPFIRE:
                it = find_if(addons_level1._items, this::isCampFire);
                break;

            case Mp2Kind.OBJ_TREASURECHEST:
            case Mp2Kind.OBJ_ANCIENTLAMP:
            case Mp2Kind.OBJ_RESOURCE:
                it = find_if(addons_level1._items, this::isResource);
                break;

            case Mp2Kind.OBJ_RNDRESOURCE:
                it = find_if(addons_level1._items, this::isRandomResource);
                break;

            case Mp2Kind.OBJ_FLOTSAM:
            case Mp2Kind.OBJ_SHIPWRECKSURVIROR:
            case Mp2Kind.OBJ_WATERCHEST:
            case Mp2Kind.OBJ_BOTTLE:
                it = find_if(addons_level1._items, this::isWaterResource);
                break;

            case Mp2Kind.OBJ_ARTIFACT:
                it = find_if(addons_level1._items, this::isArtifact);
                break;

            case Mp2Kind.OBJ_RNDARTIFACT:
                it = find_if(addons_level1._items, this::isRandomArtifact);
                break;

            case Mp2Kind.OBJ_RNDARTIFACT1:
                it = find_if(addons_level1._items, this::isRandomArtifact1);
                break;

            case Mp2Kind.OBJ_RNDARTIFACT2:
                it = find_if(addons_level1._items, this::isRandomArtifact2);
                break;

            case Mp2Kind.OBJ_RNDARTIFACT3:
                it = find_if(addons_level1._items, this::isRandomArtifact3);
                break;

            case Mp2Kind.OBJ_RNDULTIMATEARTIFACT:
                it = find_if(addons_level1._items, this::isUltimateArtifact);
                break;

            case Mp2Kind.OBJ_MONSTER:
                it = find_if(addons_level1._items, this::isMonster);
                break;

            case Mp2Kind.OBJ_WHIRLPOOL:
                it = find_if(addons_level1._items, this::isWhirlPool);
                break;

            case Mp2Kind.OBJ_STANDINGSTONES:
                it = find_if(addons_level1._items, this::isStandingStone);
                break;

            case Mp2Kind.OBJ_ARTESIANSPRING:
                it = find_if(addons_level1._items, this::isArtesianSpring);
                break;

            case Mp2Kind.OBJ_OASIS:
                it = find_if(addons_level1._items, this::isOasis);
                break;

            case Mp2Kind.OBJ_WATERINGHOLE:
                it = find_if(addons_level1._items, this::isWateringHole);
                break;

            case Mp2Kind.OBJ_MINES:
                it = find_if(addons_level1._items, this::isMine);
                break;

            case Mp2Kind.OBJ_JAIL:
                it = find_if(addons_level1._items, this::isJail);
                break;

            case Mp2Kind.OBJ_EVENT:
                it = find_if(addons_level1._items, this::isEvent);
                break;

            case Mp2Kind.OBJ_BOAT:
                it = find_if(addons_level1._items, this::isBoat);
                break;

            case Mp2Kind.OBJ_BARRIER:
                it = find_if(addons_level1._items, this::isBarrier);
                break;

            case Mp2Kind.OBJ_HEROES:
                it = find_if(addons_level1._items, this::isMiniHero);
                break;

            case Mp2Kind.OBJ_CASTLE:
                it = find_if(addons_level1._items, this::isCastle);
                if (it == null) {
                    it = find_if(addons_level2._items, this::isCastle);
                    return it;
                }
                break;

            case Mp2Kind.OBJ_RNDCASTLE:
                it = find_if(addons_level1._items, this::isRandomCastle);
                if (it == null) {
                    it = find_if(addons_level2._items, this::isRandomCastle);
                    return it;
                }
                break;

            case Mp2Kind.OBJ_RNDMONSTER:
            case Mp2Kind.OBJ_RNDMONSTER1:
            case Mp2Kind.OBJ_RNDMONSTER2:
            case Mp2Kind.OBJ_RNDMONSTER3:
            case Mp2Kind.OBJ_RNDMONSTER4:
                it = find_if(addons_level1._items, this::isRandomMonster);
                break;

            case Mp2Kind.OBJ_SKELETON:
                it = find_if(addons_level1._items, this::isSkeleton);
                break;

            default:
                //FIXME for: " << Mp2Kind.StringObject(objs));
                break;
        }
        return it;
    }


    public Sprite RedrawBottom(Painter dst, boolean skip_objs, Agg agg) {
        for (var it : addons_level1._items) {
            // skip
            if (skip_objs &&
                    Mp2.isRemoveObject(GetObject()) &&
                    FindObjectConst(GetObject()) == it)
                continue;

            var object = toByte(it.object);
            var index = toByte(it.index);
            var icn = Mp2.GetICNObject(object);

            if (IcnKind.UNKNOWN == icn || IcnKind.MINIHERO == icn || IcnKind.MONS32 == icn)
                continue;
            var sprite = Agg.GetICN(icn, index);
            return sprite;
        /*
            area.BlitOnTile(dst, sprite, mp);

            // possible anime
            var anime_index = IcnKind.AnimationFrame(icn, index, Game.MapsAnimationFrame(), quantity2);
            if (anime_index == 0)
                continue;
        var anime_sprite = agg.GetICN(icn, anime_index);
            area.BlitOnTile(dst, anime_sprite, mp);

         */
        }
        //TODO
        return null;
    }

    public Sprite[] RedrawObjects(Painter dst) {
        if (maps_x == 0 && maps_y == 4) {
            System.out.println("Here is a monster");
        }
        var obj = GetObject();
        switch (obj) {
            // boat
            case Mp2Kind.OBJ_BOAT:
                RedrawBoat(dst);
                break;
            // monster
            case Mp2Kind.OBJ_MONSTER:
                return RedrawMonster(dst);

            //
            default:
                break;
        }
        //TODO
        return null;
    }

    Monster QuantityMonster() {
        switch (GetObject(false)) {
            case Mp2Kind.OBJ_WATCHTOWER:
                return new Monster(MonsterKind.ORC);
            case Mp2Kind.OBJ_EXCAVATION:
                return new Monster(MonsterKind.SKELETON);
            case Mp2Kind.OBJ_CAVE:
                return new Monster(MonsterKind.CENTAUR);
            case Mp2Kind.OBJ_TREEHOUSE:
                return new Monster(MonsterKind.SPRITE);
            case Mp2Kind.OBJ_ARCHERHOUSE:
                return new Monster(MonsterKind.ARCHER);
            case Mp2Kind.OBJ_GOBLINHUT:
                return new Monster(MonsterKind.GOBLIN);
            case Mp2Kind.OBJ_DWARFCOTT:
                return new Monster(MonsterKind.DWARF);
            case Mp2Kind.OBJ_HALFLINGHOLE:
                return new Monster(MonsterKind.HALFLING);
            case Mp2Kind.OBJ_PEASANTHUT:
            case Mp2Kind.OBJ_THATCHEDHUT:
                return new Monster(MonsterKind.PEASANT);

            case Mp2Kind.OBJ_RUINS:
                return new Monster(MonsterKind.MEDUSA);
            case Mp2Kind.OBJ_TREECITY:
                return new Monster(MonsterKind.SPRITE);
            case Mp2Kind.OBJ_WAGONCAMP:
                return new Monster(MonsterKind.ROGUE);
            case Mp2Kind.OBJ_DESERTTENT:
                return new Monster(MonsterKind.NOMAD);

            case Mp2Kind.OBJ_TROLLBRIDGE:
                return new Monster(MonsterKind.TROLL);
            case Mp2Kind.OBJ_DRAGONCITY:
                return new Monster(MonsterKind.RED_DRAGON);
            case Mp2Kind.OBJ_CITYDEAD:
                return new Monster(MonsterKind.POWER_LICH);

            case Mp2Kind.OBJ_ANCIENTLAMP:
                return new Monster(MonsterKind.GENIE);

            // loyalty version
            case Mp2Kind.OBJ_WATERALTAR:
                return new Monster(MonsterKind.WATER_ELEMENT);
            case Mp2Kind.OBJ_AIRALTAR:
                return new Monster(MonsterKind.AIR_ELEMENT);
            case Mp2Kind.OBJ_FIREALTAR:
                return new Monster(MonsterKind.FIRE_ELEMENT);
            case Mp2Kind.OBJ_EARTHALTAR:
                return new Monster(MonsterKind.EARTH_ELEMENT);
            case Mp2Kind.OBJ_BARROWMOUNDS:
                return new Monster(MonsterKind.GHOST);

            case Mp2Kind.OBJ_MONSTER:
                return new Monster(GetQuantity3());

            default:
                break;
        }
        var world = World.Instance;

        return Mp2.isCaptureObject(GetObject(false))
                ? world.GetCapturedObject(GetIndex()).GetTroop()._monster
                : new Monster(MonsterKind.UNKNOWN);
    }

    private Sprite[] RedrawMonster(Painter dst) {

        if (GetIndex() == 288) {
            System.out.println("Care!");
        }
        // scan hero around
        MapsIndexes v = new MapsIndexes();
        ScanAroundObject(GetIndex(), Mp2Kind.OBJ_HEROES, v);
        var world = World.Instance;
        var dst_index = -1;
        int[] elements = v.values.elements();
        var sizeLoop = v.values.size();
        for (int i = 0; i < sizeLoop; i++) {
            int it = elements[i];
            var tile = world.GetTiles(it);
            dst_index = it;

            if (Mp2Kind.OBJ_HEROES != mp2_object ||
                    // skip bottom, bottom_right, bottom_left with ground objects
                    (((DIRECTION_BOTTOM_ROW & Direction.Get(GetIndex(), it)) != 0)
                            && Mp2.isGroundObject(tile.GetObject(false)))
                    ||
                    // skip ground check
                    tile.isWater() != isWater())
                dst_index = -1;
            else
                break;
        }

        var sprite_index = QuantityMonster().GetSpriteIndex();
        var conf = Settings.Get();
        // draw attack sprite
        if (-1 != dst_index && !conf.ExtWorldOnlyFirstMonsterAttack()) {
            var revert = false;

            switch (Direction.Get(GetIndex(), dst_index)) {
                case Direction.TOP_LEFT:
                case Direction.LEFT:
                case Direction.BOTTOM_LEFT:
                    revert = true;
                    break;
                default:
                    break;
            }

            var sprite_first = Agg.GetICN(IcnKind.MINIMON, sprite_index * 9 + (revert ? 8 : 7));
            return new Sprite[]{sprite_first};

        } else {

            // draw first sprite
            var sprite_first = Agg.GetICN(IcnKind.MINIMON, sprite_index * 9);
            /*area.BlitOnTile(dst, sprite_first, sprite_first.x() + 16, TILEWIDTH + sprite_first.y(), mp);

            // draw second sprite
         var sprite_next = Agg.GetICNIcnKind.MINIMON, sprite_index * 9 + 1 +
                monster_animation_cicle[
                        (Game.MapsAnimationFrame() + mp.x * mp.y) %
            ARRAY_COUNT(monster_animation_cicle)]);
            area.BlitOnTile(dst, sprite_next, sprite_next.x() + 16, TILEWIDTH + sprite_next.y(), mp);
*/

        }
        return null;
    }

    private boolean isWater() {
        return 30 > TileSpriteIndex();
    }

    private void ScanAroundObject(int getIndex, int objHeroes, MapsIndexes v) {
    }

    private void RedrawBoat(Painter dst) {

    }

    public void RedrawTop(Painter dst) {
        //TODO
    }

    public boolean isFog(int colors) {
        //TODO
        return false;
    }

    public void RedrawFogs(Painter dst, int colors) {
    }

    public Bitmap RedrawTile() {
        var surface = GetTileSurface();
        return surface;

    }

    private H2Point GetPoint(int index) {
        H2Point res = new H2Point();
        var world = World.Instance;
        res.x = index % world.w;
        res.y = index / world.w;
        return res;
    }

    private Bitmap GetTileSurface() {
        return Agg.GetTIL(TilKind.GROUND32, TileSpriteIndex(), TileSpriteShape());
    }

    private int TileSpriteShape() {
        return toUShort(pack_sprite_index) >> 14;
    }

    private int TileSpriteIndex() {
        return pack_sprite_index & 0x3FFF;
    }
}
