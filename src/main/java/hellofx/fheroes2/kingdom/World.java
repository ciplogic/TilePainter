package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.castle.AllCastles;
import hellofx.fheroes2.castle.Castle;
import hellofx.fheroes2.common.H2IntPair;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.game.GameConsts;
import hellofx.fheroes2.game.GameStatic;
import hellofx.fheroes2.heroes.AllHeroes;
import hellofx.fheroes2.heroes.HeroFlags;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.heroes.HeroesKind;
import hellofx.fheroes2.maps.*;
import hellofx.fheroes2.maps.objects.MapEvent;
import hellofx.fheroes2.maps.objects.MapSign;
import hellofx.fheroes2.maps.objects.MapSphinx;
import hellofx.fheroes2.maps.objects.Maps;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.serialize.FileUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static hellofx.common.Utilities.resizeList;
import static hellofx.fheroes2.game.GameConsts.*;
import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class World {
    public static World Instance;

    static {
        Instance = new World();
    }

    public short w;
    public AllCastles vec_castles = new AllCastles();
    public List<Tiles> vec_tiles = new ArrayList<>();
    public CapturedObjects map_captureobj = new CapturedObjects();
    public MapObjects map_objects = new MapObjects();
    public AllHeroes vec_heroes = new AllHeroes();
    public Kingdoms vec_kingdoms = new Kingdoms();
    public List<String> vec_rumors = new ArrayList<>();
    public List<EventsDate> vec_eventsday = new ArrayList<>();
    IntArrayList vec_object = new IntArrayList();
    private short h;

    public int GetIndexFromAbsPoint(int px, int py) {
        var res = py * w + px;

        return res;
    }

    public boolean loadMapMp2(String fileName) {
        if (!FileUtils.Exists(fileName))
            return false;
        Defaults();
        var vectorBytes = FileUtils.ReadAllBytes(fileName);
        var fs = new ByteVectorReader(vectorBytes);

        // check (mp2, mx2) ID
        if (fs.getBE32() != 0x5C000000)
            return false;

        var endof_mp2 = fs.size();
        fs.seek(endof_mp2 - 4);
        GameStatic.uniq = fs.getLE32();

        // offset data
        fs.seek(MP2OFFSETDATA - 2 * 4);

        // width
        var mapWidth = fs.getLE32();
        switch (mapWidth) {
            case MapSizeConsts.SMALL:
            case MapSizeConsts.MEDIUM:
            case MapSizeConsts.LARGE:
            case MapSizeConsts.XLARGE:
                this.w = (short) mapWidth;
                break;
            default:
                this.w = 0;
                break;
        }
        // width
        var mapHeight = fs.getLE32();
        switch (mapHeight) {
            case MapSizeConsts.SMALL:
            case MapSizeConsts.MEDIUM:
            case MapSizeConsts.LARGE:
            case MapSizeConsts.XLARGE:
                this.h = (short) mapWidth;
                break;
            default:
                this.h = 0;
                break;
        }
        if (w == 0 || h == 0 || w != h)
            return false;
        fs.skip(w * h * GameConsts.SIZEOFMP2TILE);
        var addonCount = fs.getLE32();
        var vec_mp2addons = new ArrayList<Mp2Addon>(addonCount);
        IntStream.range(0, addonCount).forEach(
                i -> {
                    var addon = new Mp2Addon();
                    addon.loadFromMp2Stream(fs);
                    vec_mp2addons.add(addon);
                }
        );
        var endof_addons = fs.tell();
        fs.seek(MP2OFFSETDATA);
        vec_tiles.clear();

        resizeList(vec_tiles, w * h, Tiles::new);

        IntStream.range(0, w * h)
                .forEach(index -> {
                    var mp2tile = new Mp2Tile();

                    // offset first addon
                    var offsetAddonsBlock = mp2tile.loadFromMp2Stream(fs);

                    switch (toByte(Mp2Tile.generalObject)) {
                        case H2Obj.OBJ_RNDTOWN:
                        case H2Obj.OBJ_RNDCASTLE:
                        case H2Obj.OBJ_CASTLE:
                        case H2Obj.OBJ_HEROES:
                        case H2Obj.OBJ_SIGN:
                        case H2Obj.OBJ_BOTTLE:
                        case H2Obj.OBJ_EVENT:
                        case H2Obj.OBJ_SPHINX:
                        case H2Obj.OBJ_JAIL:
                            vec_object.add(index);
                            break;
                        default:
                            break;
                    }

                    var tile = vec_tiles.get(index);

                    tile.Init(index, mp2tile);

                    // load all addon for current tils
                    while (offsetAddonsBlock != 0) {
                        if (vec_mp2addons.size() <= offsetAddonsBlock) {
                            break;
                        }
                        tile.AddonsPushLevel1(vec_mp2addons.get(offsetAddonsBlock));
                        tile.AddonsPushLevel2(vec_mp2addons.get(offsetAddonsBlock));
                        offsetAddonsBlock = vec_mp2addons.get(offsetAddonsBlock).indexAddon;
                    }

                    tile.AddonsSort();

                });

        // after addons
        fs.seek(endof_addons);

        // cood castles
        // 72 x 3 byte (cx, cy, id)
        for (int ii = 0; ii < 72; ++ii) {
            int cx = fs.get();
            int cy = fs.get();
            int id = fs.get();

            // empty block
            if (0xFF == cx && 0xFF == cy) continue;

            switch (id) {
                case 0x00: // tower: knight
                case 0x80: // castle: knight
                    vec_castles._items.add(new Castle(cx, cy, RaceKind.KNGT));
                    break;

                case 0x01: // tower: barbarian
                case 0x81: // castle: barbarian
                    vec_castles._items.add(new Castle(cx, cy, RaceKind.BARB));
                    break;

                case 0x02: // tower: sorceress
                case 0x82: // castle: sorceress
                    vec_castles._items.add(new Castle(cx, cy, RaceKind.SORC));
                    break;

                case 0x03: // tower: warlock
                case 0x83: // castle: warlock
                    vec_castles._items.add(new Castle(cx, cy, RaceKind.WRLK));
                    break;

                case 0x04: // tower: wizard
                case 0x84: // castle: wizard
                    vec_castles._items.add(new Castle(cx, cy, RaceKind.WZRD));
                    break;

                case 0x05: // tower: necromancer
                case 0x85: // castle: necromancer
                    vec_castles._items.add(new Castle(cx, cy, RaceKind.NECR));
                    break;

                case 0x06: // tower: random
                case 0x86: // castle: random
                    vec_castles._items.add(new Castle(cx, cy, RaceKind.NONE));
                    break;

                default:
                    break;
            }
            // preload in to capture objects cache
            map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), H2Obj.OBJ_CASTLE, H2Color.NONE);
        }

        fs.seek(endof_addons + 72 * 3);

        // cood resource kingdoms
        // 144 x 3 byte (cx, cy, id)
        for (int ii = 0; ii < 144; ++ii) {
            int cx = fs.get();
            int cy = fs.get();
            int id = fs.get();

            // empty block
            if (0xFF == cx && 0xFF == cy) continue;

            switch (id) {
                // mines: wood
                case 0x00:
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), H2Obj.OBJ_SAWMILL, H2Color.NONE);
                    break;
                // mines: mercury
                case 0x01:
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), H2Obj.OBJ_ALCHEMYLAB, H2Color.NONE);
                    break;
                // mines: ore
                case 0x02:
                    // mines: sulfur
                case 0x03:
                    // mines: crystal
                case 0x04:
                    // mines: gems
                case 0x05:
                    // mines: gold
                case 0x06:
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), H2Obj.OBJ_MINES, H2Color.NONE);
                    break;
                // lighthouse
                case 0x64:
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), H2Obj.OBJ_LIGHTHOUSE, H2Color.NONE);
                    break;
                // dragon city
                case 0x65:
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), H2Obj.OBJ_DRAGONCITY, H2Color.NONE);
                    break;
                // abandoned mines
                case 0x67:
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), H2Obj.OBJ_ABANDONEDMINE, H2Color.NONE);
                    break;
                default:
                    break;
            }
        }

        fs.seek(endof_addons + 72 * 3 + 144 * 3);

        // byte: num obelisks (01 default)
        fs.skip(1);

        // count final mp2 blocks
        int countblock = 0;
        while (true) {
            int l = fs.get();
            int h = fs.get();

            //VERBOSE("dump block: 0x" << std::setw(2) << std::setfill('0') << std::hex << l <<
            //	std::setw(2) << std::setfill('0') << std::hex << h);

            if (0 == h && 0 == l) break;
            countblock = 256 * h + l - 1;
        }

        // castle or heroes or (events, rumors, etc)
        for (int ii = 0; ii < countblock; ++ii) {
            int findobject = -1;

            // read block
            var sizeblock = fs.getLE16();
            var pblock = fs.getRaw(sizeblock);

            for (var it_index = 0; it_index != vec_object.size() && findobject < 0; ++it_index) {
                var tile = vec_tiles.get(vec_object.getInt(it_index));

                // orders(quantity2, quantity1)
                var orders = tile.GetQuantity2();
                orders <<= 8;
                orders |= tile.GetQuantity1();

                if ((orders != 0) && ((orders % 0x08) == 0) && (ii + 1 == orders / 0x08))
                    findobject = vec_object.getInt(it_index);
            }

            if (0 <= findobject) {
                var tile = vec_tiles.get(findobject);
                TilesAddon addon;

                switch (tile.GetObject()) {
                    case H2Obj.OBJ_CASTLE:
                        // add castle
                        if (SIZEOFMP2CASTLE != pblock.length) {
                        } else {
                            var castle = GetCastle(Maps.GetPoint(findobject));
                            if (castle != null) {
                                var bvr = new ByteVectorReader(pblock);
                                castle.LoadFromMP2(bvr);
                                Maps.MinimizeAreaForCastle(castle.GetCenter());
                                map_captureobj.SetColor(tile.GetIndex(), castle.GetColor());
                            }
                        }
                        break;
                    case H2Obj.OBJ_RNDTOWN:
                    case H2Obj.OBJ_RNDCASTLE:
                        // add rnd castle
                        if (SIZEOFMP2CASTLE != pblock.length) {
                        } else {
                            var castle = GetCastle(Maps.GetPoint(findobject));
                            if (castle != null) {
                                var bvr = new ByteVectorReader(pblock);
                                castle.LoadFromMP2(bvr);
                                Maps.UpdateRNDSpriteForCastle(castle.GetCenter(), castle.GetRace(), castle.isCastle());
                                Maps.MinimizeAreaForCastle(castle.GetCenter());
                                map_captureobj.SetColor(tile.GetIndex(), castle.GetColor());
                            } else {
                            }
                        }
                        break;
                    case H2Obj.OBJ_JAIL:
                        // add jail
                        if (SIZEOFMP2HEROES != pblock.length) {
                        } else {
                            int race = RaceKind.KNGT;
                            switch (pblock[0x3c]) {
                                case 1:
                                    race = RaceKind.BARB;
                                    break;
                                case 2:
                                    race = RaceKind.SORC;
                                    break;
                                case 3:
                                    race = RaceKind.WRLK;
                                    break;
                                case 4:
                                    race = RaceKind.WZRD;
                                    break;
                                case 5:
                                    race = RaceKind.NECR;
                                    break;
                                default:
                                    break;
                            }

                            var hero = GetFreemanHeroes(race);

                            if (hero != null) {
                                var bvr = new ByteVectorReader(pblock);
                                hero.LoadFromMP2(findobject, H2Color.NONE, hero.GetRace(), bvr);
                                hero.bitModes.SetModes(HeroFlags.JAIL);
                            }
                        }
                        break;
                    case H2Obj.OBJ_HEROES:
                        // add heroes
                        if (SIZEOFMP2HEROES != pblock.length) {
                        } else if (null != (addon = tile.FindObjectConst(H2Obj.OBJ_HEROES))) {
                            H2IntPair colorRace = TilesAddon.ColorRaceFromHeroSprite(addon);
                            Kingdom kingdom = GetKingdom(colorRace.first);

                            if (colorRace.second == RaceKind.RAND &&
                                    colorRace.first != H2Color.NONE)
                                colorRace.second = kingdom.GetRace();

                            // check heroes max count
                            if (kingdom.AllowRecruitHero(false, 0)) {
                                Heroes hero = null;

                                if (pblock[17] != 0 && pblock[18] < HeroesKind.BAX)
                                    hero = vec_heroes.Get(pblock[18]);

                                if (hero != null || !hero.isFreeman())
                                    hero = vec_heroes.GetFreeman(colorRace.second);

                                if (hero != null) {
                                    var bvr = new ByteVectorReader(pblock);
                                    hero.LoadFromMP2(findobject, colorRace.first, colorRace.second, bvr);
                                }
                            }
                        }
                        break;
                    case H2Obj.OBJ_SIGN:
                    case H2Obj.OBJ_BOTTLE:
                        // add sign or bottle
                        if (SIZEOFMP2SIGN - 1 < pblock.length && 0x01 == pblock[0]) {
                            var obj = new MapSign();
                            var bvr = new ByteVectorReader(pblock);
                            obj.LoadFromMP2(findobject, bvr);
                            map_objects.add(obj);
                        }
                        break;
                    case H2Obj.OBJ_EVENT:
                        // add event maps
                        if (SIZEOFMP2EVENT - 1 < pblock.length && 0x01 == pblock[0]) {
                            var obj = new MapEvent();
                            var bvr = new ByteVectorReader(pblock);
                            obj.LoadFromMP2(findobject, bvr);
                            map_objects.add(obj);
                        }
                        break;
                    case H2Obj.OBJ_SPHINX:
                        // add riddle sphinx
                        if (SIZEOFMP2RIDDLE - 1 < pblock.length && 0x00 == pblock[0]) {
                            var obj = new MapSphinx();
                            var bvr = new ByteVectorReader(pblock);
                            obj.LoadFromMP2(findobject, bvr);
                            map_objects.add(obj);
                        }
                        break;
                    default:
                        break;
                }
            }
            // other events
            else if (0x00 == pblock[0]) {
                // add event day
                if (SIZEOFMP2EVENT - 1 < pblock.length && 1 == pblock[42]) {
                    var dayEvent = new EventsDate();

                    var bvr = new ByteVectorReader(pblock);
                    dayEvent.LoadFromMP2(bvr);
                    vec_eventsday.add(dayEvent);
                }
                // add rumors
                else if (SIZEOFMP2RUMOR - 1 < pblock.length) {
                    if (pblock[8] != 0) {
                        byte[] subBlock = Arrays.copyOfRange(pblock, 8, pblock.length);
                        ByteVectorReader bvr = new ByteVectorReader(subBlock);
                        String valueRumor = bvr.toString(subBlock.length);
                        vec_rumors.add(valueRumor);
                    }
                }
            }
            // debug
            else {
            }
        }
        return true;
    }

    private void Defaults() {
        // playing kingdom
        vec_kingdoms.Init();

        // initialize all heroes
        vec_heroes.Init();

        vec_castles.Init();
    }

    private Kingdom GetKingdom(int color) {

        return vec_kingdoms.GetKingdom(color);
    }


    private Heroes GetFreemanHeroes(int race) {
        return vec_heroes.GetFreeman(race);
    }

    private Castle GetCastle(H2Point center) {
        return vec_castles.Get(center);
    }
}
