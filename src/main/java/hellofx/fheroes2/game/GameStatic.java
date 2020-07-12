package hellofx.fheroes2.game;

import hellofx.fheroes2.heroes.*;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.resource.Funds;

import java.util.ArrayList;
import java.util.List;

import static hellofx.fheroes2.game.GameConsts.monster_upgrade_ratio;

public class GameStatic {
    public static int uniq = 0;
    public static List<stats_t> _stats = new ArrayList<>();
    public static List<values_t> _values = new ArrayList<>();

    static {
        setupStats();
        setupSkills();
    }

    private static void setupSkills() {
        addSkill("pathfinding", new level_t(25, 50, 100));
        addSkill("archery", new level_t(10, 25, 50));
        addSkill("logistics", new level_t(10, 20, 30));
        addSkill("scouting", new level_t(1, 3, 5));
        addSkill("diplomacy", new level_t(25, 50, 100));
        addSkill("navigation", new level_t(33, 66, 100));
        addSkill("leadership", new level_t(1, 2, 3));
        addSkill("wisdom", new level_t(3, 4, 5));
        addSkill("mysticism", new level_t(2, 3, 4));
        addSkill("luck", new level_t(1, 2, 3));
        addSkill("ballistics", new level_t(0, 0, 0));
        addSkill("eagleeye", new level_t(25, 40, 65));
        addSkill("necromancy", new level_t(10, 20, 30));
        addSkill("estates", new level_t(100, 250, 500));
    }

    private static void addSkill(String skillId, level_t levelT) {
        values_t item = new values_t();
        item.id = skillId;
        item.values = levelT;
        _values.add(item);
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

    static secondary_t _from_witch_hut = new secondary_t(

            /* archery */ 1, /* ballistics */ 1, /* diplomacy */ 1, /* eagleeye */ 1,
            /* estates */ 1, /* leadership */ 0, /* logistics */ 1, /* luck */ 1,
            /* mysticism */ 1, /* navigation */ 1, /* necromancy*/ 0, /* pathfinding */ 1,
            /* scouting */ 1, /* wisdom */ 1
    );

    public static secondary_t GetSkillForWitchHut() {
        return _from_witch_hut;
    }

    public static float GetMonsterUpgradeRatio() {
        return monster_upgrade_ratio;
    }

    public static values_t GetSkillValues(int type) {
        switch (type) {
            case SkillT.PATHFINDING:
                return _values.get(0);
            case SkillT.ARCHERY:
                return _values.get(1);
            case SkillT.LOGISTICS:
                return _values.get(2);
            case SkillT.SCOUTING:
                return _values.get(3);
            case SkillT.DIPLOMACY:
                return _values.get(4);
            case SkillT.NAVIGATION:
                return _values.get(5);
            case SkillT.LEADERSHIP:
                return _values.get(6);
            case SkillT.WISDOM:
                return _values.get(7);
            case SkillT.MYSTICISM:
                return _values.get(8);
            case SkillT.LUCK:
                return _values.get(9);
            case SkillT.BALLISTICS:
                return _values.get(10);
            case SkillT.EAGLEEYE:
                return _values.get(11);
            case SkillT.NECROMANCY:
                return _values.get(12);
            case SkillT.ESTATES:
                return _values.get(13);
            default:
                break;
        }
        return null;
    }

    public static int ObjectVisitedModifiers(int obj) {
        //TODO
        return 0;
    }

    private final static Funds[] kingdom_starting_resource = {
            new Funds(10000, 30, 10, 30, 10, 10, 10),
            new Funds(7500, 20, 5, 20, 5, 5, 5),
            new Funds(5000, 10, 2, 10, 2, 2, 2),
            new Funds(2500, 5, 0, 5, 0, 0, 0),
            new Funds(0, 0, 0, 0, 0, 0, 0),
            // ai resource
            new Funds(10000, 30, 10, 30, 10, 10, 10)
    };

    public static Funds GetKingdomStartingResource(int df) {
        switch (df) {
            case DifficultyEnum.EASY:
                return kingdom_starting_resource[0];
            case DifficultyEnum.NORMAL:
                return kingdom_starting_resource[1];
            case DifficultyEnum.HARD:
                return kingdom_starting_resource[2];
            case DifficultyEnum.EXPERT:
                return kingdom_starting_resource[3];
            case DifficultyEnum.IMPOSSIBLE:
                return kingdom_starting_resource[4];
        }

        return kingdom_starting_resource[5];
    }
}
