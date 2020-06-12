package hellofx.fheroes2.maps;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Bitmap;
import hellofx.fheroes2.agg.Sprite;
import hellofx.fheroes2.agg.TilKind;
import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.army.Troop;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.common.RandQueue;
import hellofx.fheroes2.game.DifficultyEnum;
import hellofx.fheroes2.game.Game;
import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.heroes.Secondary;
import hellofx.fheroes2.kingdom.CapturedObject;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.kingdom.WeekKind;
import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.monster.Monster;
import hellofx.fheroes2.monster.MonsterJoin;
import hellofx.fheroes2.monster.MonsterKind;
import hellofx.fheroes2.monster.MonsterLevel;
import hellofx.fheroes2.resource.*;
import hellofx.fheroes2.spell.Spell;
import hellofx.fheroes2.spell.SpellKind;
import hellofx.fheroes2.system.Settings;
import hellofx.framework.controls.Painter;

import java.util.List;
import java.util.function.Predicate;

import static hellofx.common.Utilities.find_if;
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
    public int maps_x;
    public int maps_y;

    private final static int[] monster_animation_cicle = new int[]{0, 1, 2, 1, 0, 3, 4, 5, 4, 3};

    public void FixedPreload() {
        var tile = this;
        var it = find_if(addons_level1._items, this::isSkeletonFix);
        if (it != null) {
            tile.SetObject(Mp2Kind.OBJ_SKELETON);
        }
        // fix price loyalty objects.
        if (!Settings.Get().PriceLoyaltyVersion())
            return;
        switch (tile.GetObject()) {
            case Mp2Kind.OBJ_UNKNW_79:
            case Mp2Kind.OBJ_UNKNW_7A:
            case Mp2Kind.OBJ_UNKNW_F9:
            case Mp2Kind.OBJ_UNKNW_FA: {
                int newobj = Mp2Kind.OBJ_ZERO;
                it = find_if(tile.addons_level1._items,
                        TilesAddon::isX_LOC123);
                if (it != null) {
                    newobj = TilesAddon.GetLoyaltyObject(it);
                } else {
                    it = find_if(tile.addons_level2._items, TilesAddon::isX_LOC123);
                    if (it != null)
                        newobj = TilesAddon.GetLoyaltyObject(it);
                }

                if (Mp2Kind.OBJ_ZERO != newobj)
                    tile.SetObject(newobj);
            }
            break;

            default:
                break;
        }
    }

    public void Init(int index, Mp2Tile mp2) {
        tile_passable = Direction.DIRECTION_ALL;
        quantity1 = mp2.quantity1;
        quantity2 = mp2.quantity2;
        quantity3 = 0;
        fog_colors = H2Color.ALL;

        SetTile(mp2.tileIndex, mp2.shape);
        SetIndex(index);
        SetObject(mp2.generalObject);

        addons_level1._items.clear();
        addons_level2._items.clear();

        AddonsPushLevel1(mp2);
        AddonsPushLevel2(mp2);
    }

    private void AddonsPushLevel1(Mp2Tile mp2) {
        if (mp2.objectName1 != 0 && toByte(mp2.indexName1) < 0xFF)
            AddonsPushLevel1(new TilesAddon(0, mp2.uniqNumber1, mp2.objectName1, mp2.indexName1));
    }

    private void AddonsPushLevel2(Mp2Tile mp2) {
        if (mp2.objectName2 != 0 && toByte(mp2.indexName2) < 0xFF)
            AddonsPushLevel1(new TilesAddon(0, mp2.uniqNumber2, mp2.objectName2, mp2.indexName2));
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
        addons_level1._items.sort((ta1, ta2) -> {
            var level1 = ta1.level % 4;
            var level2 = ta2.level % 4;
            if (level1 == level2)
                return 0;
            return level1 > level2 ? 1 : -1;
        });
        addons_level2._items.sort((ta1, ta2) -> {
            var level1 = ta1.level % 4;
            var level2 = ta2.level % 4;
            if (level1 == level2)
                return 0;
            return level1 > level2 ? -1 : 1;
        });
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
                (toByte(ta.index) >= 217 && toByte(ta.index) <= 220);
    }

    boolean isJail(TilesAddon ta) {
        return IcnKind.X_LOC2 == Mp2.GetICNObject(ta.object) && 0x09 == ta.index;
    }

    boolean isEvent(TilesAddon ta) {
        // OBJNMUL2
        return IcnKind.OBJNMUL2 == Mp2.GetICNObject(ta.object) && 0xA3 == toByte(ta.index);
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
        return IcnKind.OBJNTWRD == Mp2.GetICNObject(ta.object) && 32 > toByte(ta.index);
    }

    boolean isRandomMonster(TilesAddon ta) {
        // MONS32
        return IcnKind.MONS32 == Mp2.GetICNObject(ta.object) &&
                (0x41 < toByte(ta.index) && 0x47 > toByte(ta.index));
    }

    boolean isBarrier(TilesAddon ta) {
        return IcnKind.X_LOC3 == Mp2.GetICNObject(ta.object) &&
                60 <= ta.index && 102 >= ta.index && 0 == toByte(ta.index) % 6;
    }

    TilesAddon find_if_reverse(List<TilesAddon> addons, Predicate<TilesAddon> isFound) {
        for (var i = addons.size() - 1; i >= 0; i--) {
            var item = addons.get(i);
            if (isFound.test(item))
                return item;
        }
        return null;
    }

/*
    TilesAddon find_if(List<TilesAddon> addons, Predicate<TilesAddon> isFound) {
        return addons.stream().filter(isFound::test).findFirst().orElse(null);
    }
*/

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
            dst_index = i;

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
            int firstSpriteIndex = sprite_index * 9;
            var sprite_first = Agg.GetICN(IcnKind.MINIMON, firstSpriteIndex);
            /*area.BlitOnTile(dst, sprite_first, sprite_first.x() + 16, TILEWIDTH + sprite_first.y(), mp);*/

            // draw second sprite

            int secondSpriteIndex = sprite_index * 9 + 1 +
                    monster_animation_cicle[(Game.MapsAnimationFrame()) % monster_animation_cicle.length];
            var sprite_next = Agg.GetICN(IcnKind.MINIMON, secondSpriteIndex);

            return new Sprite[]{sprite_first, sprite_next};
        }
    }

    public boolean isWater() {
        return 30 > TileSpriteIndex();
    }

    private void ScanAroundObject(int getIndex, int objHeroes, MapsIndexes v) {
    }

    private void RedrawBoat(Painter dst) {

    }

    public Sprite RedrawTop(Painter dst) {

        var tileObject = GetObject();
        // animate objects
        if (Mp2Kind.OBJ_ABANDONEDMINE == tileObject) {
            var anime_sprite = Agg.GetICN(IcnKind.OBJNHAUN, Game.MapsAnimationFrame() % 15);
            return anime_sprite;
        } else if (Mp2Kind.OBJ_MINES == GetObject()) {
            var addon = FindObjectConst(Mp2Kind.OBJ_MINES);
            if (addon != null && addon.tmp == SpellKind.HAUNT) {
                var anime_sprite = Agg.GetICN(IcnKind.OBJNHAUN, Game.MapsAnimationFrame() % 15);
                return anime_sprite;
            } else if (addon != null && addon.tmp >= SpellKind.SETEGUARDIAN && addon.tmp <= SpellKind.SETWGUARDIAN) {
                //return Agg.GetICN(IcnKind.MONS32, new Monster(new Spell(addon.tmp)).GetSpriteIndex());

            }
        }

        //TODO
        return null;
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

    @Override
    public String toString() {
        return "{" +
                "{x:" + maps_x + ",y:" + maps_y + "}, addons1=" + addons_level1 +
                ", addons2=" + addons_level2 +
                ", index=" + maps_index +
                ", sprite_index=" + pack_sprite_index +
                ", passable=" + tile_passable +
                ", mp2_object=" + toByte(mp2_object) +
                ", quantity1=" + toByte(quantity1) +
                ", quantity2=" + toByte(quantity2) +
                ", quantity3=" + toByte(quantity3) +
                '}';
    }

    void QuantitySetSkill(int skill) {
        if (GetObject(false) == Mp2Kind.OBJ_WITCHSHUT) {
            quantity1 = (byte) skill;
        }
    }

    public void QuantityUpdate() {
        var world = World.Instance;
        switch (GetObject(false)) {
            case Mp2Kind.OBJ_WITCHSHUT:
                QuantitySetSkill(Secondary.RandForWitchHut());
                break;

            case Mp2Kind.OBJ_SHRINE1: {
                var spell = Rand.GetBool() ? Spell.RandCombat(1) : Spell.RandAdventure(1);
                QuantitySetSpell(spell.id);
            }
            break;

            case Mp2Kind.OBJ_SHRINE2: {
                var spell = Rand.GetBool() ? Spell.RandCombat(2) : Spell.RandAdventure(2);
                QuantitySetSpell(spell.id);
            }
            break;

            case Mp2Kind.OBJ_SHRINE3: {
                var spell = Rand.GetBool() ? Spell.RandCombat(3) : Spell.RandAdventure(3);
                QuantitySetSpell(spell.id);
            }
            break;

            case Mp2Kind.OBJ_SKELETON: {
                RandQueue percents = new RandQueue();
                // 80%: empty
                percents.Push(0, 80);
                // 20%: artifact 1 or 2 or 3
                percents.Push(1, 20);

                if (percents.Get() != 0)
                    QuantitySetArtifact(Artifact.Rand(ArtifactLevel.ART_LEVEL123));
                else
                    QuantityReset();
            }
            break;

            case Mp2Kind.OBJ_WAGON: {
                quantity2 = 0;

                RandQueue percents = new RandQueue();
                // 20%: empty
                percents.Push(0, 20);
                // 10%: artifact 1 or 2
                percents.Push(1, 10);
                // 50%: resource
                percents.Push(2, 50);

                switch (percents.Get()) {
                    case 1:
                        QuantitySetArtifact(Artifact.Rand(Rand.GetBool() ? ArtifactLevel.ART_LEVEL1 : ArtifactLevel.ART_LEVEL2));
                        break;
                    case 2:
                        QuantitySetResource(Resource.Rand(), Rand.Get(2, 5));
                        break;
                    default:
                        QuantityReset();
                        break;
                }
            }
            break;

            case Mp2Kind.OBJ_ARTIFACT: {
                TilesAddon addon = FindObject(Mp2Kind.OBJ_ARTIFACT);
                if (addon != null) {
                    int art = Artifact.FromMP2IndexSprite(addon.index).GetID();

                    if (ArtifactKind.UNKNOWN != art) {
                        if (art == ArtifactKind.SPELL_SCROLL) {
                            QuantitySetVariant(15);
                            // spell from origin mp2
                            QuantitySetSpell(1 + (quantity2 * 256 + quantity1) / 8);
                        } else {
                            // 0: 70% none
                            // 1,2,3 - 2000g, 2500g+3res, 3000g+5res,
                            // 4,5 - need have skill wisard or leadership,
                            // 6 - 50 rogues, 7 - 1 gin, 8,9,10,11,12,13 - 1 monster level4,
                            // 15 - spell
                            int cond = Rand.Get(1, 10) < 4 ? Rand.Get(1, 13) : 0;

                            // always available
                            if (Settings.Get().ExtWorldNoRequirementsForArtifacts())
                                cond = 0;

                            QuantitySetVariant(cond);
                            QuantitySetArtifact(art);

                            if (cond == 2 || cond == 3)
                                QuantitySetExt(Resource.GetIndexSprite2(Resource.Rand()) + 1);
                        }
                    }
                }
            }
            break;

            case Mp2Kind.OBJ_RESOURCE: {
                TilesAddon addon = FindObject(Mp2Kind.OBJ_RESOURCE);
                if (addon != null) {
                    int res = Resource.FromIndexSprite(addon.index);
                    var count = 0;

                    switch (res) {
                        case ResourceKind.GOLD:
                            count = 100 * Rand.Get(5, 10);
                            break;
                        case ResourceKind.WOOD:
                        case ResourceKind.ORE:
                            count = Rand.Get(5, 10);
                            break;
                        default:
                            count = Rand.Get(3, 6);
                            break;
                    }

                    QuantitySetResource(res, count);
                }
            }
            break;

            case Mp2Kind.OBJ_CAMPFIRE: {
                // 4-6 rnd resource and + 400-600 gold
                QuantitySetResource(Resource.Rand(), Rand.Get(4, 6));
            }
            break;

            case Mp2Kind.OBJ_MAGICGARDEN:
                // 5 gems or 500 gold
                if (Rand.GetBool())
                    QuantitySetResource(ResourceKind.GEMS, 5);
                else
                    QuantitySetResource(ResourceKind.GOLD, 500);
                break;

            case Mp2Kind.OBJ_WATERWHEEL:
                // first week 500 gold, next week 1000 gold
                QuantitySetResource(ResourceKind.GOLD, 0 == world.CountDay() ? 500 : 1000);
                break;

            case Mp2Kind.OBJ_WINDMILL: {
                int res = ResourceKind.WOOD;
                // except: wood, bugs: #3117478
                while (res == ResourceKind.WOOD) res = Resource.Rand();

                // 2 rnd resource
                QuantitySetResource(res, 2);
            }
            break;

            case Mp2Kind.OBJ_LEANTO:
                // 1-4 rnd resource
                QuantitySetResource(Resource.Rand(), Rand.Get(1, 4));
                break;

            case Mp2Kind.OBJ_FLOTSAM: {
                switch (Rand.Get(1, 4)) {
                    // 25%: empty
                    default:
                        break;
                    // 25%: 500 gold + 10 wood
                    case 1:
                        QuantitySetResource(ResourceKind.GOLD, 500);
                        quantity1 = 10;
                        break;
                    // 25%: 200 gold + 5 wood
                    case 2:
                        QuantitySetResource(ResourceKind.GOLD, 200);
                        quantity1 = 5;
                        break;
                    // 25%: 5 wood
                    case 3:
                        quantity1 = 5;
                        break;
                }
            }
            break;

            case Mp2Kind.OBJ_SHIPWRECKSURVIROR: {
                RandQueue percents = new RandQueue();
                // 55%: artifact 1
                percents.Push(1, 55);
                // 30%: artifact 2
                percents.Push(1, 30);
                // 15%: artifact 3
                percents.Push(1, 15);

                // variant
                switch (percents.Get()) {
                    case 1:
                        QuantitySetArtifact(Artifact.Rand(ArtifactLevel.ART_LEVEL1));
                        break;
                    case 2:
                        QuantitySetArtifact(Artifact.Rand(ArtifactLevel.ART_LEVEL2));
                        break;
                    default:
                        QuantitySetArtifact(Artifact.Rand(ArtifactLevel.ART_LEVEL3));
                        break;
                }
            }
            break;

            case Mp2Kind.OBJ_WATERCHEST: {
                RandQueue percents = new RandQueue();
                // 20% - empty
                percents.Push(0, 20);
                // 70% - 1500 gold
                percents.Push(1, 70);
                // 10% - 1000 gold + art
                percents.Push(2, 10);

                int art = ArtifactKind.UNKNOWN;
                int gold = 0;

                // variant
                switch (percents.Get()) {
                    default:
                        break; // empty
                    case 1:
                        gold = 1500;
                        break;
                    case 2:
                        gold = 1000;
                        art = Artifact.Rand(ArtifactLevel.ART_LEVEL1);
                        break;
                }

                QuantitySetResource(ResourceKind.GOLD, gold);
                QuantitySetArtifact(art);
            }
            break;

            case Mp2Kind.OBJ_TREASURECHEST:
                if (isWater()) {
                    SetObject(Mp2Kind.OBJ_WATERCHEST);
                    QuantityUpdate();
                } else {
                    UpdateTreasureChestSprite(this);

                    RandQueue percents = new RandQueue();
                    // 31% - 2000 gold or 1500 exp
                    percents.Push(1, 31);
                    // 32% - 1500 gold or 1000 exp
                    percents.Push(2, 32);
                    // 32% - 1000 gold or 500 exp
                    percents.Push(3, 32);
                    // 5% - art
                    percents.Push(4, 5);

                    int art = ArtifactKind.UNKNOWN;
                    int gold = 0;

                    // variant
                    switch (percents.Get()) {
                        case 1:
                            gold = 2000;
                            break;
                        case 2:
                            gold = 1500;
                            break;
                        case 3:
                            gold = 1000;
                            break;
                        default:
                            art = Artifact.Rand(ArtifactLevel.ART_LEVEL1);
                            break;
                    }

                    QuantitySetResource(ResourceKind.GOLD, gold);
                    QuantitySetArtifact(art);
                }
                break;

            case Mp2Kind.OBJ_DERELICTSHIP:
                QuantitySetResource(ResourceKind.GOLD, 5000);
                break;

            case Mp2Kind.OBJ_SHIPWRECK: {
                RandQueue percents = new RandQueue();
                // 40% - 10ghost(1000g)
                percents.Push(1, 40);
                // 30% - 15 ghost(2000g)
                percents.Push(2, 30);
                // 20% - 25ghost(5000g)
                percents.Push(3, 20);
                // 10% - 50ghost(2000g+art)
                percents.Push(4, 10);

                int cond = percents.Get();

                QuantitySetVariant(cond);
                QuantitySetArtifact(cond == 4 ? Artifact.Rand(ArtifactLevel.ART_LEVEL123) : ArtifactKind.UNKNOWN);
            }
            break;

            case Mp2Kind.OBJ_GRAVEYARD:
                // 1000 gold + art
                QuantitySetResource(ResourceKind.GOLD, 1000);
                QuantitySetArtifact(Artifact.Rand(ArtifactLevel.ART_LEVEL123));
                break;

            case Mp2Kind.OBJ_PYRAMID: {
                // random spell level 5
                Spell spell = Rand.GetBool() ? Spell.RandCombat(5) : Spell.RandAdventure(5);
                QuantitySetSpell(spell.id);
            }
            break;

            case Mp2Kind.OBJ_DAEMONCAVE: {
                // 1000 exp or 1000 exp + 2500 gold or 1000 exp + art or (-2500 or remove hero)
                int cond = Rand.Get(1, 4);
                QuantitySetVariant(cond);
                QuantitySetArtifact(cond == 3 ? Artifact.Rand(ArtifactLevel.ART_LEVEL123) : ArtifactKind.UNKNOWN);
            }
            break;

            case Mp2Kind.OBJ_TREEKNOWLEDGE:
                // variant: 10 gems, 2000 gold or free
                switch (Rand.Get(1, 3)) {
                    case 1:
                        QuantitySetResource(ResourceKind.GEMS, 10);
                        break;
                    case 2:
                        QuantitySetResource(ResourceKind.GOLD, 2000);
                        break;
                    default:
                        break;
                }
                break;

            case Mp2Kind.OBJ_BARRIER: {
                var it = find_if_reverse(addons_level1._items,
                        (TilesAddon ta) -> {
                            return TilesAddon.ColorFromBarrierSprite(ta) != 0;
                        });
                if (it != null)
                    QuantitySetColor(TilesAddon.ColorFromBarrierSprite(it));
            }
            break;

            case Mp2Kind.OBJ_TRAVELLERTENT: {
                var it = find_if_reverse(addons_level1._items,
                        (TilesAddon ta) -> {
                            return TilesAddon.ColorFromTravellerTentSprite(ta) != 0;
                        });
                if (it != null)
                    QuantitySetColor(TilesAddon.ColorFromTravellerTentSprite(it));
            }
            break;

            case Mp2Kind.OBJ_ALCHEMYLAB:
                QuantitySetResource(ResourceKind.MERCURY, 1);
                break;

            case Mp2Kind.OBJ_SAWMILL:
                QuantitySetResource(ResourceKind.WOOD, 2);
                break;

            case Mp2Kind.OBJ_MINES: {
                TilesAddon addon = FindObject(Mp2Kind.OBJ_MINES);
                if (addon != null)
                    switch (addon.index) {
                        case 0:
                            QuantitySetResource(ResourceKind.ORE, 2);
                            break;
                        case 1:
                            QuantitySetResource(ResourceKind.SULFUR, 1);
                            break;
                        case 2:
                            QuantitySetResource(ResourceKind.CRYSTAL, 1);
                            break;
                        case 3:
                            QuantitySetResource(ResourceKind.GEMS, 1);
                            break;
                        case 4:
                            QuantitySetResource(ResourceKind.GOLD, 1000);
                            break;
                        default:
                            break;
                    }
            }
            break;

            case Mp2Kind.OBJ_ABANDONEDMINE: {
                CapturedObject object = world.GetCapturedObject(GetIndex());
                //TODO: fix NPE
                if (object == null) {
                    break;
                }
                Troop troop = object.GetTroop();

                // I checked in Heroes II: min 3 x 13, and max 3 x 15
                troop.Set(MonsterKind.GHOST, 3 * Rand.Get(13, 15));

                if (!Settings.Get().ExtWorldAbandonedMineRandom())
                    QuantitySetResource(ResourceKind.GOLD, 1000);
                else
                    switch (Rand.Get(1, 5)) {
                        case 1:
                            QuantitySetResource(ResourceKind.ORE, 2);
                            break;
                        case 2:
                            QuantitySetResource(ResourceKind.SULFUR, 1);
                            break;
                        case 3:
                            QuantitySetResource(ResourceKind.CRYSTAL, 1);
                            break;
                        case 4:
                            QuantitySetResource(ResourceKind.GEMS, 1);
                            break;
                        default:
                            QuantitySetResource(ResourceKind.GOLD, 1000);
                            break;
                    }
            }
            break;

            case Mp2Kind.OBJ_STONELIGHTS:
                UpdateStoneLightsSprite(this);
                break;

            case Mp2Kind.OBJ_FOUNTAIN:
                UpdateFountainSprite(this);
                break;

            case Mp2Kind.OBJ_EVENT: {
                TilesAddon addon = FindObject(Mp2Kind.OBJ_EVENT);
                // remove event sprite
                if (addon != null) Remove(addon.uniq);
            }
            break;

            case Mp2Kind.OBJ_BOAT: {
                TilesAddon addon = FindObject(Mp2Kind.OBJ_BOAT);
                // remove small sprite boat
                if (addon != null) Remove(addon.uniq);
            }
            break;

            case Mp2Kind.OBJ_RNDARTIFACT:
            case Mp2Kind.OBJ_RNDARTIFACT1:
            case Mp2Kind.OBJ_RNDARTIFACT2:
            case Mp2Kind.OBJ_RNDARTIFACT3:
                // modify rnd artifact sprite
                UpdateRNDArtifactSprite(this);
                //TODO: infinite recursive call
                //QuantityUpdate();
                break;

            case Mp2Kind.OBJ_RNDRESOURCE:
                // modify rnd resource sprite
                UpdateRNDResourceSprite(this);
                //TODO: infinite recursive call
                //QuantityUpdate();
                break;

            case Mp2Kind.OBJ_MONSTER:
                if (world.CountWeek() > 1)
                    UpdateMonsterPopulation(this);
                else
                    UpdateMonsterInfo(this);
                break;

            case Mp2Kind.OBJ_RNDMONSTER:
            case Mp2Kind.OBJ_RNDMONSTER1:
            case Mp2Kind.OBJ_RNDMONSTER2:
            case Mp2Kind.OBJ_RNDMONSTER3:
            case Mp2Kind.OBJ_RNDMONSTER4:
                // modify rnd monster sprite
                UpdateMonsterInfo(this);
                break;

            // join dwelling
            case Mp2Kind.OBJ_ANCIENTLAMP:
                MonsterSetCount(QuantityMonster().GetRNDSize(true));
                break;

            case Mp2Kind.OBJ_WATCHTOWER:
            case Mp2Kind.OBJ_EXCAVATION:
            case Mp2Kind.OBJ_CAVE:
            case Mp2Kind.OBJ_TREEHOUSE:
            case Mp2Kind.OBJ_ARCHERHOUSE:
            case Mp2Kind.OBJ_GOBLINHUT:
            case Mp2Kind.OBJ_DWARFCOTT:
            case Mp2Kind.OBJ_HALFLINGHOLE:
            case Mp2Kind.OBJ_PEASANTHUT:
            case Mp2Kind.OBJ_THATCHEDHUT:
                // recruit dwelling
            case Mp2Kind.OBJ_RUINS:
            case Mp2Kind.OBJ_TREECITY:
            case Mp2Kind.OBJ_WAGONCAMP:
            case Mp2Kind.OBJ_DESERTTENT:
            case Mp2Kind.OBJ_TROLLBRIDGE:
            case Mp2Kind.OBJ_DRAGONCITY:
            case Mp2Kind.OBJ_CITYDEAD:
            case Mp2Kind.OBJ_WATERALTAR:
            case Mp2Kind.OBJ_AIRALTAR:
            case Mp2Kind.OBJ_FIREALTAR:
            case Mp2Kind.OBJ_EARTHALTAR:
                UpdateDwellingPopulation(this);
                break;

            case Mp2Kind.OBJ_BARROWMOUNDS:
                if (!Settings.Get().ExtWorldDisableBarrowMounds())
                    UpdateDwellingPopulation(this);
                break;

            default:
                break;
        }
    }

    private void UpdateRNDResourceSprite(Tiles tiles) {
        //TODO
    }

    public int MonsterCount() {
        return toByte(quantity1) << 8 | quantity2;
    }

    private void MonsterSetCount(int count) {
        quantity1 = (byte) (count >> 8);
        quantity2 = (byte) (0x00FF & count);
    }

    private void UpdateStoneLightsSprite(Tiles tiles) {
        //TODO
    }

    private void UpdateMonsterPopulation(Tiles tiles) {
        //TODO
    }

    private void UpdateFountainSprite(Tiles tiles) {
        //TODO
    }

    private void UpdateTreasureChestSprite(Tiles tiles) {
        //TODO
    }

    private void UpdateRNDArtifactSprite(Tiles tiles) {
        //TODO
    }

    private void UpdateMonsterInfo(Tiles tile) {
        int mons = -1;

        if (Mp2Kind.OBJ_MONSTER == tile.GetObject()) {
            TilesAddon addon = tile.FindObject(Mp2Kind.OBJ_MONSTER);

            if (addon != null)
                mons = new Monster(addon.index + 1).id; // ICN.MONS32 start from PEASANT
        } else {
            TilesAddon addon = tile.FindObject(Mp2Kind.OBJ_RNDMONSTER);

            switch (tile.GetObject()) {
                case Mp2Kind.OBJ_RNDMONSTER:
                    mons = Monster.Rand(MonsterLevel.LEVEL0);
                    break;
                case Mp2Kind.OBJ_RNDMONSTER1:
                    mons = Monster.Rand(MonsterLevel.LEVEL1);
                    break;
                case Mp2Kind.OBJ_RNDMONSTER2:
                    mons = Monster.Rand(MonsterLevel.LEVEL2);
                    break;
                case Mp2Kind.OBJ_RNDMONSTER3:
                    mons = Monster.Rand(MonsterLevel.LEVEL3);
                    break;
                case Mp2Kind.OBJ_RNDMONSTER4:
                    mons = Monster.Rand(MonsterLevel.LEVEL4);
                    break;
                default:
                    break;
            }

            // fixed random sprite
            tile.SetObject(Mp2Kind.OBJ_MONSTER);

            if (addon != null)
                addon.index = (byte) (mons - 1); // ICN.MONS32 start from PEASANT
        }

        var count = 0;

        // update count (mp2 format)
        if (tile.quantity1 != 0 || tile.quantity2 != 0) {
            count = tile.quantity2;
            count <<= 8;
            count |= tile.quantity1;
            count >>= 3;
        }

        PlaceMonsterOnTile(tile, mons, count);

    }

    private void PlaceMonsterOnTile(Tiles tile, int mons, int count) {
        var monster = new Monster(mons);
        tile.SetObject(Mp2Kind.OBJ_MONSTER);
        // monster type
        tile.SetQuantity3(mons);

        if (count != 0) {
            tile.MonsterSetFixedCount();
            tile.MonsterSetCount(count);
        } else {
            int mul = 4;

            // set random count
            switch (Settings.Get().GameDifficulty()) {
                case DifficultyEnum.EASY:
                    mul = 3;
                    break;
                case DifficultyEnum.NORMAL:
                    mul = 4;
                    break;
                case DifficultyEnum.HARD:
                    mul = 4;
                    break;
                case DifficultyEnum.EXPERT:
                    mul = 5;
                    break;
                case DifficultyEnum.IMPOSSIBLE:
                    mul = 6;
                    break;
                default:
                    break;
            }

            tile.MonsterSetCount(mul * monster.GetRNDSize(true));
        }

        var world = World.Instance;
        // skip join
        if (mons == MonsterKind.GHOST || monster.isElemental())
            tile.MonsterSetJoinCondition(MonsterJoin.JOIN_CONDITION_SKIP);
        else
            // fixed count: for money
            if (tile.MonsterFixedCount() != 0 ||
                    // month of monster
                    (world.GetWeekType().GetType() == WeekKind.MONSTERS &&
                            world.GetWeekType().GetMonster() == mons))
                tile.MonsterSetJoinCondition(MonsterJoin.JOIN_CONDITION_MONEY);
            else {
                // 20% chance for join
                if (3 > Rand.Get(1, 10))
                    tile.MonsterSetJoinCondition(MonsterJoin.JOIN_CONDITION_FREE);
                else
                    tile.MonsterSetJoinCondition(MonsterJoin.JOIN_CONDITION_MONEY);
            }

        //
        TilesAddon addon = tile.FindObject(Mp2Kind.OBJ_MONSTER);

        if (addon == null) {
            // add new sprite
            tile.AddonsPushLevel1(new TilesAddon(TilesAddonLevel.UPPER, World.Instance.GetUniq(), 0x33, monster.GetSpriteIndex()));
        } else if (toByte(addon.index) != mons - 1) {
            // fixed sprite
            addon.index = (byte) (mons - 1); // ICN.MONS32 start from PEASANT
        }
    }

    private int MonsterFixedCount() {
        var addon = FindObjectConst(Mp2Kind.OBJ_MONSTER);
        return addon != null ? addon.tmp & 0x80 : 0;
    }

    private void MonsterSetJoinCondition(int cond) {

        TilesAddon addon = FindObject(Mp2Kind.OBJ_MONSTER);
        if (addon == null)
            return;
        addon.tmp &= 0xFC;
        addon.tmp |= cond & 0x03;
    }

    private void MonsterSetFixedCount() {
        var addon = FindObject(Mp2Kind.OBJ_MONSTER);
        if (addon != null) addon.tmp |= 0x80;
    }

    private void SetQuantity3(int mod) {
        quantity3 = (byte) mod;
    }

    private void QuantitySetExt(int ext) {
        quantity2 &= 0xf0;
        quantity2 |= 0x0f & ext;
    }

    private void UpdateDwellingPopulation(Tiles tiles) {
        //TODO
    }

    private void QuantitySetVariant(int value) {
        quantity2 &= 0x0f;
        quantity2 |= value << 4;
    }

    private void QuantitySetColor(int col) {

        switch (GetObject(false)) {

            case Mp2Kind.OBJ_BARRIER:
            case Mp2Kind.OBJ_TRAVELLERTENT:
                quantity1 = (byte) col;
                break;

            default:
                World.Instance.CaptureObject(GetIndex(), col);
                break;
        }
    }

    private void QuantitySetSpell(int spell) {
        switch (GetObject(false)) {
            case Mp2Kind.OBJ_ARTIFACT:
            case Mp2Kind.OBJ_SHRINE1:
            case Mp2Kind.OBJ_SHRINE2:
            case Mp2Kind.OBJ_SHRINE3:
            case Mp2Kind.OBJ_PYRAMID:
                quantity1 = (byte) spell;
                break;

            default:
                break;
        }
    }

    private void QuantitySetResource(int res, int count) {
        quantity1 = (byte) res;
        quantity2 = (byte) (res == ResourceKind.GOLD ? count / 100 : count);
    }

    public void QuantityReset() {
        quantity1 = 0;
        quantity2 = 0;

        switch (GetObject(false)) {
            case Mp2Kind.OBJ_SKELETON:
            case Mp2Kind.OBJ_WAGON:
            case Mp2Kind.OBJ_ARTIFACT:
            case Mp2Kind.OBJ_SHIPWRECKSURVIROR:
            case Mp2Kind.OBJ_WATERCHEST:
            case Mp2Kind.OBJ_TREASURECHEST:
            case Mp2Kind.OBJ_SHIPWRECK:
            case Mp2Kind.OBJ_GRAVEYARD:
            case Mp2Kind.OBJ_DAEMONCAVE:
                QuantitySetArtifact(ArtifactKind.UNKNOWN);
                break;

            default:
                break;
        }

        if (Mp2.isPickupObject(mp2_object))
            SetObject(Mp2Kind.OBJ_ZERO);
    }

    public TilesAddon FindObject(int objs) {
        TilesAddon it = null;
        switch (objs) {
            case Mp2Kind.OBJ_CAMPFIRE:
                it = find_if(addons_level1._items, TilesAddon::isCampFire);
                break;

            case Mp2Kind.OBJ_TREASURECHEST:
            case Mp2Kind.OBJ_ANCIENTLAMP:
            case Mp2Kind.OBJ_RESOURCE:
                it = find_if(addons_level1._items, TilesAddon::isResource);
                break;

            case Mp2Kind.OBJ_RNDRESOURCE:
                it = find_if(addons_level1._items, TilesAddon::isRandomResource);
                break;

            case Mp2Kind.OBJ_FLOTSAM:
            case Mp2Kind.OBJ_SHIPWRECKSURVIROR:
            case Mp2Kind.OBJ_WATERCHEST:
            case Mp2Kind.OBJ_BOTTLE:
                it = find_if(addons_level1._items, TilesAddon::isWaterResource);
                break;

            case Mp2Kind.OBJ_ARTIFACT:
                it = find_if(addons_level1._items, TilesAddon::isArtifact);
                break;

            case Mp2Kind.OBJ_RNDARTIFACT:
                it = find_if(addons_level1._items, TilesAddon::isRandomArtifact);
                break;

            case Mp2Kind.OBJ_RNDARTIFACT1:
                it = find_if(addons_level1._items, TilesAddon::isRandomArtifact1);
                break;

            case Mp2Kind.OBJ_RNDARTIFACT2:
                it = find_if(addons_level1._items, TilesAddon::isRandomArtifact2);
                break;

            case Mp2Kind.OBJ_RNDARTIFACT3:
                it = find_if(addons_level1._items, TilesAddon::isRandomArtifact3);
                break;

            case Mp2Kind.OBJ_RNDULTIMATEARTIFACT:
                it = find_if(addons_level1._items, TilesAddon::isUltimateArtifact);
                break;

            case Mp2Kind.OBJ_MONSTER:
                it = find_if(addons_level1._items, TilesAddon::isMonster);
                break;

            case Mp2Kind.OBJ_WHIRLPOOL:
                it = find_if(addons_level1._items, TilesAddon::isWhirlPool);
                break;

            case Mp2Kind.OBJ_STANDINGSTONES:
                it = find_if(addons_level1._items, TilesAddon::isStandingStone);
                break;

            case Mp2Kind.OBJ_ARTESIANSPRING:
                it = find_if(addons_level1._items, TilesAddon::isArtesianSpring);
                break;

            case Mp2Kind.OBJ_OASIS:
                it = find_if(addons_level1._items, TilesAddon::isOasis);
                break;

            case Mp2Kind.OBJ_WATERINGHOLE:
                it = find_if(addons_level1._items, TilesAddon::isWateringHole);
                break;

            case Mp2Kind.OBJ_MINES:
                it = find_if(addons_level1._items, TilesAddon::isMine);
                break;

            case Mp2Kind.OBJ_JAIL:
                it = find_if(addons_level1._items, TilesAddon::isJail);
                break;

            case Mp2Kind.OBJ_EVENT:
                it = find_if(addons_level1._items, TilesAddon::isEvent);
                break;

            case Mp2Kind.OBJ_BOAT:
                it = find_if(addons_level1._items, TilesAddon::isBoat);
                break;

            case Mp2Kind.OBJ_BARRIER:
                it = find_if(addons_level1._items, TilesAddon::isBarrier);
                break;

            case Mp2Kind.OBJ_HEROES:
                it = find_if(addons_level1._items, TilesAddon::isMiniHero);
                break;

            case Mp2Kind.OBJ_CASTLE:
                it = find_if(addons_level1._items, TilesAddon::isCastle);
                if (it == null) {
                    it = find_if(addons_level2._items, TilesAddon::isCastle);
                    return it;
                }
                break;

            case Mp2Kind.OBJ_RNDCASTLE:
                it = find_if(addons_level1._items, TilesAddon::isRandomCastle);
                if (it == null) {
                    it = find_if(addons_level2._items, TilesAddon::isRandomCastle);
                    return it;
                }
                break;

            case Mp2Kind.OBJ_RNDMONSTER:
            case Mp2Kind.OBJ_RNDMONSTER1:
            case Mp2Kind.OBJ_RNDMONSTER2:
            case Mp2Kind.OBJ_RNDMONSTER3:
            case Mp2Kind.OBJ_RNDMONSTER4:
                it = find_if(addons_level1._items, TilesAddon::isRandomMonster);
                break;

            case Mp2Kind.OBJ_SKELETON:
                it = find_if(addons_level1._items, TilesAddon::isSkeleton);
                break;

            default:
                //FIXME for: " << Mp2Kind.StringObject(objs));
                break;
        }

        return it;
    }

    void QuantitySetArtifact(int art) {
        quantity1 = (byte) art;
    }

    public void CaptureFlags32(int obj, int color) {
        //TODO
    }

    public TilesAddon FindAddonICN1(int icn) {
        //TODO
        return null;
    }

    public void Remove(int uniq) {
        //TODO
    }

    public void UpdatePassable() {
        //TODO
    }

    public boolean GoodForUltimateArtifact() {
        //TODO
        return false;
    }

    public boolean isObject(int obj) {
        //TODO
        return false;
    }

    public H2Point GetCenter() {
        return new H2Point(maps_x, maps_y);
    }
}
