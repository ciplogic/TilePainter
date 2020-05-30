package hellofx.fheroes2.game;

import hellofx.fheroes2.heroes.primary_t;
import hellofx.fheroes2.heroes.secondary_t;
import hellofx.fheroes2.heroes.stats_t;
import hellofx.fheroes2.kingdom.RaceKind;

import java.util.ArrayList;
import java.util.List;

public class GameStatic {
    public static int uniq = 0;
    public static List<stats_t> _stats = new ArrayList<>();

    static {
        setupStats();
    }

    private static void addStat(String id, primary_t captain_primary,
                                primary_t initial_primary,
                                int initial_book,
                                int initial_spell,
                                secondary_t initial_secondary,
                                int over_level,
                                primary_t mature_primary_under,
                                primary_t mature_primary_over,
                                secondary_t mature_secondary) {
        stats_t item = new stats_t(id, captain_primary, initial_primary, initial_book, initial_spell, initial_secondary, over_level, mature_primary_under, mature_primary_over, mature_secondary);
        _stats.add(item);
    }

    private static void setupStats() {
        addStat(
                "knight", new primary_t(1, 1, 1, 1), new primary_t(2, 2, 1, 1), 0, 0, new secondary_t(0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0), 10,
                new primary_t(35, 45, 10, 10), new primary_t(25, 25, 25, 25), new secondary_t(3, 4, 3, 1, 3, 5, 3, 1, 0, 2, 0, 3, 2, 2)
        );
        addStat(
                "barbarian", new primary_t(1, 1, 1, 1), new primary_t(3, 1, 1, 1), 0, 0, new secondary_t(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0), 10,
                new primary_t(55, 35, 5, 5), new primary_t(25, 25, 25, 25), new secondary_t(3, 3, 2, 0, 1, 3, 3, 2, 1, 3, 0, 5, 4, 1)
        );
        addStat(
                "sorceress", new primary_t(0, 0, 2, 2), new primary_t(0, 1, 2, 2), 1, 15, new secondary_t(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1), 10,
                new primary_t(20, 15, 32, 33), new primary_t(25, 20, 25, 30), new secondary_t(3, 2, 2, 2, 2, 0, 2, 5, 3, 3, 0, 2, 1, 4)
        );
        addStat(
                "warlock",
                new primary_t(0, 0, 2, 2), new primary_t(0, 0, 3, 2), 1, 19, new secondary_t(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1), 10,
                new primary_t(15, 15, 40, 30), new primary_t(20, 20, 30, 30), new secondary_t(0, 1, 2, 4, 3, 2, 2, 1, 3, 2, 1, 2, 3, 5)
        );
        addStat(
                "wizard",
                new primary_t(0, 0, 2, 2), new primary_t(0, 0, 2, 3), 1, 17,
                new secondary_t(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1), 10,
                new primary_t(15, 15, 35, 35),
                new primary_t(20, 20, 30, 30), new secondary_t(2, 0, 2, 3, 3, 2, 2, 2, 4, 2, 0, 2, 2, 5)
        );
        addStat(
                "necromancer",
                new primary_t(0, 0, 2, 2),
                new primary_t(1, 0, 2, 2), 1, 10,
                new secondary_t(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1), 10,
                new primary_t(20, 20, 30, 30), new primary_t(25, 25, 25, 25), new secondary_t(1, 2, 3, 3, 2, 0, 2, 1, 3, 2, 5, 3, 0, 4)
        );
        addStat(
                null, new primary_t(0, 0, 0, 0),
                new primary_t(0, 0, 0, 0), 0, 0,
                new secondary_t(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), 10,
                new primary_t(0, 0, 0, 0),
                new primary_t(0, 0, 0, 0), new secondary_t(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        );
    }

    public static stats_t GetSkillStats(int race) {
        switch (race) {
            case RaceKind.KNGT:
                return _stats.get(0);
            case RaceKind.BARB:
                return _stats.get(1);
            case RaceKind.SORC:
                return _stats.get(2);
            case RaceKind.WRLK:
                return _stats.get(3);
            case RaceKind.WZRD:
                return _stats.get(4);
            case RaceKind.NECR:
                return _stats.get(5);
            default:
                break;
        }

        return null;
    }
}
