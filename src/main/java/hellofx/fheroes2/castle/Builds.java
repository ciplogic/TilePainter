package hellofx.fheroes2.castle;

import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.resource.Funds;

import java.util.ArrayList;
import java.util.List;

import static hellofx.fheroes2.castle.building_t.*;

public class Builds {
    static List<buildstats_t> _builds = new ArrayList<>();

    static void add(int build, int race, Funds funds) {
        _builds.add(new buildstats_t(build, race, funds));
    }

    public void setup() {
        // id                             gold wood mercury ore sulfur crystal gems
        add(BUILD_THIEVESGUILD, RaceKind.ALL, new Funds(750, 5, 0, 0, 0, 0, 0));
        add(BUILD_TAVERN, RaceKind.ALL, new Funds(500, 5, 0, 0, 0, 0, 0));
        add(BUILD_SHIPYARD, RaceKind.ALL, new Funds(2000, 15, 0, 0, 0, 0, 0));
        add(BUILD_WELL, RaceKind.ALL, new Funds(500, 0, 0, 0, 0, 0, 0));
        add(BUILD_STATUE, RaceKind.ALL, new Funds(1250, 0, 0, 5, 0, 0, 0));
        add(BUILD_LEFTTURRET, RaceKind.ALL, new Funds(1500, 0, 0, 5, 0, 0, 0));
        add(BUILD_RIGHTTURRET, RaceKind.ALL, new Funds(1500, 0, 0, 5, 0, 0, 0));
        add(BUILD_MARKETPLACE, RaceKind.ALL, new Funds(500, 5, 0, 0, 0, 0, 0));
        add(BUILD_MOAT, RaceKind.ALL, new Funds(750, 0, 0, 0, 0, 0, 0));
        add(BUILD_CASTLE, RaceKind.ALL, new Funds(5000, 20, 0, 20, 0, 0, 0));
        add(BUILD_CAPTAIN, RaceKind.ALL, new Funds(500, 0, 0, 0, 0, 0, 0));
        add(BUILD_MAGEGUILD1, RaceKind.ALL, new Funds(1000, 5, 0, 5, 0, 0, 0));
        add(BUILD_MAGEGUILD2, RaceKind.ALL, new Funds(1500, 5, 2, 5, 2, 2, 2));
        add(BUILD_MAGEGUILD3, RaceKind.ALL, new Funds(1500, 5, 4, 5, 4, 4, 4));
        add(BUILD_MAGEGUILD4, RaceKind.ALL, new Funds(2000, 5, 6, 5, 6, 6, 6));
        add(BUILD_MAGEGUILD5, RaceKind.ALL, new Funds(2000, 5, 8, 5, 8, 8, 8));

        add(BUILD_WEL2, RaceKind.KNGT, new Funds(1000, 0, 0, 0, 0, 0, 0));
        add(BUILD_WEL2, RaceKind.BARB, new Funds(1000, 0, 0, 0, 0, 0, 0));
        add(BUILD_WEL2, RaceKind.SORC, new Funds(1000, 0, 0, 0, 0, 0, 0));
        add(BUILD_WEL2, RaceKind.WRLK, new Funds(1000, 0, 0, 0, 0, 0, 0));
        add(BUILD_WEL2, RaceKind.WZRD, new Funds(1000, 0, 0, 0, 0, 0, 0));
        add(BUILD_WEL2, RaceKind.NECR, new Funds(1000, 0, 0, 0, 0, 0, 0));

        add(BUILD_SPEC, RaceKind.KNGT, new Funds(1500, 0, 0, 10, 0, 0, 0));
        add(BUILD_SPEC, RaceKind.BARB, new Funds(1500, 5, 0, 5, 0, 0, 0));
        add(BUILD_SPEC, RaceKind.SORC, new Funds(1500, 0, 5, 0, 0, 5, 0));
        add(BUILD_SPEC, RaceKind.WRLK, new Funds(3500, 5, 3, 10, 3, 3, 3));
        add(BUILD_SPEC, RaceKind.WZRD, new Funds(1500, 5, 5, 5, 5, 5, 5));
        add(BUILD_SPEC, RaceKind.NECR, new Funds(1000, 0, 10, 0, 10, 0, 0));

        add(BUILD_SHRINE, RaceKind.NECR, new Funds(4000, 10, 0, 0, 0, 10, 0));

        add(DWELLING_MONSTER1, RaceKind.KNGT, new Funds(200, 0, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER2, RaceKind.KNGT, new Funds(1000, 0, 0, 0, 0, 0, 0));
        add(DWELLING_UPGRADE2, RaceKind.KNGT, new Funds(1500, 5, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER3, RaceKind.KNGT, new Funds(1000, 0, 0, 5, 0, 0, 0));
        add(DWELLING_UPGRADE3, RaceKind.KNGT, new Funds(1500, 0, 0, 5, 0, 0, 0));
        add(DWELLING_MONSTER4, RaceKind.KNGT, new Funds(2000, 10, 0, 10, 0, 0, 0));
        add(DWELLING_UPGRADE4, RaceKind.KNGT, new Funds(2000, 5, 0, 5, 0, 0, 0));
        add(DWELLING_MONSTER5, RaceKind.KNGT, new Funds(3000, 20, 0, 0, 0, 0, 0));
        add(DWELLING_UPGRADE5, RaceKind.KNGT, new Funds(3000, 10, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER6, RaceKind.KNGT, new Funds(5000, 10, 0, 0, 0, 20, 0));
        add(DWELLING_UPGRADE6, RaceKind.KNGT, new Funds(3500, 10, 0, 0, 0, 5, 0));

        add(DWELLING_MONSTER1, RaceKind.BARB, new Funds(300, 0, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER2, RaceKind.BARB, new Funds(800, 5, 0, 0, 0, 0, 0));
        add(DWELLING_UPGRADE2, RaceKind.BARB, new Funds(1200, 5, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER3, RaceKind.BARB, new Funds(1000, 0, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER4, RaceKind.BARB, new Funds(2000, 10, 0, 10, 0, 0, 0));
        add(DWELLING_UPGRADE4, RaceKind.BARB, new Funds(3000, 5, 0, 5, 0, 0, 0));
        add(DWELLING_MONSTER5, RaceKind.BARB, new Funds(4000, 0, 0, 20, 0, 0, 0));
        add(DWELLING_UPGRADE5, RaceKind.BARB, new Funds(2000, 0, 0, 10, 0, 0, 0));
        add(DWELLING_MONSTER6, RaceKind.BARB, new Funds(6000, 0, 0, 10, 0, 20, 0));

        add(DWELLING_MONSTER1, RaceKind.SORC, new Funds(500, 5, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER2, RaceKind.SORC, new Funds(1000, 5, 0, 0, 0, 0, 0));
        add(DWELLING_UPGRADE2, RaceKind.SORC, new Funds(1500, 5, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER3, RaceKind.SORC, new Funds(1500, 0, 0, 0, 0, 0, 0));
        add(DWELLING_UPGRADE3, RaceKind.SORC, new Funds(1500, 5, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER4, RaceKind.SORC, new Funds(1500, 0, 0, 10, 0, 0, 0));
        add(DWELLING_UPGRADE4, RaceKind.SORC, new Funds(2000, 0, 5, 0, 0, 0, 0));
        add(DWELLING_MONSTER5, RaceKind.SORC, new Funds(3000, 10, 0, 0, 0, 0, 10));
        add(DWELLING_MONSTER6, RaceKind.SORC, new Funds(10000, 0, 20, 30, 0, 0, 0));

        add(DWELLING_MONSTER1, RaceKind.WRLK, new Funds(500, 0, 0, 5, 0, 0, 0));
        add(DWELLING_MONSTER2, RaceKind.WRLK, new Funds(1000, 0, 0, 5, 0, 0, 0));
        add(DWELLING_MONSTER3, RaceKind.WRLK, new Funds(2000, 0, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER4, RaceKind.WRLK, new Funds(3000, 0, 0, 0, 0, 0, 10));
        add(DWELLING_UPGRADE4, RaceKind.WRLK, new Funds(2000, 0, 0, 0, 0, 0, 5));
        add(DWELLING_MONSTER5, RaceKind.WRLK, new Funds(4000, 0, 0, 0, 10, 0, 0));
        add(DWELLING_MONSTER6, RaceKind.WRLK, new Funds(13000, 0, 0, 30, 20, 0, 0));
        add(DWELLING_UPGRADE6, RaceKind.WRLK, new Funds(6000, 0, 0, 5, 10, 0, 0));
        add(DWELLING_UPGRADE7, RaceKind.WRLK, new Funds(6000, 0, 0, 5, 10, 0, 0));

        add(DWELLING_MONSTER1, RaceKind.WZRD, new Funds(400, 0, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER2, RaceKind.WZRD, new Funds(800, 0, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER3, RaceKind.WZRD, new Funds(1500, 5, 0, 5, 0, 0, 0));
        add(DWELLING_UPGRADE3, RaceKind.WZRD, new Funds(1500, 0, 5, 0, 0, 0, 0));
        add(DWELLING_MONSTER4, RaceKind.WZRD, new Funds(3000, 5, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER5, RaceKind.WZRD, new Funds(3500, 5, 5, 5, 5, 5, 5));
        add(DWELLING_UPGRADE5, RaceKind.WZRD, new Funds(3000, 5, 0, 5, 0, 0, 0));
        add(DWELLING_MONSTER6, RaceKind.WZRD, new Funds(7500, 5, 0, 5, 0, 0, 20));
        add(DWELLING_UPGRADE6, RaceKind.WZRD, new Funds(16500, 5, 0, 5, 0, 0, 20));

        add(DWELLING_MONSTER1, RaceKind.NECR, new Funds(400, 0, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER2, RaceKind.NECR, new Funds(1000, 0, 0, 0, 0, 0, 0));
        add(DWELLING_UPGRADE2, RaceKind.NECR, new Funds(1000, 0, 0, 0, 0, 0, 0));
        add(DWELLING_MONSTER3, RaceKind.NECR, new Funds(1500, 0, 0, 10, 0, 0, 0));
        add(DWELLING_UPGRADE3, RaceKind.NECR, new Funds(1500, 0, 0, 5, 0, 0, 0));
        add(DWELLING_MONSTER4, RaceKind.NECR, new Funds(3000, 10, 0, 0, 0, 0, 0));
        add(DWELLING_UPGRADE4, RaceKind.NECR, new Funds(3500, 5, 10, 0, 0, 5, 0));
        add(DWELLING_MONSTER5, RaceKind.NECR, new Funds(4000, 10, 0, 0, 10, 0, 0));
        add(DWELLING_UPGRADE5, RaceKind.NECR, new Funds(2500, 0, 0, 5, 5, 0, 0));
        add(DWELLING_MONSTER6, RaceKind.NECR, new Funds(12000, 10, 10, 10, 5, 5, 5));
    }
}
