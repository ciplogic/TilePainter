package hellofx.fheroes2.castle;

import hellofx.fheroes2.army.Army;
import hellofx.fheroes2.army.Troop;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.engine.Rand;
import hellofx.fheroes2.game.DifficultyEnum;
import hellofx.fheroes2.kingdom.MapColor;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.maps.objects.MapPosition;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.system.Settings;

import static hellofx.fheroes2.castle.BuildingKind.*;
import static hellofx.fheroes2.game.GameConsts.CASTLEMAXMONSTER;

public class Castle {
    public MapPosition mapPosition = new MapPosition();
    public int race;
    public int building;
    public Captain captain;
    String name;
    MageGuild mageguild;
    int[] dwelling = new int[CASTLEMAXMONSTER]
    Army army = new Army();    //from MapColor enum
    private int color;

    {
    }

    public Castle() {
    }

    public Castle(int cx, int cy, int race) {
    }

    public boolean isPosition(H2Point center) {
        H2Point mp = mapPosition.GetCenter();
        return mp.equals(center);
    }

    public void LoadFromMP2(ByteVectorReader st) {
        switch (st.get()) {
            case 0:
                SetColor(MapColor.BLUE);
                break;
            case 1:
                SetColor(MapColor.GREEN);
                break;
            case 2:
                SetColor(MapColor.RED);
                break;
            case 3:
                SetColor(MapColor.YELLOW);
                break;
            case 4:
                SetColor(MapColor.ORANGE);
                break;
            case 5:
                SetColor(MapColor.PURPLE);
                break;
            default:
                SetColor(MapColor.NONE);
                break;
        }

        // custom building
        if (st.get() != 0) {
            // building
            int build = st.getLE16();
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
            int dwell = st.getLE16();
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
            int level = st.get();
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
            Troop[] troops = new Troop[5];

            // set monster id
            for (var troop : troops)
                troop.SetMonster(st.get() + 1);

            // set count
            for (var troop : troops)
                troop.SetCount(st.getLE16());

            army.m_troops.Assign(troops, ARRAY_COUNT_END(troops));
            SetModes(CUSTOMARMY);
        } else
            st.skip(15);

        // captain
        if (st.get() != 0) building |= BUILD_CAPTAIN;

        // custom name
        st.skip(1);
        name = st.toString(13);

        // race
        var kingdom_race = Players::GetPlayerRace (GetColor());
        switch (st.get()) {
            case 0:
                race = RaceKind.KNGT;
                break;
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
                race = Color::NONE != GetColor() && RaceKind.ALL & kingdom_race ? kingdom_race : Race::Rand ();
                break;
        }

        // castle
        building |= st.get() ? BUILD_CASTLE : BUILD_TENT;

        // allow upgrade to castle (0 - true, 1 - false)
        if (st.get() != 0)
            ResetModes(ALLOWCASTLE);
        else
            SetModes(ALLOWCASTLE);

        // unknown 29 byte
        //

        PostLoad();
    }

    private void PostLoad() {
        //TODO
    }

    private void SetColor(int col) {
        this.color = col;
    }

    public H2Point GetCenter() {
        return mapPosition.center;
    }
}
