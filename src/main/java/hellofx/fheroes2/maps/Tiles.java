package hellofx.fheroes2.maps;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Bitmap;
import hellofx.fheroes2.agg.Sprite;
import hellofx.fheroes2.agg.TilKind;
import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.army.Troop;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.H2Size;
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
import hellofx.fheroes2.maps.objects.Maps;
import hellofx.fheroes2.monster.Monster;
import hellofx.fheroes2.monster.MonsterJoin;
import hellofx.fheroes2.monster.MonsterKind;
import hellofx.fheroes2.monster.MonsterLevel;
import hellofx.fheroes2.resource.*;
import hellofx.fheroes2.spell.Spell;
import hellofx.fheroes2.spell.SpellKind;
import hellofx.fheroes2.system.Settings;
import hellofx.framework.controls.Painter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static hellofx.common.Utilities.find_if;
import static hellofx.common.Utilities.writeTodo;
import static hellofx.fheroes2.heroes.Direction.DIRECTION_BOTTOM_ROW;
import static hellofx.fheroes2.kingdom.WorldDump.writeField;
import static hellofx.fheroes2.kingdom.WorldDump.writeFieldBare;
import static hellofx.fheroes2.maps.objects.Maps.GetDirectionIndex;
import static hellofx.fheroes2.serialize.ByteVectorReader.toUShort;


public class Tiles {
    public Addons addons_level1 = new Addons();
    public Addons addons_level2 = new Addons(); // 16
    public int maps_index = 0;
    public short pack_sprite_index = 0;
    public short tile_passable = 0;
    public short mp2_object = 0;
    public short fog_colors = 0;
    public short quantity1 = 0;
    public short quantity2 = 0;
    public short quantity3 = 0;
    public int maps_x;
    public int maps_y;

    private final static int[] monster_animation_cicle = new int[]{0, 1, 2, 1, 0, 3, 4, 5, 4, 3};

    static int PredicateSortRules(TilesAddon ta1, TilesAddon ta2) {
        var level1 = ta1.level % 4;
        var level2 = ta2.level % 4;
        if (level1 == level2)
            return 0;
        return level1 > level2 ? -1 : 1;
    }

    static boolean isStream(TilesAddon ta) {
        return IcnKind.STREAM == Mp2.GetICNObject(ta.getObject()) ||
                IcnKind.OBJNMUL2 == Mp2.GetICNObject(ta.getObject()) && ta.getIndex() < 14;
    }

    public String toJsonRow() {
        var sb = new StringBuilder();
        sb.append("{");
        writeField(sb, "maps_index", maps_index);
        writeField(sb, "quantity1", (quantity1));
        writeField(sb, "quantity2", (quantity2));
        writeField(sb, "quantity3", (quantity3));
        writeField(sb, "pack_sprite_index", toUShort(pack_sprite_index));

        writeAddons(sb, "addons_level1", addons_level1);
        writeAddons(sb, "addons_level2", addons_level2);
        writeFieldBare(sb, "mp2_object", (mp2_object));
        sb.append("}");
        return sb.toString();
    }

    private Artifact QuantityArtifact() {
        switch (GetObject(false)) {
            case Mp2Kind.OBJ_WAGON:
                return new Artifact(quantity2 != 0 ? ArtifactKind.UNKNOWN : quantity1);

            case Mp2Kind.OBJ_SKELETON:
            case Mp2Kind.OBJ_DAEMONCAVE:
            case Mp2Kind.OBJ_WATERCHEST:
            case Mp2Kind.OBJ_TREASURECHEST:
            case Mp2Kind.OBJ_SHIPWRECKSURVIROR:
            case Mp2Kind.OBJ_SHIPWRECK:
            case Mp2Kind.OBJ_GRAVEYARD:
                return new Artifact(quantity1);

            case Mp2Kind.OBJ_ARTIFACT: {
                if (QuantityVariant() == 15) {
                    Artifact art = new Artifact(ArtifactKind.SPELL_SCROLL);
                    art.SetSpell(QuantitySpell().GetID());
                    return art;
                }
                return new Artifact(quantity1);
            }

            default:
                break;
        }

        return new Artifact(ArtifactKind.UNKNOWN);
    }

    private Spell QuantitySpell() {
        switch (GetObject(false)) {
            case Mp2Kind.OBJ_ARTIFACT:
                return new Spell(QuantityVariant() == 15 ? quantity1 : SpellKind.NONE);

            case Mp2Kind.OBJ_SHRINE1:
            case Mp2Kind.OBJ_SHRINE2:
            case Mp2Kind.OBJ_SHRINE3:
            case Mp2Kind.OBJ_PYRAMID:
                return new Spell(quantity1);

            default:
                break;
        }

        return new Spell(SpellKind.NONE);
    }

    private int QuantityVariant() {
        return quantity2 >> 4;
    }


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

    public boolean QuantityIsValid() {
        switch (GetObject(false)) {
            case Mp2Kind.OBJ_ARTIFACT:
            case Mp2Kind.OBJ_RESOURCE:
            case Mp2Kind.OBJ_CAMPFIRE:
            case Mp2Kind.OBJ_FLOTSAM:
            case Mp2Kind.OBJ_SHIPWRECKSURVIROR:
            case Mp2Kind.OBJ_TREASURECHEST:
            case Mp2Kind.OBJ_WATERCHEST:
            case Mp2Kind.OBJ_ABANDONEDMINE:
                return true;

            case Mp2Kind.OBJ_PYRAMID:
                return QuantitySpell().isValid();

            case Mp2Kind.OBJ_SHIPWRECK:
            case Mp2Kind.OBJ_GRAVEYARD:
            case Mp2Kind.OBJ_DERELICTSHIP:
            case Mp2Kind.OBJ_WATERWHEEL:
            case Mp2Kind.OBJ_WINDMILL:
            case Mp2Kind.OBJ_LEANTO:
            case Mp2Kind.OBJ_MAGICGARDEN:
                return quantity2 != 0;

            case Mp2Kind.OBJ_SKELETON:
                return QuantityArtifact().GetID() != ArtifactKind.UNKNOWN;

            case Mp2Kind.OBJ_WAGON:
                return QuantityArtifact().GetID() != ArtifactKind.UNKNOWN || quantity2 != 0;

            case Mp2Kind.OBJ_DAEMONCAVE:
                return QuantityVariant() != 0;

            default:
                break;
        }

        return false;
    }

    private void writeAddons(StringBuilder sb, String name, Addons addons) {
        var strings = new ArrayList<String>();
        for (var a : addons._items) {
            strings.add(a.toJsonRow());
        }
        String builtArray = "[" + String.join(",", strings) + "]";
        writeField(sb, name, builtArray);
    }

    private void AddonsPushLevel1(Mp2Tile mp2) {
        if (mp2.objectName1 != 0 && (mp2.indexName1) < 0xFF)
            AddonsPushLevel1(new TilesAddon(0, mp2.uniqNumber1, mp2.objectName1, mp2.indexName1));
    }

    boolean isLongObject(int direction) {
        var world = World.Instance;
        var wSize = new H2Size(world.w, world.h);
        if (!isValidDirection(GetIndex(), direction, wSize)) {
            return false;
        }
        var tile = world.GetTiles(GetDirectionIndex(GetIndex(), direction));

        for (var it : addons_level1._items) {
            if (!Exclude4LongObject(it) &&
                    (HaveLongObjectUniq(tile.addons_level1, it.uniq) ||
                            !TilesAddon.isTrees(it) && HaveLongObjectUniq(tile.addons_level2, it.uniq))) {
                return true;
            }
        }
        return false;
    }

    boolean HaveLongObjectUniq(Addons level, int uid) {
        for (var it : level._items)
            if (!Exclude4LongObject(it) && it.isUniq(uid)) return true;
        return false;
    }

    static boolean Exclude4LongObject(TilesAddon ta) {
        return TilesAddon.isStream(ta) ||
                TilesAddon.isRoad(ta) || TilesAddon.isShadow(ta);

    }

    static boolean isMountsRocs(TilesAddon ta) {

        return TilesAddon.isMounts(ta) || TilesAddon.isRocs(ta);
    }

    static boolean isForestsTrees(TilesAddon ta) {
        return TilesAddon.isForests(ta) || TilesAddon.isTrees(ta) ||
                TilesAddon.isCactus(ta);
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

    private void AddonsPushLevel2(Mp2Tile mp2) {
        if (mp2.objectName2 != 0 && (mp2.indexName2) < 0xFF)
            AddonsPushLevel2(new TilesAddon(0, mp2.uniqNumber2, mp2.objectName2, mp2.indexName2));
    }

    public void AddonsSort() {
        addons_level1._items.sort(Tiles::PredicateSortRules);
        addons_level2._items.sort((ta1, ta2) -> -PredicateSortRules(ta1, ta2));
    }

    public int GetQuantity1() {
        return (quantity1);
    }

    public int GetQuantity2() {
        return (quantity2);
    }

    public int GetQuantity3() {
        return (quantity3);
    }

    public int GetObject() {
        return GetObject(true);
    }

    public void SetObject(int mp2Kind) {
        mp2_object = (byte) mp2Kind;
    }

    public int GetObject(boolean skip_hero) {
        if (!skip_hero && Mp2Kind.OBJ_HEROES == (mp2_object)) {
            var hero = GetHeroes();
            return hero != null ? hero.GetMapsObject() : Mp2Kind.OBJ_ZERO;
        }

        return (mp2_object);
    }

    public Heroes GetHeroes() {
        //TODO
        return null;
    }

    public int GetIndex() {
        return maps_index;
    }

    public void AddonsPushLevel1(Mp2Addon ma) {
        if (ma.objectNameN1 != 0 && (ma.indexNameN1) < 0xFF)
            AddonsPushLevel1(new TilesAddon(ma.quantityN, ma.uniqNumberN1, ma.objectNameN1, ma.indexNameN1));

    }

    private void AddonsPushLevel1(TilesAddon ta) {
        if (TilesAddon.ForceLevel2(ta))
            addons_level2._items.add(ta);
        else
            addons_level1._items.add(ta);
    }


    boolean isRoad(TilesAddon ta) {
        return IcnKind.ROAD == Mp2.GetICNObject(ta.getObject());
    }

    boolean isWaterResource(TilesAddon ta) {
        return IcnKind.OBJNWATR == Mp2.GetICNObject(ta.getObject()) &&
                (0 == ta.getIndex() || // buttle
                        19 == ta.getIndex() || // chest
                        45 == ta.getIndex() || // flotsam
                        111 == ta.getIndex()) // surviror
                ;
    }

    boolean isWhirlPool(TilesAddon ta) {
        return IcnKind.OBJNWATR == Mp2.GetICNObject(ta.getObject()) &&
                ta.getIndex() >= 202 && ta.getIndex() <= 225;
    }

    boolean isStandingStone(TilesAddon ta) {
        return IcnKind.OBJNMULT == Mp2.GetICNObject(ta.getObject()) &&
                (ta.getIndex() == 84 || ta.getIndex() == 85);
    }

    boolean isResource(TilesAddon ta) {
        // OBJNRSRC
        return IcnKind.OBJNRSRC == Mp2.GetICNObject(ta.getObject()) && ta.getIndex() % 2 != 0 ||
                // TREASURE
                IcnKind.TREASURE == Mp2.GetICNObject(ta.getObject());
    }

    boolean isRandomResource(TilesAddon ta) {
        // OBJNRSRC
        return IcnKind.OBJNRSRC == Mp2.GetICNObject(ta.getObject()) && 17 == ta.getIndex();
    }

    boolean isArtifact(TilesAddon ta) {
        // OBJNARTI (skip ultimate)
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.getObject()) && ta.getIndex() > 0x10 && ta.getIndex() % 2 != 0;
    }

    boolean isRandomArtifact(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.getObject()) && 0xA3 == ta.getIndex();
    }

    boolean isRandomArtifact1(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.getObject()) && 0xA7 == ta.getIndex();
    }

    boolean isRandomArtifact2(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.getObject()) && 0xA9 == ta.getIndex();
    }

    boolean isRandomArtifact3(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.getObject()) && 0xAB == ta.getIndex();
    }

    boolean isUltimateArtifact(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2.GetICNObject(ta.getObject()) && 0xA4 == ta.getIndex();
    }

    boolean isCampFire(TilesAddon ta) {
        // MTNDSRT
        return IcnKind.OBJNDSRT == Mp2.GetICNObject(ta.getObject()) && 61 == ta.getIndex() ||
                // OBJNMULT
                IcnKind.OBJNMULT == Mp2.GetICNObject(ta.getObject()) && 131 == ta.getIndex() ||
                // OBJNSNOW
                IcnKind.OBJNSNOW == Mp2.GetICNObject(ta.getObject()) && 4 == ta.getIndex();
    }

    boolean isMonster(TilesAddon ta) {
        // MONS32
        return IcnKind.MONS32 == Mp2.GetICNObject(ta.getObject());
    }

    boolean isArtesianSpring(TilesAddon ta) {
        return IcnKind.OBJNCRCK == Mp2.GetICNObject(ta.getObject()) &&
                (ta.getIndex() == 3 || ta.getIndex() == 4);
    }

    boolean isSkeleton(TilesAddon ta) {
        return IcnKind.OBJNDSRT == Mp2.GetICNObject(ta.getObject()) && ta.getIndex() == 84;
    }

    boolean isSkeletonFix(TilesAddon ta) {
        return IcnKind.OBJNDSRT == Mp2.GetICNObject(ta.getObject()) && ta.getIndex() == 83;
    }

    boolean isOasis(TilesAddon ta) {
        return IcnKind.OBJNDSRT == Mp2.GetICNObject(ta.getObject()) &&
                (ta.getIndex() == 108 || ta.getIndex() == 109);
    }

    boolean isWateringHole(TilesAddon ta) {
        return IcnKind.OBJNCRCK == Mp2.GetICNObject(ta.getObject()) &&
                ta.getIndex() >= 217 && ta.getIndex() <= 220;
    }

    boolean isJail(TilesAddon ta) {
        return IcnKind.X_LOC2 == Mp2.GetICNObject(ta.getObject()) && 0x09 == ta.getIndex();
    }

    boolean isEvent(TilesAddon ta) {
        // OBJNMUL2
        return IcnKind.OBJNMUL2 == Mp2.GetICNObject(ta.getObject()) && 0xA3 == ta.getIndex();
    }

    boolean isMine(TilesAddon ta) {
        // EXTRAOVR
        return IcnKind.EXTRAOVR == Mp2.GetICNObject(ta.getObject());
    }

    boolean isBoat(TilesAddon ta) {
        // OBJNWAT2
        return IcnKind.OBJNWAT2 == Mp2.GetICNObject(ta.getObject()) && 0x17 == ta.getIndex();
    }

    boolean isMiniHero(TilesAddon ta) {
        // MINIHERO
        return IcnKind.MINIHERO == Mp2.GetICNObject(ta.getObject());
    }

    boolean isCastle(TilesAddon ta) {
        // OBJNTOWN
        return IcnKind.OBJNTOWN == Mp2.GetICNObject(ta.getObject());
    }

    boolean isRandomCastle(TilesAddon ta) {
        // OBJNTWRD
        return IcnKind.OBJNTWRD == Mp2.GetICNObject(ta.getObject()) && 32 > ta.getIndex();
    }

    boolean isRandomMonster(TilesAddon ta) {
        // MONS32
        return IcnKind.MONS32 == Mp2.GetICNObject(ta.getObject()) &&
                0x41 < ta.getIndex() && 0x47 > ta.getIndex();
    }

    boolean isBarrier(TilesAddon ta) {
        return IcnKind.X_LOC3 == Mp2.GetICNObject(ta.getObject()) &&
                60 <= ta.getIndex() && 102 >= ta.getIndex() && 0 == ta.getIndex() % 6;
    }

    TilesAddon find_if_reverse(List<TilesAddon> addons, Predicate<TilesAddon> isFound) {
        for (var i = addons.size() - 1; i >= 0; i--) {
            var item = addons.get(i);
            if (isFound.test(item))
                return item;
        }
        return null;
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


    public void RedrawBottom(boolean skip_objs, ArrayList<Sprite> toPaint) {

        toPaint.clear();
        for (var it : addons_level1._items) {
            // skip
            if (skip_objs &&
                    Mp2.isRemoveObject(GetObject()) &&
                    FindObjectConst(GetObject()) == it)
                continue;

            var object = it.getObject();
            var index = it.getIndex();
            var icn = Mp2.GetICNObject(object);

            if (IcnKind.UNKNOWN == icn || IcnKind.MINIHERO == icn || IcnKind.MONS32 == icn)
                continue;
            var sprite = Agg.GetICN(icn, index);
            toPaint.add(sprite);


            // possible anime
            var anime_index = IcnKind.AnimationFrame(icn, index, Game.MapsAnimationFrame(), quantity2 != 0);
            if (anime_index == 0)
                continue;
            var anime_sprite = Agg.GetICN(icn, anime_index);
            toPaint.add(anime_sprite);
        }

    }

    public void RedrawObjects(ArrayList<Sprite> toPaint) {
        var obj = GetObject();
        switch (obj) {
            // boat
            case Mp2Kind.OBJ_BOAT:
                RedrawBoat(toPaint);
                break;
            // monster
            case Mp2Kind.OBJ_MONSTER:
                RedrawMonster(toPaint);

                //
            default:
                break;
        }
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

    private void RedrawMonster(ArrayList<Sprite> toPaint) {
        toPaint.clear();
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
                    (DIRECTION_BOTTOM_ROW & Direction.Get(GetIndex(), it)) != 0
                            && Mp2.isGroundObject(tile.GetObject(false))
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
            toPaint.add(sprite_first);

        } else {

            // draw first sprite
            int firstSpriteIndex = sprite_index * 9;
            var sprite_first = Agg.GetICN(IcnKind.MINIMON, firstSpriteIndex).clone();
            sprite_first.pos.add(16, 16);
            /*area.BlitOnTile(dst, sprite_first, sprite_first.x() + 16, TILEWIDTH + sprite_first.y(), mp);*/

            // draw second sprite

            int secondSpriteIndex = sprite_index * 9 + 1 +
                    monster_animation_cicle[Game.MapsAnimationFrame() % monster_animation_cicle.length];
            var sprite_next = Agg.GetICN(IcnKind.MINIMON, secondSpriteIndex).clone();
            sprite_next.pos.add(16, 16);
            toPaint.add(sprite_first);
            toPaint.add(sprite_next);
        }
    }

    public boolean isWater() {
        return 30 > TileSpriteIndex();
    }

    private void ScanAroundObject(int center, int obj, MapsIndexes resultsScan) {
        Maps.GetAroundIndexes(center, resultsScan);
        Maps.MapsIndexesFilteredObject(resultsScan, obj);
    }

    private void RedrawBoat(ArrayList<Sprite> dst) {

    }

    public void RedrawTop(Painter dst, ArrayList<Sprite> spritesToPaint, TilesAddon skip) {
        spritesToPaint.clear();
        var tileObject = GetObject();
        // animate objects
        if (Mp2Kind.OBJ_ABANDONEDMINE == tileObject) {
            var anime_sprite = Agg.GetICN(IcnKind.OBJNHAUN, Game.MapsAnimationFrame() % 15);
            spritesToPaint.add(anime_sprite);
        } else if (Mp2Kind.OBJ_MINES == GetObject()) {
            var addon = FindObjectConst(Mp2Kind.OBJ_MINES);
            if (addon != null && addon.tmp == SpellKind.HAUNT) {
                var anime_sprite = Agg.GetICN(IcnKind.OBJNHAUN, Game.MapsAnimationFrame() % 15);
                spritesToPaint.add(anime_sprite);
            } else if (addon != null && addon.tmp >= SpellKind.SETEGUARDIAN && addon.tmp <= SpellKind.SETWGUARDIAN) {
                spritesToPaint.add(Agg.GetICN(IcnKind.MONS32, new Monster(new Spell(addon.tmp)).GetSpriteIndex()));
            }
        }

        for (var it : addons_level2._items) {
            if (skip != null && skip == it) continue;

            var object = it.object;
            var index = it.index;
            var icn = Mp2.GetICNObject(object);

            if (IcnKind.UNKNOWN != icn && IcnKind.MINIHERO != icn && IcnKind.MONS32 != icn) {
                Sprite sprite = null;
                try {
                    sprite = Agg.GetICN(icn, index);
                    spritesToPaint.add(sprite);
                } catch (Exception ex) {
                    writeTodo("out of bounds" + ex);
                    continue;
                }

                var anime_index = IcnKind.AnimationFrame(icn, index, Game.MapsAnimationFrame(), false);
                // possible anime
                if (anime_index != 0) {
                    var anime_sprite = Agg.GetICN(icn, anime_index);
                    spritesToPaint.add(anime_sprite);
                }
            }
        }
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
                ", mp2_object=" + (mp2_object) +
                ", quantity1=" + (quantity1) +
                ", quantity2=" + (quantity2) +
                ", quantity3=" + (quantity3) +
                '}';
    }

    void QuantitySetSkill(int skill) {
        if (GetObject(false) == Mp2Kind.OBJ_WITCHSHUT) {
            quantity1 = (short) skill;
        }
    }

    private void AddonsPushLevel2(TilesAddon ta) {
        if (TilesAddon.ForceLevel1(ta))
            addons_level1._items.add(ta);
        else
            addons_level2._items.add(ta);
    }

    private void UpdateRNDResourceSprite(Tiles tile) {
        var addon = tile.FindObject(Mp2Kind.OBJ_RNDRESOURCE);

        if (addon == null) return;
        var resourceId = Resource.Rand();
        addon.setIndex(Resource.GetIndexSprite(resourceId));
        tile.SetObject(Mp2Kind.OBJ_RESOURCE);

        var world = World.Instance;
        H2Size wSize = new H2Size(world.w, world.h);
        // replace shadow artifact
        if (!isValidDirection(tile.GetIndex(), Direction.LEFT, wSize))
            return;
        var left_tile = world.GetTiles(GetDirectionIndex(tile.GetIndex(), Direction.LEFT));
        var shadow = left_tile.FindAddonLevel1(addon.uniq);

        if (shadow != null) {
            shadow.setIndex((byte) (addon.getIndex() - 1));
        }
    }

    public void AddonsPushLevel2(Mp2Addon ma) {
        if (ma.objectNameN2 != 0 && (ma.indexNameN2) < 0xFF) {
            AddonsPushLevel2(new TilesAddon(ma.quantityN, ma.uniqNumberN2, ma.objectNameN2, ma.indexNameN2));
        }

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

    private void UpdateRNDArtifactSprite(Tiles tile) {
        var world = World.Instance;
        //TODO
        TilesAddon addon = null;
        var index = 0;
        Artifact art = new Artifact();

        switch (tile.GetObject()) {
            case Mp2Kind.OBJ_RNDARTIFACT:
                addon = tile.FindObject(Mp2Kind.OBJ_RNDARTIFACT);
                art.SetId(Artifact.Rand(ArtifactLevel.ART_LEVEL123));
                index = art.IndexSprite();
                break;
            case Mp2Kind.OBJ_RNDARTIFACT1:
                addon = tile.FindObject(Mp2Kind.OBJ_RNDARTIFACT1);
                art.SetId(Artifact.Rand(ArtifactLevel.ART_LEVEL1));
                index = art.IndexSprite();
                break;
            case Mp2Kind.OBJ_RNDARTIFACT2:
                addon = tile.FindObject(Mp2Kind.OBJ_RNDARTIFACT2);
                art.SetId(Artifact.Rand(ArtifactLevel.ART_LEVEL2));
                index = art.IndexSprite();
                break;
            case Mp2Kind.OBJ_RNDARTIFACT3:
                addon = tile.FindObject(Mp2Kind.OBJ_RNDARTIFACT3);
                art.SetId(Artifact.Rand(ArtifactLevel.ART_LEVEL3));
                index = art.IndexSprite();
                break;
            default:
                return;
        }

        if (!art.IsValid()) {
            return;
        }
        if (addon == null)
            return;
        addon.setIndex(index);
        tile.SetObject(Mp2Kind.OBJ_ARTIFACT);

        H2Size wSize = new H2Size(world.w, world.h);
        // replace shadow artifact
        if (!isValidDirection(tile.GetIndex(), Direction.LEFT, wSize))
            return;
        var left_tile = world.GetTiles(GetDirectionIndex(tile.GetIndex(), Direction.LEFT));
        TilesAddon shadow = left_tile.FindAddonLevel1(addon.uniq);

        if (shadow != null) shadow.setIndex((byte) (index - 1));
    }

    private TilesAddon FindAddonLevel1(int uniq) {
        return find_if(addons_level1._items, it -> it.isUniq(uniq));
    }

    private TilesAddon FindAddonLevel2(int uniq) {
        return find_if(addons_level2._items, it -> it.isUniq(uniq));
    }

    private void UpdateMonsterInfo(Tiles tile) {
        int mons = -1;

        if (Mp2Kind.OBJ_MONSTER == tile.GetObject()) {
            TilesAddon addon = tile.FindObject(Mp2Kind.OBJ_MONSTER);

            if (addon != null)
                mons = new Monster(addon.getIndex() + 1).id; // ICN.MONS32 start from PEASANT
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
                addon.setIndex((byte) (mons - 1)); // ICN.MONS32 start from PEASANT
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
                    world.GetWeekType().GetType() == WeekKind.MONSTERS &&
                            world.GetWeekType().GetMonster() == mons)
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
        } else if (addon.getIndex() != mons - 1) {
            // fixed sprite
            addon.setIndex(mons - 1); // ICN.MONS32 start from PEASANT
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
                    int art = Artifact.FromMP2IndexSprite(addon.getIndex()).GetID();

                    if (ArtifactKind.UNKNOWN != art) {
                        if (art == ArtifactKind.SPELL_SCROLL) {
                            QuantitySetVariant(15);
                            // spell from origin mp2
                            QuantitySetSpell(1 + ((quantity2) * 256 + (quantity1)) / 8);
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
                    int res = Resource.FromIndexSprite(addon.getIndex());
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
                    switch (addon.getIndex()) {
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
                QuantityUpdate();
                break;

            case Mp2Kind.OBJ_RNDRESOURCE:
                // modify rnd resource sprite
                UpdateRNDResourceSprite(this);
                QuantityUpdate();
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

    public int MonsterCount() {
        return (quantity1) << 8 | (quantity2);
    }

    public void CaptureFlags32(int obj, int col) {
        var index = 0;
        var world = World.Instance;

        switch (col) {
            case H2Color.BLUE:
                index = 0;
                break;
            case H2Color.GREEN:
                index = 1;
                break;
            case H2Color.RED:
                index = 2;
                break;
            case H2Color.YELLOW:
                index = 3;
                break;
            case H2Color.ORANGE:
                index = 4;
                break;
            case H2Color.PURPLE:
                index = 5;
                break;
            default:
                index = 6;
                break;
        }

        switch (obj) {
            case Mp2Kind.OBJ_WINDMILL:
            case Mp2Kind.OBJ_MAGICGARDEN:
                //case Mp2Kind.OBJ_DRAGONCITY:	index += 35; CorrectFlags32(index); break; unused
            case Mp2Kind.OBJ_LIGHTHOUSE:
                index += 42;
                CorrectFlags32(index, false);
                break;
            case Mp2Kind.OBJ_WATERWHEEL:
                index += 14;
                CorrectFlags32(index, false);
                break;

            case Mp2Kind.OBJ_MINES:
                index += 14;
                CorrectFlags32(index, true);
                break;

            case Mp2Kind.OBJ_ALCHEMYLAB: {
                H2Size wSize = new H2Size(world.w, world.h);
                index += 21;
                if (isValidDirection(GetIndex(), Direction.TOP, wSize)) {
                    var tile = world.GetTiles(GetDirectionIndex(GetIndex(), Direction.TOP));
                    tile.CorrectFlags32(index, true);
                }
            }
            break;

            case Mp2Kind.OBJ_SAWMILL: {
                index += 28;
                H2Size wSize = new H2Size(world.w, world.h);
                if (isValidDirection(GetIndex(), Direction.TOP_RIGHT, wSize)) {
                    var tile = world.GetTiles(GetDirectionIndex(GetIndex(), Direction.TOP_RIGHT));
                    tile.CorrectFlags32(index, true);
                }
            }
            break;

            case Mp2Kind.OBJ_CASTLE: {
                index *= 2;

                H2Size wSize = new H2Size(world.w, world.h);
                if (isValidDirection(GetIndex(), Direction.LEFT, wSize)) {
                    var tile = world.GetTiles(GetDirectionIndex(GetIndex(), Direction.LEFT));
                    tile.CorrectFlags32(index, true);
                }

                index += 1;
                if (isValidDirection(GetIndex(), Direction.RIGHT, wSize)) {
                    var tile = world.GetTiles(GetDirectionIndex(GetIndex(), Direction.RIGHT));
                    tile.CorrectFlags32(index, true);
                }
            }
            break;

            default:
                break;
        }
    }

    private boolean isValidDirection(int from, int vector, H2Size world) {
        return switch (vector) {
            case Direction.TOP -> from >= world.w;
            case Direction.RIGHT -> from % world.w < world.w - 1;
            case Direction.BOTTOM -> from < world.w * (world.h - 1);
            case Direction.LEFT -> from % world.w != 0;
            case Direction.TOP_RIGHT -> from >= world.w && from % world.w < world.w - 1;
            case Direction.BOTTOM_RIGHT -> from < world.w * (world.h - 1) && from % world.w < world.w - 1;
            case Direction.BOTTOM_LEFT -> from < world.w * (world.h - 1) && from % world.w != 0;
            case Direction.TOP_LEFT -> from >= world.w && from % world.w != 0;
            default -> false;
        };
    }

    private void CorrectFlags32(int index, boolean up) {
        TilesAddon taddon = FindFlags();

        var world = World.Instance;
        // replace flag
        if (taddon != null)
            taddon.setIndex(index);
        else if (up)
            // or new flag
            addons_level2._items.add(new TilesAddon(TilesAddonLevel.UPPER, world.GetUniq(), 0x38, index));
        else
            // or new flag
            addons_level1._items.add(new TilesAddon(TilesAddonLevel.UPPER, world.GetUniq(), 0x38, index));
    }


    boolean isFlag32(TilesAddon ta) {
        return IcnKind.FLAG32 == Mp2.GetICNObject(ta.getObject());
    }

    private TilesAddon FindFlags() {
        var it = find_if(addons_level1._items, this::isFlag32);
        if (it == null) {
            it = find_if(addons_level2._items, this::isFlag32);
        }
        return it;
    }

    public TilesAddon FindAddonICN1(int icn) {
        return find_if(addons_level1._items, ta -> ta.isICN(icn));
    }

    public TilesAddon FindAddonICN2(int icn) {
        return find_if(addons_level2._items, ta -> ta.isICN(icn));
    }

    public void UpdatePassable() {

        tile_passable = Direction.DIRECTION_ALL;

        var obj = GetObject(false);
        var emptyobj = Mp2Kind.OBJ_ZERO == obj || Mp2Kind.OBJ_COAST == obj || Mp2Kind.OBJ_EVENT == obj;

        if (Mp2.isActionObject(obj, isWater())) {
            tile_passable = (short) Mp2.GetObjectDirect(obj);
            return;
        }
        var world = World.Instance;

        var wSize = new H2Size(world.w, world.h);
        // on ground
        if (Mp2Kind.OBJ_HEROES != (mp2_object) && !isWater()) {
            var mounts1 = null != find_if(addons_level1._items, Tiles::isMountsRocs);
            var mounts2 = null != find_if(addons_level2._items, Tiles::isMountsRocs);
            var trees1 = null != find_if(addons_level1._items, Tiles::isForestsTrees);
            var trees2 = null != find_if(addons_level2._items, Tiles::isForestsTrees);
/*
//TODO
            // fix coast passable
            if (tile_passable &&
                    //! MP2.isActionObject(obj, false) &&
                    !emptyobj &&
                    TileIsCoast(GetIndex(), Direction.TOP | Direction.BOTTOM | Direction.LEFT | Direction.RIGHT) &&
                    addons_level1._items.size() != (count_if(
                            addons_level1._items.begin(), addons_level1._items.end(),
                            []( TilesAddon& it)
            {
                return TilesAddon.isShadow(it);
            })))
            {
                tile_passable = 0;
            }
*/
            // fix mountain layer
            if (tile_passable != 0 &&
                    (Mp2Kind.OBJ_MOUNTS == obj || Mp2Kind.OBJ_TREES == obj) &&
                    mounts1 && (mounts2 || trees2)) {
                tile_passable = 0;
            }

            // fix trees layer
            if (tile_passable != 0 &&
                    (Mp2Kind.OBJ_MOUNTS == obj || Mp2Kind.OBJ_TREES == obj) &&
                    trees1 && (mounts2 || trees2)) {
                tile_passable = 0;
            }

            // town twba
            if (tile_passable != 0 && null != FindAddonICN1(IcnKind.OBJNTWBA) && (mounts2 || trees2)) {
                tile_passable = 0;
            }

            if (isValidDirection(GetIndex(), Direction.TOP, wSize)) {
                var top = world.GetTiles(GetDirectionIndex(GetIndex(), Direction.TOP));
                // fix: rocs on water
                if (top.isWater() &&
                        top.tile_passable != 0 &&
                        (Direction.TOP & top.tile_passable) == 0) {
                    top.tile_passable = 0;
                }
            }
        }

        // fix bottom border: disable passable for all no action objects

        if (tile_passable != 0 &&
                !isValidDirection(GetIndex(), Direction.BOTTOM, wSize) &&
                !emptyobj &&
                !Mp2.isActionObject(obj, isWater())) {
            tile_passable = 0;
        }
        // check all sprite (level 1)
        for (var it : addons_level1._items) {
            if (tile_passable != 0) {
                tile_passable &= TilesAddon.GetPassable(it);
            }
        }

        // fix top passable
        if (isValidDirection(GetIndex(), Direction.TOP, wSize)) {
            var top = world.GetTiles(GetDirectionIndex(GetIndex(), Direction.TOP));
/*
//TODO

            if (isWater() == top.isWater() &&
                    top.addons_level1._items.end() !=
                            find_if(top.addons_level1._items.begin(), top.addons_level1._items.end(), TopObjectDisable) &&
                    !MP2.isActionObject(top.GetObject(false), isWater()) &&
            (tile_passable && !(tile_passable & DIRECTION_TOP_ROW)) &&
                    !(top.tile_passable & DIRECTION_TOP_ROW))
            {
                top.tile_passable = 0;
            }
 */
        }

        // fix corners
        if (isValidDirection(GetIndex(), Direction.LEFT, wSize)) {
            var left = world.GetTiles(GetDirectionIndex(GetIndex(), Direction.LEFT));

            // left corner
            if (left.tile_passable != 0 &&
                    isLongObject(Direction.TOP) &&
                    ((Direction.TOP | Direction.TOP_LEFT) & tile_passable) != 0 &&
                    (Direction.TOP_RIGHT & left.tile_passable) != 0) {
                left.tile_passable &= ~Direction.TOP_RIGHT;
            } else
                // right corner
                if (tile_passable != 0 &&
                        left.isLongObject(Direction.TOP) &&
                        ((Direction.TOP | Direction.TOP_RIGHT) & left.tile_passable) != 0 &&
                        (Direction.TOP_LEFT & tile_passable) != 0) {
                    tile_passable &= ~Direction.TOP_LEFT;
                }

        }


    }

    public boolean GoodForUltimateArtifact() {
        //TODO
        return false;
    }

    public boolean isObject(int obj) {
        return obj == mp2_object;
    }

    public H2Point GetCenter() {
        return new H2Point(maps_x, maps_y);
    }

    public void Remove(int uniq) {
        if (addons_level1._items.size() != 0) {
            addons_level1.Remove(uniq);
        }
        if (addons_level2._items.size() != 0) {
            addons_level2.Remove(uniq);
        }
    }

    public void Refresh() {
        //works on copies, so a reference will not corrupt the other addon
        addons_level1.Refresh();
        addons_level2.Refresh();
    }

    public int GetObjectUID(int obj) {
        var addon = FindObjectConst(obj);
        return addon != null ? addon.uniq : 0;
    }

    public boolean MonsterJoinConditionSkip() {
        //TODO
        return false;
    }

    public boolean MonsterJoinConditionFree() {
        //TODO
        return false;
    }

    public boolean MonsterJoinConditionForce() {
        //TODO
        return false;
    }

    public int GetGround() {
        var index = TileSpriteIndex();

        // list grounds from GROUND32.TIL
        if (30 > index)
            return GroundKind.WATER;
        if (92 > index)
            return GroundKind.GRASS;
        if (146 > index)
            return GroundKind.SNOW;
        if (208 > index)
            return GroundKind.SWAMP;
        if (262 > index)
            return GroundKind.LAVA;
        if (321 > index)
            return GroundKind.DESERT;
        if (361 > index)
            return GroundKind.DIRT;
        if (415 > index)
            return GroundKind.WASTELAND;

        //else if(432 > pack_sprite_index)

        return GroundKind.BEACH;
    }

    public boolean isRoad(int direct) {

        for (var addon : addons_level1._items) {
            if (addon.isRoad(direct))
                return true;
        }
        return false;
    }

    public void SetHeroes(Heroes hero) {
        if (hero != null) {
            hero.SetMapsObject(mp2_object);
            SetQuantity3(hero.GetID() + 1);
            SetObject(Mp2Kind.OBJ_HEROES);
        } else {
            hero = GetHeroes();

            if (hero != null) {
                SetObject(hero.GetMapsObject());
                hero.SetMapsObject(Mp2Kind.OBJ_ZERO);
            } else
                SetObject(Mp2Kind.OBJ_ZERO);

            SetQuantity3(0);
        }
    }
}
