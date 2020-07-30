package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.castle.AllCastles;
import hellofx.fheroes2.castle.Castle;
import hellofx.fheroes2.castle.CastleHeroes;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.game.GameOver;
import hellofx.fheroes2.game.GameStatic;
import hellofx.fheroes2.heroes.AllHeroes;
import hellofx.fheroes2.heroes.HeroFlags;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.heroes.HeroesKind;
import hellofx.fheroes2.maps.MapsIndexes;
import hellofx.fheroes2.maps.Mp2Kind;
import hellofx.fheroes2.maps.Tiles;
import hellofx.fheroes2.maps.TilesAddon;
import hellofx.fheroes2.maps.objects.MapObjectSimple;
import hellofx.fheroes2.maps.objects.Maps;
import hellofx.fheroes2.resource.Artifact;
import hellofx.fheroes2.resource.ArtifactLevel;
import hellofx.fheroes2.resource.UltimateArtifact;
import hellofx.fheroes2.system.Players;
import hellofx.fheroes2.system.Settings;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static hellofx.common.Utilities.find_if;
import static hellofx.fheroes2.game.GameConsts.DAYOFWEEK;
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


    public void Reset() {

        day = 0;
        week = 0;
        month = 0;

        week_current = new Week(WeekKind.TORTOISE);
        week_next = new Week(Week.WeekRand());

        heroes_cond_wins = HeroesKind.UNKNOWN;
        heroes_cond_loss = HeroesKind.UNKNOWN;
    }

    void PostLoad() {

        // modify other objects
        var ii = -1;
        for (Tiles tile : vec_tiles) {
            ++ii;
            tile.Refresh();

            tile.FixedPreload();

            //
            int tileObject = tile.GetObject();
            switch (tileObject) {
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
                    tile.SetHeroes(GetHeroes(Maps.GetPoint(ii)));
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

        // update wins, loss conditions
        if ((GameOver.WINS_HERO & Settings.Get().ConditionWins()) != 0) {
            Heroes hero = GetHeroes(Settings.Get().WinsMapsPositionObject());
            heroes_cond_wins = hero != null ? hero.GetID() : HeroesKind.UNKNOWN;
        }
        if ((GameOver.LOSS_HERO & Settings.Get().ConditionLoss()) != 0) {
            Heroes hero = GetHeroes(Settings.Get().LossMapsPositionObject());
            if (hero != null) {
                heroes_cond_loss = hero.GetID();
                hero.bitModes.SetModes(HeroFlags.NOTDISMISS | HeroFlags.NOTDEFAULTS);
            }
        }

        // update tile passable
        Arrays.stream(vec_tiles).forEach(tile -> {
            tile.UpdatePassable();
        });

        // play with hero
        vec_kingdoms.ApplyPlayWithStartingHero();

        if (Settings.Get().ExtWorldStartHeroLossCond4Humans())
            vec_kingdoms.AddCondLossHeroes(vec_heroes);

        // play with debug hero
        if (Settings.Get().IS_DEVEL()) {
            // get first castle position
            var kingdom = GetKingdom(H2Color.GetFirst(Players.HumanColors()));

            if (kingdom.GetCastles()._items.size() != 0) {
                var castle = kingdom.GetCastles()._items.get(0);
                var hero = vec_heroes.Get(HeroesKind.SANDYSANDY);

                if (hero != null) {
                    var cp = castle.GetCenter();
                    hero.Recruit(castle.GetColor(), new H2Point(cp.x, cp.y + 1));
                }
            }
        }

        // set ultimate
        var it = find_if(vec_tiles, tile ->
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
                ultimate_artifact.Set(pos, new Artifact(Artifact.Rand(ArtifactLevel.ART_ULTIMATE)));
                ultimate_pos = Maps.GetPoint(pos);
            }
        } else {
            TilesAddon addon = it.FindObject(Mp2Kind.OBJ_RNDULTIMATEARTIFACT);

            // remove ultimate artifact sprite
            if (addon != null) {
                //TODO
                ultimate_artifact.Set(it.GetIndex(), Artifact.FromMP2IndexSprite(addon.index));
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

    private Heroes GetHeroes(H2Point center) {
        return vec_heroes.Get(center);
    }

    public void Defaults() {
        // playing kingdom
        vec_kingdoms.Init();

        // initialize all heroes
        vec_heroes.Init();

        vec_castles.Init();
    }

    public Kingdom GetKingdom(int color) {
        return vec_kingdoms.GetKingdom(color);
    }


    public Castle GetCastle(H2Point center) {
        return vec_castles.Get(center);
    }

    public Tiles GetTiles(int x, int y) {
        var index = x + y * w;
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
        return day;
    }

    public int CountWeek() {
        return week;
    }

    public boolean BeginWeek() {
        return 1 == day % DAYOFWEEK;
    }

    public int GetUniq() {
        return ++GameStatic.uniq;
    }

    public Week GetWeekType() {
        return week_current;
    }

    public CastleHeroes GetHeroes(Castle castle) {
        //TODO
        return new CastleHeroes(vec_heroes.GetGuest(castle), vec_heroes.GetGuard(castle));
    }

    public Heroes GetHeroes(int id) {
        return vec_heroes.Get(id);
    }

    public Heroes GetFreemanHeroes(int race) {
        return vec_heroes.GetFreeman(race);
    }

    public Heroes GetFreemanHeroes() {
        return GetFreemanHeroes(0);
    }

    public MapObjectSimple GetMapObject(int uid) {
        return uid != 0 ? map_objects.get(uid) : null;
    }

    public void UpdateRecruits(Recruits recruits) {

        if (vec_heroes.HaveTwoFreemans())
            while (recruits.GetID1() == recruits.GetID2()) recruits.SetHero2(GetFreemanHeroes());
        else
            recruits.SetHero2(null);
    }

    public int ColorCapturedObject(int index) {
        return map_captureobj.GetColor(index);
    }
}
