package hellofx.fheroes2.heroes;

import hellofx.fheroes2.common.H2IntPair;

//
public class Secondary extends H2IntPair {
    public void SetSkill(int skill) {
        first = skill <= SkillT.ESTATES ? skill : SkillT.UNKNOWN;
    }

    public void SetLevel(int level) {

        second = level <= SkillLevel.EXPERT ? level : SkillLevel.NONE;
    }

    public boolean isValid() {
        return Skill() != SkillT.UNKNOWN && Level() != SkillLevel.NONE;
    }

    private int Level() {
        return second;
    }

    private int Skill() {
        return first;
    }
}
