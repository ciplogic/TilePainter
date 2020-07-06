package hellofx.fheroes2.castle;

import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.army.Army;
import hellofx.fheroes2.army.Troop;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.game.DifficultyEnum;
import hellofx.fheroes2.heroes.HeroBase;
import hellofx.fheroes2.heroes.HeroType;
import hellofx.fheroes2.kingdom.*;
import hellofx.fheroes2.maps.MapPosition;
import hellofx.fheroes2.maps.objects.Maps;
import hellofx.fheroes2.monster.Monster;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.system.BitModes;
import hellofx.fheroes2.system.Players;
import hellofx.fheroes2.system.Settings;

import java.util.stream.IntStream;

import static hellofx.fheroes2.castle.CastleFlags.*;
import static hellofx.fheroes2.castle.buildcond_t.*;
import static hellofx.fheroes2.castle.building_t.*;
import static hellofx.fheroes2.game.GameConsts.CASTLEMAXMONSTER;

public class Castle {
    public MapPosition mapPosition = new MapPosition();
    public int race;
    public int building;
    public Captain captain;
    public String name = "";
    public MageGuild mageguild = new MageGuild();
    public int[] dwelling = new int[CASTLEMAXMONSTER];
    public Army army = new Army();    //from MapColor enum
    public ColorBase color = new ColorBase();
    public BitModes bitModes = new BitModes();


    public Castle() {
    }

    public Castle(int cx, int cy, int rc) {
        mapPosition = new MapPosition(new H2Point(cx, cy));
        captain = new Captain(this);
        //TODO
        //fill(dwelling, dwelling + CASTLEMAXMONSTER, 0);
        army.SetCommander(captain);
    }

    public boolean isPosition(H2Point center) {
        var mp = mapPosition.GetCenter();
        return mp.equals(center);
    }

    public void LoadFromMP2(ByteVectorReader st) {
        switch (st.get()) {
            case 0:
                SetColor(H2Color.BLUE);
                break;
            case 1:
                SetColor(H2Color.GREEN);
                break;
            case 2:
                SetColor(H2Color.RED);
                break;
            case 3:
                SetColor(H2Color.YELLOW);
                break;
            case 4:
                SetColor(H2Color.ORANGE);
                break;
            case 5:
                SetColor(H2Color.PURPLE);
                break;
            default:
                SetColor(H2Color.NONE);
                break;
        }

        // custom building
        if (st.get() != 0) {
            // building
            var build = st.getLE16();
            if ((0x0002 & build) != 0) building |= BUILD_THIEVESGUILD;
            if ((0x0004 & build) != 0) building |= BUILD_TAVERN;
            if ((0x0008 & build) != 0) building |= BUILD_SHIPYARD;
            if ((0x0010 & build) != 0) building |= BUILD_WELL;
            if ((0x0080 & build) != 0) building |= BUILD_STATUE;
            if ((0x0100 & build) != 0) building |= BUILD_LEFTTURRET;
            if ((0x0200 & build) != 0) building |= BUILD_RIGHTTURRET;
            if ((0x0400 & build) != 0) building |= BUILD_MARKETPLACE;
            if ((0x1000 & build) != 0) building |= BUILD_MOAT;
            if ((0x0800 & build) != 0) building |= BUILD_WEL2;
            if ((0x2000 & build) != 0) building |= BUILD_SPEC;

            // dwelling
            var dwell = st.getLE16();
            if ((0x0008 & dwell) != 0) building |= DWELLING_MONSTER1;
            if ((0x0010 & dwell) != 0) building |= DWELLING_MONSTER2;
            if ((0x0020 & dwell) != 0) building |= DWELLING_MONSTER3;
            if ((0x0040 & dwell) != 0) building |= DWELLING_MONSTER4;
            if ((0x0080 & dwell) != 0) building |= DWELLING_MONSTER5;
            if ((0x0100 & dwell) != 0) building |= DWELLING_MONSTER6;
            if ((0x0200 & dwell) != 0) building |= DWELLING_UPGRADE2 | DWELLING_MONSTER2;
            if ((0x0400 & dwell) != 0) building |= DWELLING_UPGRADE3 | DWELLING_MONSTER3;
            if ((0x0800 & dwell) != 0) building |= DWELLING_UPGRADE4 | DWELLING_MONSTER4;
            if ((0x1000 & dwell) != 0) building |= DWELLING_UPGRADE5 | DWELLING_MONSTER5;
            if ((0x2000 & dwell) != 0) building |= DWELLING_UPGRADE6 | DWELLING_MONSTER6;

            // magic tower
            var level = st.get();
            if (0 < level) building |= BUILD_MAGEGUILD1;
            if (1 < level) building |= BUILD_MAGEGUILD2;
            if (2 < level) building |= BUILD_MAGEGUILD3;
            if (3 < level) building |= BUILD_MAGEGUILD4;
            if (4 < level) building |= BUILD_MAGEGUILD5;
        } else {
            st.skip(5);

            // default building
            building |= DWELLING_MONSTER1;
            var dwelling2 = 0;
            switch (Settings.Get().GameDifficulty()) {
                case DifficultyEnum.EASY:
                    dwelling2 = 75;
                    break;
                case DifficultyEnum.NORMAL:
                    dwelling2 = 50;
                    break;
                case DifficultyEnum.HARD:
                    dwelling2 = 25;
                    break;
                case DifficultyEnum.EXPERT:
                    dwelling2 = 10;
                    break;
                default:
                    break;
            }
            if (dwelling2 != 0 && dwelling2 >= Rand.Get(1, 100)) building |= DWELLING_MONSTER2;
        }

        // custom troops
        var custom_troops = st.get() != 0;
        if (custom_troops) {
            var troops = new Troop[5];
            IntStream.range(0, 5)
                    .forEach(i -> troops[i] = new Troop());

            // set monster id
            for (var troop : troops)
                troop.SetMonster(new Monster(st.get() + 1));

            // set count
            for (var troop : troops)
                troop.count = st.getLE16();

            army.m_troops.assign(troops);
            bitModes.SetModes(CUSTOMARMY);
        } else
            st.skip(15);

        // captain
        if (st.get() != 0) building |= BUILD_CAPTAIN;

        // custom name
        st.skip(1);
        name = st.toString(13);

        // race
        var kingdom_race = Players.GetPlayerRace(GetColor());
        switch (st.get()) {
            case 0 -> race = RaceKind.KNGT;
            case 1 -> race = RaceKind.BARB;
            case 2 -> race = RaceKind.SORC;
            case 3 -> race = RaceKind.WRLK;
            case 4 -> race = RaceKind.WZRD;
            case 5 -> race = RaceKind.NECR;
            default -> race = H2Color.NONE != GetColor() && (RaceKind.ALL & kingdom_race) != 0 ? kingdom_race : RaceKind.Rand();
        }

        // castle
        building |= st.get() != 0 ? BUILD_CASTLE : BUILD_TENT;

        // allow upgrade to castle (0 - true, 1 - false)
        if (st.get() != 0)
            bitModes.ResetModes(ALLOWCASTLE);
        else
            bitModes.SetModes(ALLOWCASTLE);

        // unknown 29 byte
        //

        PostLoad();
    }

    public int GetColor() {
        return color.color;
    }

    private void PostLoad() {
        //TODO
        // dwelling pack
        if ((building & DWELLING_MONSTER1) != 0) dwelling[0] = new Monster(race, DWELLING_MONSTER1).GetGrown();
        if ((building & DWELLING_MONSTER2) != 0) dwelling[1] = new Monster(race, DWELLING_MONSTER2).GetGrown();
        if ((building & DWELLING_UPGRADE2) != 0) dwelling[1] = new Monster(race, DWELLING_UPGRADE2).GetGrown();
        if ((building & DWELLING_MONSTER3) != 0) dwelling[2] = new Monster(race, DWELLING_MONSTER3).GetGrown();
        if ((building & DWELLING_UPGRADE3) != 0) dwelling[2] = new Monster(race, DWELLING_UPGRADE3).GetGrown();
        if ((building & DWELLING_MONSTER4) != 0) dwelling[3] = new Monster(race, DWELLING_MONSTER4).GetGrown();
        if ((building & DWELLING_UPGRADE4) != 0) dwelling[3] = new Monster(race, DWELLING_UPGRADE4).GetGrown();
        if ((building & DWELLING_MONSTER5) != 0) dwelling[4] = new Monster(race, DWELLING_MONSTER5).GetGrown();
        if ((building & DWELLING_UPGRADE5) != 0) dwelling[4] = new Monster(race, DWELLING_UPGRADE5).GetGrown();
        if ((building & DWELLING_MONSTER6) != 0) dwelling[5] = new Monster(race, DWELLING_MONSTER6).GetGrown();
        if ((building & DWELLING_UPGRADE6) != 0) dwelling[5] = new Monster(race, DWELLING_UPGRADE6).GetGrown();
        if ((building & DWELLING_UPGRADE7) != 0) dwelling[5] = new Monster(race, DWELLING_UPGRADE7).GetGrown();

        // fix upgrade dwelling dependend from race
        switch (race) {
            case RaceKind.BARB:
                building &= ~(DWELLING_UPGRADE3 | DWELLING_UPGRADE6);
                break;
            case RaceKind.SORC:
                building &= ~(DWELLING_UPGRADE5 | DWELLING_UPGRADE6);
                break;
            case RaceKind.WRLK:
                building &= ~(DWELLING_UPGRADE2 | DWELLING_UPGRADE3 | DWELLING_UPGRADE5);
                break;
            case RaceKind.WZRD:
                building &= ~(DWELLING_UPGRADE2 | DWELLING_UPGRADE4);
                break;
            case RaceKind.NECR:
                building &= ~DWELLING_UPGRADE6;
                break;
            default:
                break;
        }

        army.SetColor(GetColor());

        // fix captain
        if ((building & BUILD_CAPTAIN) != 0)
            captain.LoadDefaults(HeroType.CAPTAIN, race);

        // MageGuild
        mageguild.Builds(race, HaveLibraryCapability());
        // educate heroes and captain
        EducateHeroes();

        // AI troops auto pack for gray towns
        if (H2Color.NONE == GetColor() &&
                !bitModes.Modes(CUSTOMARMY))
            JoinRNDArmy();

        // fix shipyard
        if (!HaveNearlySea()) building &= ~BUILD_SHIPYARD;

        // remove tavern from necromancer castle
        if (RaceKind.NECR == race && (building & BUILD_TAVERN) != 0) {
            building &= ~BUILD_TAVERN;
            if (Settings.Get().PriceLoyaltyVersion())
                building |= BUILD_SHRINE;
        }

        bitModes.SetModes(ALLOWBUILD);
    }

    private boolean HaveNearlySea() {
        var centerPos = GetCenter();
        var world = World.Instance;
        // check nearest ocean
        if (Maps.isValidAbsPoint(centerPos.x, centerPos.y + 2)) {
            var index = Maps.GetIndexFromAbsPoint(centerPos.x, centerPos.y + 2);
            var left = world.GetTiles(index - 1);
            var right = world.GetTiles(index + 1);
            var center = world.GetTiles(index);

            return left.isWater() || right.isWater() || center.isWater();
        }
        return false;
    }

    private void JoinRNDArmy() {
        var mon1 = new Monster(race, DWELLING_MONSTER1);
        var mon2 = new Monster(race, DWELLING_MONSTER2);
        var mon3 = new Monster(race, DWELLING_MONSTER3);

        switch (Rand.Get(1, 4)) {
            case 1:
                army.m_troops.JoinTroop(mon1, mon1.GetRNDSize(false) * 3);
                army.m_troops.JoinTroop(mon2, mon2.GetRNDSize(false));
                break;

            case 2:
                army.m_troops.JoinTroop(mon1, mon1.GetRNDSize(false) * 2);
                army.m_troops.JoinTroop(mon2, mon2.GetRNDSize(false) * 2);
                break;

            case 3:

                army.m_troops.JoinTroop(mon1, mon1.GetRNDSize(false) * 2);
                army.m_troops.JoinTroop(mon2, mon2.GetRNDSize(false));
                army.m_troops.JoinTroop(mon3, mon3.GetRNDSize(false) * 2 / 3);
                break;

            default:
                army.m_troops.JoinTroop(mon1, mon1.GetRNDSize(false));
                army.m_troops.JoinTroop(mon3, mon3.GetRNDSize(false));
                break;
        }
    }

    private boolean HaveLibraryCapability() {
        return race == RaceKind.WZRD;
    }

    private void EducateHeroes() {
        // for learns new spells need 1 day
        if (GetLevelMageGuild() == 0)
            return;
        var world = World.Instance;
        var heroes = world.GetHeroes(this);

        if (heroes.FullHouse()) {
            MageGuildEducateHero(heroes.Guest());
            MageGuildEducateHero(heroes.Guard());
        } else if (heroes.IsValid())
            MageGuildEducateHero(heroes.GuestFirst());

        // captain
        if (captain.isValid()) MageGuildEducateHero(captain);
    }

    private void MageGuildEducateHero(HeroBase hero) {
        //TODO
    }

    private int GetLevelMageGuild() {
        if ((building & building_t.BUILD_MAGEGUILD5) != 0) return 5;
        if ((building & building_t.BUILD_MAGEGUILD4) != 0) return 4;
        if ((building & building_t.BUILD_MAGEGUILD3) != 0) return 3;
        if ((building & building_t.BUILD_MAGEGUILD2) != 0) return 2;
        if ((building & building_t.BUILD_MAGEGUILD1) != 0) return 1;
        return 0;
    }

    private void SetColor(int col) {
        this.color.color = col;
    }

    public H2Point GetCenter() {
        return mapPosition.center;
    }

    public int GetRace() {
        return race;
    }

    public boolean isCastle() {
        return (building & building_t.BUILD_CASTLE) != 0;
    }

    public void ChangeColor(int cl) {
        SetColor(cl);
        army.SetColor(cl);
    }

    public boolean isBuild(int buildToCheck) {
        return (building & buildToCheck) != 0;
    }

    public int GetActualDwelling(int build) {
        switch (build) {
            case DWELLING_MONSTER1:
            case DWELLING_UPGRADE2:
            case DWELLING_UPGRADE3:
            case DWELLING_UPGRADE4:
            case DWELLING_UPGRADE5:
            case DWELLING_UPGRADE7:
                return build;
            case DWELLING_MONSTER2:
                return (building & DWELLING_UPGRADE2) != 0 ? DWELLING_UPGRADE2 : build;
            case DWELLING_MONSTER3:
                return (building & DWELLING_UPGRADE3) != 0 ? DWELLING_UPGRADE3 : build;
            case DWELLING_MONSTER4:
                return (building & DWELLING_UPGRADE4) != 0 ? DWELLING_UPGRADE4 : build;
            case DWELLING_MONSTER5:
                return (building & DWELLING_UPGRADE5) != 0 ? DWELLING_UPGRADE5 : build;
            case DWELLING_MONSTER6:
                return (building & DWELLING_UPGRADE7) != 0
                        ? DWELLING_UPGRADE7
                        : (building & DWELLING_UPGRADE6) != 0
                        ? DWELLING_UPGRADE6
                        : build;
            case DWELLING_UPGRADE6:
                return (building & DWELLING_UPGRADE7) != 0 ? DWELLING_UPGRADE7 : build;
            default:
                break;
        }

        return BUILD_NOTHING;
    }

    public int GetUpgradeBuilding(int build) {

        switch (build) {
            case BUILD_TENT:
                return BUILD_CASTLE;
            case BUILD_MAGEGUILD1:
                return BUILD_MAGEGUILD2;
            case BUILD_MAGEGUILD2:
                return BUILD_MAGEGUILD3;
            case BUILD_MAGEGUILD3:
                return BUILD_MAGEGUILD4;
            case BUILD_MAGEGUILD4:
                return BUILD_MAGEGUILD5;
            default:
                break;
        }

        if (RaceKind.BARB == race) {
            switch (build) {
                case DWELLING_MONSTER2:
                    return DWELLING_UPGRADE2;
                case DWELLING_MONSTER4:
                    return DWELLING_UPGRADE4;
                case DWELLING_MONSTER5:
                    return DWELLING_UPGRADE5;
                default:
                    break;
            }
        } else if (RaceKind.KNGT == race) {
            switch (build) {
                case DWELLING_MONSTER2:
                    return DWELLING_UPGRADE2;
                case DWELLING_MONSTER3:
                    return DWELLING_UPGRADE3;
                case DWELLING_MONSTER4:
                    return DWELLING_UPGRADE4;
                case DWELLING_MONSTER5:
                    return DWELLING_UPGRADE5;
                case DWELLING_MONSTER6:
                    return DWELLING_UPGRADE6;
                default:
                    break;
            }
        } else if (RaceKind.NECR == race) {
            switch (build) {
                case DWELLING_MONSTER2:
                    return DWELLING_UPGRADE2;
                case DWELLING_MONSTER3:
                    return DWELLING_UPGRADE3;
                case DWELLING_MONSTER4:
                    return DWELLING_UPGRADE4;
                case DWELLING_MONSTER5:
                    return DWELLING_UPGRADE5;
                default:
                    break;
            }
        } else if (RaceKind.SORC == race) {
            switch (build) {
                case DWELLING_MONSTER2:
                    return DWELLING_UPGRADE2;
                case DWELLING_MONSTER3:
                    return DWELLING_UPGRADE3;
                case DWELLING_MONSTER4:
                    return DWELLING_UPGRADE4;
                default:
                    break;
            }
        } else if (RaceKind.WRLK == race) {
            switch (build) {
                case DWELLING_MONSTER4:
                    return DWELLING_UPGRADE4;
                case DWELLING_MONSTER6:
                    return isBuild(DWELLING_UPGRADE6) ? DWELLING_UPGRADE7 : DWELLING_UPGRADE6;
                default:
                    break;
            }
        } else if (RaceKind.WZRD == race) {
            switch (build) {
                case DWELLING_MONSTER3:
                    return DWELLING_UPGRADE3;
                case DWELLING_MONSTER5:
                    return DWELLING_UPGRADE5;
                case DWELLING_MONSTER6:
                    return DWELLING_UPGRADE6;
                default:
                    break;
            }
        }

        return build;
    }

    int GetICNBuilding(int build, int race) {
        if (RaceKind.BARB == race) {
            switch (build) {
                case BUILD_CASTLE:
                    return IcnKind.TWNBCSTL;
                case BUILD_TENT:
                    return IcnKind.TWNBTENT;
                case BUILD_SPEC:
                    return IcnKind.TWNBSPEC;
                case BUILD_CAPTAIN:
                    return IcnKind.TWNBCAPT;
                case BUILD_WEL2:
                    return IcnKind.TWNBWEL2;
                case BUILD_LEFTTURRET:
                    return IcnKind.TWNBLTUR;
                case BUILD_RIGHTTURRET:
                    return IcnKind.TWNBRTUR;
                case BUILD_MOAT:
                    return IcnKind.TWNBMOAT;
                case BUILD_MARKETPLACE:
                    return IcnKind.TWNBMARK;
                case BUILD_THIEVESGUILD:
                    return IcnKind.TWNBTHIE;
                case BUILD_TAVERN:
                    return IcnKind.TWNBTVRN;
                case BUILD_WELL:
                    return IcnKind.TWNBWELL;
                case BUILD_STATUE:
                    return IcnKind.TWNBSTAT;
                case BUILD_SHIPYARD:
                    return IcnKind.TWNBDOCK;
                case BUILD_MAGEGUILD1:
                case BUILD_MAGEGUILD2:
                case BUILD_MAGEGUILD3:
                case BUILD_MAGEGUILD4:
                case BUILD_MAGEGUILD5:
                    return IcnKind.TWNBMAGE;
                case DWELLING_MONSTER1:
                    return IcnKind.TWNBDW_0;
                case DWELLING_MONSTER2:
                    return IcnKind.TWNBDW_1;
                case DWELLING_UPGRADE2:
                    return IcnKind.TWNBUP_1;
                case DWELLING_MONSTER3:
                    return IcnKind.TWNBDW_2;
                case DWELLING_MONSTER4:
                    return IcnKind.TWNBDW_3;
                case DWELLING_UPGRADE4:
                    return IcnKind.TWNBUP_3;
                case DWELLING_MONSTER5:
                    return IcnKind.TWNBDW_4;
                case DWELLING_UPGRADE5:
                    return IcnKind.TWNBUP_4;
                case DWELLING_MONSTER6:
                    return IcnKind.TWNBDW_5;
                default:
                    break;
            }
        } else if (RaceKind.KNGT == race) {
            switch (build) {
                case BUILD_CASTLE:
                    return IcnKind.TWNKCSTL;
                case BUILD_TENT:
                    return IcnKind.TWNKTENT;
                case BUILD_SPEC:
                    return IcnKind.TWNKSPEC;
                case BUILD_CAPTAIN:
                    return IcnKind.TWNKCAPT;
                case BUILD_WEL2:
                    return IcnKind.TWNKWEL2;
                case BUILD_LEFTTURRET:
                    return IcnKind.TWNKLTUR;
                case BUILD_RIGHTTURRET:
                    return IcnKind.TWNKRTUR;
                case BUILD_MOAT:
                    return IcnKind.TWNKMOAT;
                case BUILD_MARKETPLACE:
                    return IcnKind.TWNKMARK;
                case BUILD_THIEVESGUILD:
                    return IcnKind.TWNKTHIE;
                case BUILD_TAVERN:
                    return IcnKind.TWNKTVRN;
                case BUILD_WELL:
                    return IcnKind.TWNKWELL;
                case BUILD_STATUE:
                    return IcnKind.TWNKSTAT;
                case BUILD_SHIPYARD:
                    return IcnKind.TWNKDOCK;
                case BUILD_MAGEGUILD1:
                case BUILD_MAGEGUILD2:
                case BUILD_MAGEGUILD3:
                case BUILD_MAGEGUILD4:
                case BUILD_MAGEGUILD5:
                    return IcnKind.TWNKMAGE;
                case DWELLING_MONSTER1:
                    return IcnKind.TWNKDW_0;
                case DWELLING_MONSTER2:
                    return IcnKind.TWNKDW_1;
                case DWELLING_UPGRADE2:
                    return IcnKind.TWNKUP_1;
                case DWELLING_MONSTER3:
                    return IcnKind.TWNKDW_2;
                case DWELLING_UPGRADE3:
                    return IcnKind.TWNKUP_2;
                case DWELLING_MONSTER4:
                    return IcnKind.TWNKDW_3;
                case DWELLING_UPGRADE4:
                    return IcnKind.TWNKUP_3;
                case DWELLING_MONSTER5:
                    return IcnKind.TWNKDW_4;
                case DWELLING_UPGRADE5:
                    return IcnKind.TWNKUP_4;
                case DWELLING_MONSTER6:
                    return IcnKind.TWNKDW_5;
                case DWELLING_UPGRADE6:
                    return IcnKind.TWNKUP_5;
                default:
                    break;
            }
        } else if (RaceKind.NECR == race) {
            switch (build) {
                case BUILD_CASTLE:
                    return IcnKind.TWNNCSTL;
                case BUILD_TENT:
                    return IcnKind.TWNNTENT;
                case BUILD_SPEC:
                    return IcnKind.TWNNSPEC;
                case BUILD_CAPTAIN:
                    return IcnKind.TWNNCAPT;
                case BUILD_WEL2:
                    return IcnKind.TWNNWEL2;
                case BUILD_LEFTTURRET:
                    return IcnKind.TWNNLTUR;
                case BUILD_RIGHTTURRET:
                    return IcnKind.TWNNRTUR;
                case BUILD_MOAT:
                    return IcnKind.TWNNMOAT;
                case BUILD_MARKETPLACE:
                    return IcnKind.TWNNMARK;
                case BUILD_THIEVESGUILD:
                    return IcnKind.TWNNTHIE;
                // shrine
                case BUILD_SHRINE:
                    return IcnKind.TWNNTVRN;
                case BUILD_WELL:
                    return IcnKind.TWNNWELL;
                case BUILD_STATUE:
                    return IcnKind.TWNNSTAT;
                case BUILD_SHIPYARD:
                    return IcnKind.TWNNDOCK;
                case BUILD_MAGEGUILD1:
                case BUILD_MAGEGUILD2:
                case BUILD_MAGEGUILD3:
                case BUILD_MAGEGUILD4:
                case BUILD_MAGEGUILD5:
                    return IcnKind.TWNNMAGE;
                case DWELLING_MONSTER1:
                    return IcnKind.TWNNDW_0;
                case DWELLING_MONSTER2:
                    return IcnKind.TWNNDW_1;
                case DWELLING_UPGRADE2:
                    return IcnKind.TWNNUP_1;
                case DWELLING_MONSTER3:
                    return IcnKind.TWNNDW_2;
                case DWELLING_UPGRADE3:
                    return IcnKind.TWNNUP_2;
                case DWELLING_MONSTER4:
                    return IcnKind.TWNNDW_3;
                case DWELLING_UPGRADE4:
                    return IcnKind.TWNNUP_3;
                case DWELLING_MONSTER5:
                    return IcnKind.TWNNDW_4;
                case DWELLING_UPGRADE5:
                    return IcnKind.TWNNUP_4;
                case DWELLING_MONSTER6:
                    return IcnKind.TWNNDW_5;
                default:
                    break;
            }
        } else if (RaceKind.SORC == race) {
            switch (build) {
                case BUILD_CASTLE:
                    return IcnKind.TWNSCSTL;
                case BUILD_TENT:
                    return IcnKind.TWNSTENT;
                case BUILD_SPEC:
                    return IcnKind.TWNSSPEC;
                case BUILD_CAPTAIN:
                    return IcnKind.TWNSCAPT;
                case BUILD_WEL2:
                    return IcnKind.TWNSWEL2;
                case BUILD_LEFTTURRET:
                    return IcnKind.TWNSLTUR;
                case BUILD_RIGHTTURRET:
                    return IcnKind.TWNSRTUR;
                case BUILD_MOAT:
                    return IcnKind.TWNSMOAT;
                case BUILD_MARKETPLACE:
                    return IcnKind.TWNSMARK;
                case BUILD_THIEVESGUILD:
                    return IcnKind.TWNSTHIE;
                case BUILD_TAVERN:
                    return IcnKind.TWNSTVRN;
                case BUILD_WELL:
                    return IcnKind.TWNSWELL;
                case BUILD_STATUE:
                    return IcnKind.TWNSSTAT;
                case BUILD_SHIPYARD:
                    return IcnKind.TWNSDOCK;
                case BUILD_MAGEGUILD1:
                case BUILD_MAGEGUILD2:
                case BUILD_MAGEGUILD3:
                case BUILD_MAGEGUILD4:
                case BUILD_MAGEGUILD5:
                    return IcnKind.TWNSMAGE;
                case DWELLING_MONSTER1:
                    return IcnKind.TWNSDW_0;
                case DWELLING_MONSTER2:
                    return IcnKind.TWNSDW_1;
                case DWELLING_UPGRADE2:
                    return IcnKind.TWNSUP_1;
                case DWELLING_MONSTER3:
                    return IcnKind.TWNSDW_2;
                case DWELLING_UPGRADE3:
                    return IcnKind.TWNSUP_2;
                case DWELLING_MONSTER4:
                    return IcnKind.TWNSDW_3;
                case DWELLING_UPGRADE4:
                    return IcnKind.TWNSUP_3;
                case DWELLING_MONSTER5:
                    return IcnKind.TWNSDW_4;
                case DWELLING_MONSTER6:
                    return IcnKind.TWNSDW_5;
                default:
                    break;
            }
        } else if (RaceKind.WRLK == race) {
            switch (build) {
                case BUILD_CASTLE:
                    return IcnKind.TWNWCSTL;
                case BUILD_TENT:
                    return IcnKind.TWNWTENT;
                case BUILD_SPEC:
                    return IcnKind.TWNWSPEC;
                case BUILD_CAPTAIN:
                    return IcnKind.TWNWCAPT;
                case BUILD_WEL2:
                    return IcnKind.TWNWWEL2;
                case BUILD_LEFTTURRET:
                    return IcnKind.TWNWLTUR;
                case BUILD_RIGHTTURRET:
                    return IcnKind.TWNWRTUR;
                case BUILD_MOAT:
                    return IcnKind.TWNWMOAT;
                case BUILD_MARKETPLACE:
                    return IcnKind.TWNWMARK;
                case BUILD_THIEVESGUILD:
                    return IcnKind.TWNWTHIE;
                case BUILD_TAVERN:
                    return IcnKind.TWNWTVRN;
                case BUILD_WELL:
                    return IcnKind.TWNWWELL;
                case BUILD_STATUE:
                    return IcnKind.TWNWSTAT;
                case BUILD_SHIPYARD:
                    return IcnKind.TWNWDOCK;
                case BUILD_MAGEGUILD1:
                case BUILD_MAGEGUILD2:
                case BUILD_MAGEGUILD3:
                case BUILD_MAGEGUILD4:
                case BUILD_MAGEGUILD5:
                    return IcnKind.TWNWMAGE;
                case DWELLING_MONSTER1:
                    return IcnKind.TWNWDW_0;
                case DWELLING_MONSTER2:
                    return IcnKind.TWNWDW_1;
                case DWELLING_MONSTER3:
                    return IcnKind.TWNWDW_2;
                case DWELLING_MONSTER4:
                    return IcnKind.TWNWDW_3;
                case DWELLING_UPGRADE4:
                    return IcnKind.TWNWUP_3;
                case DWELLING_MONSTER5:
                    return IcnKind.TWNWDW_4;
                case DWELLING_MONSTER6:
                    return IcnKind.TWNWDW_5;
                case DWELLING_UPGRADE6:
                    return IcnKind.TWNWUP_5;
                case DWELLING_UPGRADE7:
                    return IcnKind.TWNWUP5B;
                default:
                    break;
            }
        } else if (RaceKind.WZRD == race) {
            switch (build) {
                case BUILD_CASTLE:
                    return IcnKind.TWNZCSTL;
                case BUILD_TENT:
                    return IcnKind.TWNZTENT;
                case BUILD_SPEC:
                    return IcnKind.TWNZSPEC;
                case BUILD_CAPTAIN:
                    return IcnKind.TWNZCAPT;
                case BUILD_WEL2:
                    return IcnKind.TWNZWEL2;
                case BUILD_LEFTTURRET:
                    return IcnKind.TWNZLTUR;
                case BUILD_RIGHTTURRET:
                    return IcnKind.TWNZRTUR;
                case BUILD_MOAT:
                    return IcnKind.TWNZMOAT;
                case BUILD_MARKETPLACE:
                    return IcnKind.TWNZMARK;
                case BUILD_THIEVESGUILD:
                    return IcnKind.TWNZTHIE;
                case BUILD_TAVERN:
                    return IcnKind.TWNZTVRN;
                case BUILD_WELL:
                    return IcnKind.TWNZWELL;
                case BUILD_STATUE:
                    return IcnKind.TWNZSTAT;
                case BUILD_SHIPYARD:
                    return IcnKind.TWNZDOCK;
                case BUILD_MAGEGUILD1:
                case BUILD_MAGEGUILD2:
                case BUILD_MAGEGUILD3:
                case BUILD_MAGEGUILD4:
                case BUILD_MAGEGUILD5:
                    return IcnKind.TWNZMAGE;
                case DWELLING_MONSTER1:
                    return IcnKind.TWNZDW_0;
                case DWELLING_MONSTER2:
                    return IcnKind.TWNZDW_1;
                case DWELLING_MONSTER3:
                    return IcnKind.TWNZDW_2;
                case DWELLING_UPGRADE3:
                    return IcnKind.TWNZUP_2;
                case DWELLING_MONSTER4:
                    return IcnKind.TWNZDW_3;
                case DWELLING_MONSTER5:
                    return IcnKind.TWNZDW_4;
                case DWELLING_UPGRADE5:
                    return IcnKind.TWNZUP_4;
                case DWELLING_MONSTER6:
                    return IcnKind.TWNZDW_5;
                case DWELLING_UPGRADE6:
                    return IcnKind.TWNZUP_5;
                default:
                    break;
            }
        }

        return IcnKind.UNKNOWN;
    }

    /* check allow buy building */
    public int CheckBuyBuilding(int build) {
        if ((build & building) != 0) return ALREADY_BUILT;

        switch (build) {
            // allow build castle
            case BUILD_CASTLE:
                if (!bitModes.Modes(ALLOWCASTLE)) return BUILD_DISABLE;
                break;
            // buid shipyard only nearly sea
            case BUILD_SHIPYARD:
                if (!HaveNearlySea()) return BUILD_DISABLE;
                break;
            case BUILD_SHRINE:
                if (RaceKind.NECR != GetRace() || !Settings.Get().PriceLoyaltyVersion()) return BUILD_DISABLE;
                break;
            case BUILD_TAVERN:
                if (RaceKind.NECR == GetRace()) return BUILD_DISABLE;
                break;

            default:
                break;
        }

        if (!bitModes.Modes(ALLOWBUILD)) return NOT_TODAY;

        if (isCastle()) {
            if (build == BUILD_TENT) return BUILD_DISABLE;
        } else {
            if (build != BUILD_CASTLE) return NEED_CASTLE;
        }

        switch (build) {
            // check upgrade dwelling
            case DWELLING_UPGRADE2:
                if (0 != ((RaceKind.WRLK | RaceKind.WZRD) & race)) return UNKNOWN_UPGRADE;
                break;
            case DWELLING_UPGRADE3:
                if (0 != ((RaceKind.BARB | RaceKind.WRLK) & race)) return UNKNOWN_UPGRADE;
                break;
            case DWELLING_UPGRADE4:
                if (0 != (RaceKind.WZRD & race)) return UNKNOWN_UPGRADE;
                break;
            case DWELLING_UPGRADE5:
                if (0 != ((RaceKind.SORC | RaceKind.WRLK) & race)) return UNKNOWN_UPGRADE;
                break;
            case DWELLING_UPGRADE6:
                if (0 != ((RaceKind.BARB | RaceKind.SORC | RaceKind.NECR) & race)) return UNKNOWN_UPGRADE;
                break;
            case DWELLING_UPGRADE7:
                if (RaceKind.WRLK != race) return UNKNOWN_UPGRADE;
                break;

            default:
                break;
        }

        // check build requirements
        int requires = GetBuildingRequires(build);

        for (int itr = 0x00000001; itr != 0; itr <<= 1)
            if (0 != (requires & itr) && 0 == (building & itr)) return REQUIRES_BUILD;

        // check valid payment
        if (!GetKingdom().AllowPayment(PaymentConditions.BuyBuilding(race, build))) return LACK_RESOURCES;

        return ALLOW_BUILD;
    }

    private int GetBuildingRequires(int build) {
        var requires = 0;

        switch (build) {
            case BUILD_SPEC:
                switch (race) {
                    case RaceKind.WZRD:
                        requires |= BUILD_MAGEGUILD1;
                        break;

                    default:
                        break;
                }
                break;

            case DWELLING_MONSTER2:
                switch (race) {
                    case RaceKind.KNGT:
                    case RaceKind.BARB:
                    case RaceKind.WZRD:
                    case RaceKind.WRLK:
                    case RaceKind.NECR:
                        requires |= DWELLING_MONSTER1;
                        break;

                    case RaceKind.SORC:
                        requires |= DWELLING_MONSTER1;
                        requires |= BUILD_TAVERN;
                        break;

                    default:
                        break;
                }
                break;

            case DWELLING_MONSTER3:
                switch (race) {
                    case RaceKind.KNGT:
                        requires |= DWELLING_MONSTER1;
                        requires |= BUILD_WELL;
                        break;

                    case RaceKind.BARB:
                    case RaceKind.SORC:
                    case RaceKind.WZRD:
                    case RaceKind.WRLK:
                    case RaceKind.NECR:
                        requires |= DWELLING_MONSTER1;
                        break;

                    default:
                        break;
                }
                break;

            case DWELLING_MONSTER4:
                switch (race) {
                    case RaceKind.KNGT:
                        requires |= DWELLING_MONSTER1;
                        requires |= BUILD_TAVERN;
                        break;

                    case RaceKind.BARB:
                        requires |= DWELLING_MONSTER1;
                        break;

                    case RaceKind.SORC:
                        requires |= DWELLING_MONSTER2;
                        requires |= BUILD_MAGEGUILD1;
                        break;

                    case RaceKind.WZRD:
                    case RaceKind.WRLK:
                        requires |= DWELLING_MONSTER2;
                        break;

                    case RaceKind.NECR:
                        requires |= DWELLING_MONSTER3;
                        requires |= BUILD_THIEVESGUILD;
                        break;

                    default:
                        break;
                }
                break;

            case DWELLING_MONSTER5:
                switch (race) {
                    case RaceKind.KNGT:
                    case RaceKind.BARB:
                        requires |= DWELLING_MONSTER2;
                        requires |= DWELLING_MONSTER3;
                        requires |= DWELLING_MONSTER4;
                        break;

                    case RaceKind.SORC:
                        requires |= DWELLING_MONSTER4;
                        break;

                    case RaceKind.WRLK:
                        requires |= DWELLING_MONSTER3;
                        break;

                    case RaceKind.WZRD:
                        requires |= DWELLING_MONSTER3;
                        requires |= BUILD_MAGEGUILD1;
                        break;

                    case RaceKind.NECR:
                        requires |= DWELLING_MONSTER2;
                        requires |= BUILD_MAGEGUILD1;
                        break;

                    default:
                        break;
                }
                break;

            case DWELLING_MONSTER6:
                switch (race) {
                    case RaceKind.KNGT:
                        requires |= DWELLING_MONSTER2;
                        requires |= DWELLING_MONSTER3;
                        requires |= DWELLING_MONSTER4;
                        break;

                    case RaceKind.BARB:
                    case RaceKind.SORC:
                    case RaceKind.NECR:
                        requires |= DWELLING_MONSTER5;
                        break;

                    case RaceKind.WRLK:
                    case RaceKind.WZRD:
                        requires |= DWELLING_MONSTER4;
                        requires |= DWELLING_MONSTER5;
                        break;

                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE2:
                switch (race) {
                    case RaceKind.KNGT:
                    case RaceKind.BARB:
                        requires |= DWELLING_MONSTER2;
                        requires |= DWELLING_MONSTER3;
                        requires |= DWELLING_MONSTER4;
                        break;

                    case RaceKind.SORC:
                        requires |= DWELLING_MONSTER2;
                        requires |= BUILD_WELL;
                        break;

                    case RaceKind.NECR:
                        requires |= DWELLING_MONSTER2;
                        break;

                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE3:
                switch (race) {
                    case RaceKind.KNGT:
                        requires |= DWELLING_MONSTER2;
                        requires |= DWELLING_MONSTER3;
                        requires |= DWELLING_MONSTER4;
                        break;

                    case RaceKind.SORC:
                        requires |= DWELLING_MONSTER3;
                        requires |= DWELLING_MONSTER4;
                        break;

                    case RaceKind.WZRD:
                        requires |= DWELLING_MONSTER3;
                        requires |= BUILD_WELL;
                        break;

                    case RaceKind.NECR:
                        requires |= DWELLING_MONSTER3;
                        break;

                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE4:
                switch (race) {
                    case RaceKind.KNGT:
                    case RaceKind.BARB:
                        requires |= DWELLING_MONSTER2;
                        requires |= DWELLING_MONSTER3;
                        requires |= DWELLING_MONSTER4;
                        break;

                    case RaceKind.SORC:
                    case RaceKind.WRLK:
                    case RaceKind.NECR:
                        requires |= DWELLING_MONSTER4;
                        break;

                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE5:
                switch (race) {
                    case RaceKind.KNGT:
                        requires |= DWELLING_MONSTER2;
                        requires |= DWELLING_MONSTER3;
                        requires |= DWELLING_MONSTER4;
                        requires |= DWELLING_MONSTER5;
                        break;

                    case RaceKind.BARB:
                        requires |= DWELLING_MONSTER5;
                        break;

                    case RaceKind.WZRD:
                        requires |= BUILD_SPEC;
                        requires |= DWELLING_MONSTER5;
                        break;

                    case RaceKind.NECR:
                        requires |= BUILD_MAGEGUILD2;
                        requires |= DWELLING_MONSTER5;
                        break;

                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE6:
                switch (race) {
                    case RaceKind.KNGT:
                        requires |= DWELLING_MONSTER2;
                        requires |= DWELLING_MONSTER3;
                        requires |= DWELLING_MONSTER4;
                        requires |= DWELLING_MONSTER6;
                        break;

                    case RaceKind.WRLK:
                    case RaceKind.WZRD:
                        requires |= DWELLING_MONSTER6;
                        break;

                    default:
                        break;
                }
                break;

            default:
                break;
        }

        return requires;
    }

    public Kingdom GetKingdom() {
        return color.GetKingdom();
    }
}
