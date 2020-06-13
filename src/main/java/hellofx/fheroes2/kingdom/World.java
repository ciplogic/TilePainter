package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.castle.AllCastles;
import hellofx.fheroes2.castle.Castle;
import hellofx.fheroes2.common.H2IntPair;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.Rand;
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
import hellofx.fheroes2.resource.UltimateArtifact;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.serialize.FileUtils;
import hellofx.fheroes2.system.Players;
import hellofx.fheroes2.system.Settings;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static hellofx.common.Utilities.find_if;
import static hellofx.fheroes2.game.GameConsts.*;
import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;
import static hellofx.fheroes2.system.Translate.StringReplace;
import static hellofx.fheroes2.system.Translate.tr;

public class World {
    public static World Instance;

    static {
        Instance = new World();
    }

    public int w, h;

    private World() {
    }

    public AllCastles vec_castles = new AllCastles();
    public Tiles[] vec_tiles;
    public CapturedObjects map_captureobj = new CapturedObjects();
    public AllHeroes vec_heroes = new AllHeroes();
    public Kingdoms vec_kingdoms = new Kingdoms();
    public List<String> vec_rumors = new ArrayList<>();
    public List<EventsDate> vec_eventsday = new ArrayList<>();
    IntArrayList vec_object = new IntArrayList();
    UltimateArtifact ultimate_artifact = new UltimateArtifact();


    int day;
    int week;
    int month;

    Week week_current = new Week();
    Week week_next = new Week();

    int heroes_cond_wins;
    int heroes_cond_loss;

    MapActions map_actions = new MapActions();
    MapObjects map_objects = new MapObjects();

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

        vec_tiles = new Tiles[w * h];
        IntStream.range(0, w * h).forEach(i -> {
            vec_tiles[i] = new Tiles();
        });

        IntStream.range(0, w * h)
                .forEach(index -> {
                    var mp2tile = new Mp2Tile();

                    // offset first addon
                    var offsetAddonsBlock = mp2tile.loadFromMp2Stream(fs);

                    switch (toByte(mp2tile.generalObject)) {
                        case Mp2Kind.OBJ_RNDTOWN:
                        case Mp2Kind.OBJ_RNDCASTLE:
                        case Mp2Kind.OBJ_CASTLE:
                        case Mp2Kind.OBJ_HEROES:
                        case Mp2Kind.OBJ_SIGN:
                        case Mp2Kind.OBJ_BOTTLE:
                        case Mp2Kind.OBJ_EVENT:
                        case Mp2Kind.OBJ_SPHINX:
                        case Mp2Kind.OBJ_JAIL:
                            vec_object.add(index);
                            break;
                        default:
                            break;
                    }

                    var tile = vec_tiles[index];

                    if (index == 1311) {
                        //here
                        int m = 5;
                    }

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
            map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), Mp2Kind.OBJ_CASTLE, H2Color.NONE);
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
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), Mp2Kind.OBJ_SAWMILL, H2Color.NONE);
                    break;
                // mines: mercury
                case 0x01:
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), Mp2Kind.OBJ_ALCHEMYLAB, H2Color.NONE);
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
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), Mp2Kind.OBJ_MINES, H2Color.NONE);
                    break;
                // lighthouse
                case 0x64:
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), Mp2Kind.OBJ_LIGHTHOUSE, H2Color.NONE);
                    break;
                // dragon city
                case 0x65:
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), Mp2Kind.OBJ_DRAGONCITY, H2Color.NONE);
                    break;
                // abandoned mines
                case 0x67:
                    map_captureobj.Set(GetIndexFromAbsPoint(cx, cy), Mp2Kind.OBJ_ABANDONEDMINE, H2Color.NONE);
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
                var tile = vec_tiles[vec_object.getInt(it_index)];

                // orders(quantity2, quantity1)
                var orders = tile.GetQuantity2();
                orders <<= 8;
                orders |= tile.GetQuantity1();

                if ((orders != 0) && ((orders % 0x08) == 0) && (ii + 1 == orders / 0x08))
                    findobject = vec_object.getInt(it_index);
            }

            if (0 <= findobject) {
                var tile = vec_tiles[findobject];
                TilesAddon addon;

                switch (tile.GetObject()) {
                    case Mp2Kind.OBJ_CASTLE: {
                        // add castle
                        if (SIZEOFMP2CASTLE != pblock.length) {
                            break;
                        }
                        var castle = GetCastle(Maps.GetPoint(findobject));
                        if (castle != null) {
                            var bvr = new ByteVectorReader(pblock);
                            castle.LoadFromMP2(bvr);
                            Maps.MinimizeAreaForCastle(castle.GetCenter());
                            map_captureobj.SetColor(tile.GetIndex(), castle.GetColor());
                        }
                    }
                    break;
                    case Mp2Kind.OBJ_RNDTOWN:
                    case Mp2Kind.OBJ_RNDCASTLE:
                        // add rnd castle
                        if (SIZEOFMP2CASTLE != pblock.length) {
                            break;
                        }
                        var castle = GetCastle(Maps.GetPoint(findobject));
                        if (castle != null) {
                            var bvr = new ByteVectorReader(pblock);
                            castle.LoadFromMP2(bvr);
                            Maps.UpdateRNDSpriteForCastle(castle.GetCenter(), castle.GetRace(), castle.isCastle());
                            Maps.MinimizeAreaForCastle(castle.GetCenter());
                            map_captureobj.SetColor(tile.GetIndex(), castle.GetColor());
                        }
                        break;
                    case Mp2Kind.OBJ_JAIL: {
                        // add jail
                        if (SIZEOFMP2HEROES != pblock.length) {
                            break;
                        }
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
                    case Mp2Kind.OBJ_HEROES:
                        // add heroes
                        if (SIZEOFMP2HEROES != pblock.length) {
                            break;
                        }
                        if (null != (addon = tile.FindObjectConst(Mp2Kind.OBJ_HEROES))) {
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

                                if (hero == null || !hero.isFreeman())
                                    hero = vec_heroes.GetFreeman(colorRace.second);

                                if (hero != null) {
                                    var bvr = new ByteVectorReader(pblock);
                                    hero.LoadFromMP2(findobject, colorRace.first, colorRace.second, bvr);
                                }
                            }
                        }
                        break;
                    case Mp2Kind.OBJ_SIGN:
                    case Mp2Kind.OBJ_BOTTLE:
                        // add sign or bottle
                        if (SIZEOFMP2SIGN - 1 < pblock.length && 0x01 == pblock[0]) {
                            var obj = new MapSign();
                            var bvr = new ByteVectorReader(pblock);
                            obj.LoadFromMP2(findobject, bvr);
                            map_objects.add(obj);
                        }
                        break;
                    case Mp2Kind.OBJ_EVENT:
                        // add event maps
                        if (SIZEOFMP2EVENT - 1 < pblock.length && 0x01 == pblock[0]) {
                            var obj = new MapEvent();
                            var bvr = new ByteVectorReader(pblock);
                            obj.LoadFromMP2(findobject, bvr);
                            map_objects.add(obj);
                        }
                        break;
                    case Mp2Kind.OBJ_SPHINX:
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
        PostLoad();
        return true;
    }

    void PostLoad() {
        // modify other objects
        for (var ii = 0; ii < vec_tiles.length; ++ii) {
            var tile = vec_tiles[ii];

            tile.FixedPreload();

            //
            switch (tile.GetObject()) {
                case Mp2Kind.OBJ_WITCHSHUT:
                case Mp2Kind.OBJ_SHRINE1:
                case Mp2Kind.OBJ_SHRINE2:
                case Mp2Kind.OBJ_SHRINE3:
                case Mp2Kind.OBJ_STONELIGHTS:
                case Mp2Kind.OBJ_FOUNTAIN:
                case Mp2Kind.OBJ_EVENT:
                case Mp2Kind.OBJ_BOAT:
                case Mp2Kind.OBJ_RNDARTIFACT:
                case Mp2Kind.OBJ_RNDARTIFACT1:
                case Mp2Kind.OBJ_RNDARTIFACT2:
                case Mp2Kind.OBJ_RNDARTIFACT3:
                case Mp2Kind.OBJ_RNDRESOURCE:
                case Mp2Kind.OBJ_WATERCHEST:
                case Mp2Kind.OBJ_TREASURECHEST:
                case Mp2Kind.OBJ_ARTIFACT:
                case Mp2Kind.OBJ_RESOURCE:
                case Mp2Kind.OBJ_MAGICGARDEN:
                case Mp2Kind.OBJ_WATERWHEEL:
                case Mp2Kind.OBJ_WINDMILL:
                case Mp2Kind.OBJ_WAGON:
                case Mp2Kind.OBJ_SKELETON:
                case Mp2Kind.OBJ_LEANTO:
                case Mp2Kind.OBJ_CAMPFIRE:
                case Mp2Kind.OBJ_FLOTSAM:
                case Mp2Kind.OBJ_SHIPWRECKSURVIROR:
                case Mp2Kind.OBJ_DERELICTSHIP:
                case Mp2Kind.OBJ_SHIPWRECK:
                case Mp2Kind.OBJ_GRAVEYARD:
                case Mp2Kind.OBJ_PYRAMID:
                case Mp2Kind.OBJ_DAEMONCAVE:
                case Mp2Kind.OBJ_ABANDONEDMINE:
                case Mp2Kind.OBJ_ALCHEMYLAB:
                case Mp2Kind.OBJ_SAWMILL:
                case Mp2Kind.OBJ_MINES:
                case Mp2Kind.OBJ_TREEKNOWLEDGE:
                case Mp2Kind.OBJ_BARRIER:
                case Mp2Kind.OBJ_TRAVELLERTENT:
                case Mp2Kind.OBJ_MONSTER:
                case Mp2Kind.OBJ_RNDMONSTER:
                case Mp2Kind.OBJ_RNDMONSTER1:
                case Mp2Kind.OBJ_RNDMONSTER2:
                case Mp2Kind.OBJ_RNDMONSTER3:
                case Mp2Kind.OBJ_RNDMONSTER4:
                case Mp2Kind.OBJ_ANCIENTLAMP:
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
                case Mp2Kind.OBJ_RUINS:
                case Mp2Kind.OBJ_TREECITY:
                case Mp2Kind.OBJ_WAGONCAMP:
                case Mp2Kind.OBJ_DESERTTENT:
                case Mp2Kind.OBJ_TROLLBRIDGE:
                case Mp2Kind.OBJ_DRAGONCITY:
                case Mp2Kind.OBJ_CITYDEAD:
                    tile.QuantityUpdate();
                    break;

                case Mp2Kind.OBJ_WATERALTAR:
                case Mp2Kind.OBJ_AIRALTAR:
                case Mp2Kind.OBJ_FIREALTAR:
                case Mp2Kind.OBJ_EARTHALTAR:
                case Mp2Kind.OBJ_BARROWMOUNDS:
                    tile.QuantityReset();
                    tile.QuantityUpdate();
                    break;

                case Mp2Kind.OBJ_HEROES: {
                    var addon = tile.FindAddonICN1(IcnKind.MINIHERO);
                    // remove event sprite
                    if (addon != null) tile.Remove(addon.uniq);

                    //TODO: fix heroes
                    // tile.SetHeroes(GetHeroes(Maps.GetPoint(ii)));
                }
                break;

                default:
                    break;
            }
        }

        var world = World.Instance;
        // add heroes to kingdoms
        vec_kingdoms.AddHeroes(vec_heroes);

        // add castles to kingdoms
        vec_kingdoms.AddCastles(vec_castles);
/*
        // update wins, loss conditions
        if (GameOver.WINS_HERO & Settings.Get().ConditionWins())
        {
            Heroes hero = GetHeroes(Settings.Get().WinsMapsPositionObject());
            heroes_cond_wins = hero!=null ? hero.GetID() : HeroesKind.UNKNOWN;
        }
        if (GameOver.LOSS_HERO & Settings.Get().ConditionLoss())
        {
            Heroes hero = GetHeroes(Settings.Get().LossMapsPositionObject());
            if (hero!=null)
            {
                heroes_cond_loss = hero.GetID();
                hero.bitModes.SetModes(Heroes.NOTDISMISS | Heroes.NOTDEFAULTS);
            }
        }
*/
        // update tile passable
        Arrays.stream(vec_tiles). forEach(tile -> {
            tile.UpdatePassable();
        });

        // play with hero
        vec_kingdoms.ApplyPlayWithStartingHero();

        if (Settings.Get().ExtWorldStartHeroLossCond4Humans())
        vec_kingdoms.AddCondLossHeroes(vec_heroes);

        // play with debug hero
        if (Settings.Get().IS_DEVEL())
        {
            // get first castle position
            var kingdom = GetKingdom(H2Color.GetFirst(Players.HumanColors()));

            if (kingdom.GetCastles()._items.size() != 0)
            {
            var castle = kingdom.GetCastles()._items.get(0);
                var hero = vec_heroes.Get(HeroesKind.SANDYSANDY);

                if (hero!=null)
                {
                var cp = castle.GetCenter();
                    hero.Recruit(castle.GetColor(), new H2Point(cp.x, cp.y + 1));
                }
            }
        }

        // set ultimate
        var it = find_if(vec_tiles, (tile) ->
        {
            return tile.isObject(Mp2Kind.OBJ_RNDULTIMATEARTIFACT);
        });

        H2Point ultimate_pos = new H2Point();

        // not found
        if (null == it) {
            // generate position for ultimate
            MapsIndexes pools = new MapsIndexes();

            for (var tile : vec_tiles) {
                var x = tile.maps_x;
                var y = tile.maps_y;
                if (tile.GoodForUltimateArtifact() &&
                        x > 5 && x < world.w - 5 && y > 5 && y < world.h - 5)
                    pools.values.add(tile.GetIndex());
            }

            if (pools.values.size() != 0) {
                int pos = Rand.Get(pools.values);
                //TODO
                //ultimate_artifact.Set(pos, Artifact.Rand(ArtifactLevel.ART_ULTIMATE));
                ultimate_pos = Maps.GetPoint(pos);
            }
        } else {
            TilesAddon addon = it.FindObject(Mp2Kind.OBJ_RNDULTIMATEARTIFACT);

            // remove ultimate artifact sprite
            if (addon != null) {
                //TODO
                //ultimate_artifact.Set(it.GetIndex(), Artifact.FromMP2IndexSprite(addon.index));
                it.Remove(addon.uniq);
                it.SetObject(Mp2Kind.OBJ_ZERO);
                ultimate_pos = it.GetCenter();
            }
        }

        String rumor = tr("The ultimate artifact is really the %{name}");
        rumor = StringReplace(rumor, "%{name}", ultimate_artifact.GetName());
        vec_rumors.add(rumor);

        rumor = tr("The ultimate artifact may be found in the %{name} regions of the world.");

        if (world.h / 3 > ultimate_pos.y) {
            if (world.w / 3 > ultimate_pos.x)
                rumor = StringReplace(rumor, "%{name}", tr("north-west"));
            else if (2 * world.w / 3 > ultimate_pos.x)
                rumor = StringReplace(rumor, "%{name}", tr("north"));
            else
                rumor = StringReplace(rumor, "%{name}", tr("north-east"));
        } else if (2 * world.h / 3 > ultimate_pos.y) {
            if (world.w / 3 > ultimate_pos.x)
                rumor = StringReplace(rumor, "%{name}", tr("west"));
            else if (2 * world.w / 3 > ultimate_pos.x)
                rumor = StringReplace(rumor, "%{name}", tr("center"));
            else
                rumor = StringReplace(rumor, "%{name}", tr("east"));
        } else {
            if (world.w / 3 > ultimate_pos.x)
                rumor = StringReplace(rumor, "%{name}", tr("south-west"));
            else if (2 * world.w / 3 > ultimate_pos.x)
                rumor = StringReplace(rumor, "%{name}", tr("south"));
            else
                rumor = StringReplace(rumor, "%{name}", tr("south-east"));
        }
        vec_rumors.add(rumor);

        vec_rumors.add(tr("The truth is out there."));
        vec_rumors.add(tr("The dark side is stronger."));
        vec_rumors.add(tr("The end of the world is near."));
        vec_rumors.add(tr("The bones of Lord Slayer are buried in the foundation of the arena."));
        vec_rumors.add(tr("A Black Dragon will take out a Titan any day of the week."));
        vec_rumors.add(tr("He told her: Yada yada yada...  and then she said: Blah, blah, blah..."));

        vec_rumors.add(
                tr("You can load the newest version of game from a site:\n http://sf.net/projects/fheroes2"));
        vec_rumors.add(tr("This game is now in beta development version. ;)"));
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

    public Tiles GetTiles(int x, int y) {
        var index = x + (y * w);
        if (x < 0 || y < 0 || x >= w || y >= h)
            return null;
        return vec_tiles[index];
    }

    public CapturedObject GetCapturedObject(int index) {
        return map_captureobj._items.get(index);
    }

    public Tiles GetTiles(int index) {
        return vec_tiles[index];
    }

    public void CaptureObject(int index, int color) {

        int obj = GetTiles(index).GetObject(false);
        map_captureobj.Set(index, obj, color);

        if (Mp2Kind.OBJ_CASTLE == obj) {
            Castle castle = GetCastle(Maps.GetPoint(index));
            if (castle != null && castle.GetColor() != color)
                castle.ChangeColor(color);
        }

        if ((color & (H2Color.ALL | H2Color.UNUSED)) != 0)
            GetTiles(index).CaptureFlags32(obj, color);
    }

    public int CountDay() {
        //TODO
        return 0;
    }

    public int CountWeek() {
        //TODO
        return 0;
    }

    public int GetUniq() {
        return ++GameStatic.uniq;
    }

    public Week GetWeekType() {
        return week_current;
    }
}
