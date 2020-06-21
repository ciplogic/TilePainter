package hellofx.fheroes2.castle;

import hellofx.fheroes2.army.Army;
import hellofx.fheroes2.army.Troop;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.game.DifficultyEnum;
import hellofx.fheroes2.heroes.HeroBase;
import hellofx.fheroes2.heroes.HeroType;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.maps.MapPosition;
import hellofx.fheroes2.maps.objects.Maps;
import hellofx.fheroes2.monster.Monster;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.system.BitModes;
import hellofx.fheroes2.system.Players;
import hellofx.fheroes2.system.Settings;

import java.util.stream.IntStream;

import static hellofx.fheroes2.castle.BuildingKind.*;
import static hellofx.fheroes2.castle.CastleFlags.*;
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
    public int color;
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
                troop.count = (st.getLE16());

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
            default -> race = H2Color.NONE != GetColor() && ((RaceKind.ALL & kingdom_race) != 0) ? kingdom_race : RaceKind.Rand();
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
        return color;
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
        if (RaceKind.NECR == race && ((building & BUILD_TAVERN) != 0)) {
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
        if ((building & BuildingKind.BUILD_MAGEGUILD5) != 0) return 5;
        if ((building & BuildingKind.BUILD_MAGEGUILD4) != 0) return 4;
        if ((building & BuildingKind.BUILD_MAGEGUILD3) != 0) return 3;
        if ((building & BuildingKind.BUILD_MAGEGUILD2) != 0) return 2;
        if ((building & BuildingKind.BUILD_MAGEGUILD1) != 0) return 1;
        return 0;
    }

    private void SetColor(int col) {
        this.color = col;
    }

    public H2Point GetCenter() {
        return mapPosition.center;
    }

    public int GetRace() {
        return race;
    }

    public boolean isCastle() {
        return (building & BuildingKind.BUILD_CASTLE) != 0;
    }

    public void ChangeColor(int cl) {
        SetColor(cl);
        army.SetColor(cl);
    }

    public boolean isBuild(int buildToCheck) {
        return (building & buildToCheck) != 0;
    }
}
