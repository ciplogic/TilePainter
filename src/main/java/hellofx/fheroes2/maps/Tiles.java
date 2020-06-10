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

    public TilesAddon FindObjectConst(int objs) {
        //TODO
        return null;
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
            /*
            // draw first sprite
         var sprite_first = AGG.GetICN(ICN.MINIMON, sprite_index * 9);
            area.BlitOnTile(dst, sprite_first, sprite_first.x() + 16, TILEWIDTH + sprite_first.y(), mp);

            // draw second sprite
         var sprite_next = AGG.GetICN(ICN.MINIMON, sprite_index * 9 + 1 +
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
