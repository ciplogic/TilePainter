package hellofx.fheroes2.heroes;

import hellofx.fheroes2.game.GameConsts;
import hellofx.fheroes2.game.GameStatic;
import hellofx.fheroes2.kingdom.RaceKind;

import java.util.ArrayList;
import java.util.List;

import static hellofx.common.Utilities.find_if;

public class SecSkills {
    public List<Secondary> _items = new ArrayList<>();

    public SecSkills(int race) {

        if ((race & RaceKind.ALL) != 0) {
            stats_t ptr = GameStatic.GetSkillStats(race);

            if (ptr != null) {
                if (ptr.initial_secondary.archery != 0)
                    AddSkill(new Secondary(SkillT.ARCHERY, ptr.initial_secondary.archery));
                if (ptr.initial_secondary.ballistics != 0)
                    AddSkill(new Secondary(SkillT.BALLISTICS, ptr.initial_secondary.ballistics));
                if (ptr.initial_secondary.diplomacy != 0)
                    AddSkill(new Secondary(SkillT.DIPLOMACY, ptr.initial_secondary.diplomacy));
                if (ptr.initial_secondary.eagleeye != 0)
                    AddSkill(new Secondary(SkillT.EAGLEEYE, ptr.initial_secondary.eagleeye));
                if (ptr.initial_secondary.estates != 0)
                    AddSkill(new Secondary(SkillT.ESTATES, ptr.initial_secondary.estates));
                if (ptr.initial_secondary.leadership != 0)
                    AddSkill(new Secondary(SkillT.LEADERSHIP, ptr.initial_secondary.leadership));
                if (ptr.initial_secondary.logistics != 0)
                    AddSkill(new Secondary(SkillT.LOGISTICS, ptr.initial_secondary.logistics));
                if (ptr.initial_secondary.luck != 0) AddSkill(new Secondary(SkillT.LUCK, ptr.initial_secondary.luck));
                if (ptr.initial_secondary.mysticism != 0)
                    AddSkill(new Secondary(SkillT.MYSTICISM, ptr.initial_secondary.mysticism));
                if (ptr.initial_secondary.navigation != 0)
                    AddSkill(new Secondary(SkillT.NAVIGATION, ptr.initial_secondary.navigation));
                if (ptr.initial_secondary.necromancy != 0)
                    AddSkill(new Secondary(SkillT.NECROMANCY, ptr.initial_secondary.necromancy));
                if (ptr.initial_secondary.pathfinding != 0)
                    AddSkill(new Secondary(SkillT.PATHFINDING, ptr.initial_secondary.pathfinding));
                if (ptr.initial_secondary.scouting != 0)
                    AddSkill(new Secondary(SkillT.SCOUTING, ptr.initial_secondary.scouting));
                if (ptr.initial_secondary.wisdom != 0)
                    AddSkill(new Secondary(SkillT.WISDOM, ptr.initial_secondary.wisdom));
            }
        }
    }

    public SecSkills() {
    }

    public void AddSkill(Secondary skill) {
        //TODO: look for already existing skill
        if (skill.isValid()) {
            var itSec = find_if(_items,
                    (Secondary it) -> {
                        return it.isSkill(skill.Skill());
                    });
            if (itSec != null)
                itSec.SetLevel(skill.Level());
            else {
                var itNotValid = find_if(_items,
                        (Secondary it) -> {
                            return !it.isValid();
                        });
                if (itNotValid != null)
                    itNotValid.Set(skill);
                else if (_items.size() < GameConsts.HEROESMAXSKILL)
                    _items.add(skill);
            }
        }

        _items.add(skill);
    }

    public int GetValues(int skill) {
        //TODO
        return 0;
    }
}
