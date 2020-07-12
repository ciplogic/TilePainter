package hellofx.fheroes2.heroes;

import hellofx.fheroes2.game.GameStatic;
import hellofx.fheroes2.spell.Spell;

public class Primary {
    public int attack;
    public int defense;
    public int power;
    public int knowledge;

    public Spell GetInitialSpell(int race) {
        var skillStats = GameStatic.GetSkillStats(race);
        if (skillStats != null) return new Spell(skillStats.initial_spell);
        return new Spell(0);
    }

    public void LoadDefaults(int type, int race) {
        var ptr = GameStatic.GetSkillStats(race);

        if (ptr != null)
            switch (type) {
                case HeroType.CAPTAIN:
                    attack = ptr.captain_primary.attack;
                    defense = ptr.captain_primary.defense;
                    power = ptr.captain_primary.power;
                    knowledge = ptr.captain_primary.knowledge;
                    break;

                case HeroType.HEROES:
                    attack = ptr.initial_primary.attack;
                    defense = ptr.initial_primary.defense;
                    power = ptr.initial_primary.power;
                    knowledge = ptr.initial_primary.knowledge;
                    break;

                default:
                    break;
            }
    }
}
