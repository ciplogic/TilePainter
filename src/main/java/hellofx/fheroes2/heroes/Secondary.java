package hellofx.fheroes2.heroes;

import hellofx.fheroes2.common.H2IntPair;
import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.game.GameStatic;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

//
public class Secondary extends H2IntPair {
    public Secondary(int skill, byte levelB) {
        int level = toByte(levelB);
        SetSkill(skill);
        SetLevel(level);
    }

    public Secondary() {
        //TODO
    }

    public static int RandForWitchHut() {
        var sec = GameStatic.GetSkillForWitchHut();
        IntArrayList v = new IntArrayList();


        if (sec.archery != 0) v.add(SkillT.ARCHERY);
        if (sec.ballistics != 0) v.add(SkillT.BALLISTICS);
        if (sec.diplomacy != 0) v.add(SkillT.DIPLOMACY);
        if (sec.eagleeye != 0) v.add(SkillT.EAGLEEYE);
        if (sec.estates != 0) v.add(SkillT.ESTATES);
        if (sec.leadership != 0) v.add(SkillT.LEADERSHIP);
        if (sec.logistics != 0) v.add(SkillT.LOGISTICS);
        if (sec.luck != 0) v.add(SkillT.LUCK);
        if (sec.mysticism != 0) v.add(SkillT.MYSTICISM);
        if (sec.navigation != 0) v.add(SkillT.NAVIGATION);
        if (sec.necromancy != 0) v.add(SkillT.NECROMANCY);
        if (sec.pathfinding != 0) v.add(SkillT.PATHFINDING);
        if (sec.scouting != 0) v.add(SkillT.SCOUTING);
        if (sec.wisdom != 0) v.add(SkillT.WISDOM);

        return Rand.Get(v);
    }

    public void SetSkill(int skill) {
        first = skill <= SkillT.ESTATES ? skill : SkillT.UNKNOWN;
    }

    public void SetLevel(int level) {

        second = level <= SkillLevel.EXPERT ? level : SkillLevel.NONE;
    }

    public boolean isValid() {
        return Skill() != SkillT.UNKNOWN && Level() != SkillLevel.NONE;
    }

    public int Level() {
        return second;
    }

    public int Skill() {
        return first;
    }

    public boolean isSkill(int skill) {
        return skill == first;
    }

    public boolean isLevel(int level) {
        return level == second;
    }

    public void Set(Secondary skill) {
        first = skill.first;
        second = skill.second;
    }

    public void NextLevel() {
        switch (second) {
            case SkillLevel.NONE:
                second = SkillLevel.BASIC;
                break;
            case SkillLevel.BASIC:
                second = SkillLevel.ADVANCED;
                break;
            case SkillLevel.ADVANCED:
                second = SkillLevel.EXPERT;
                break;
            default:
                break;
        }
    }

    public int GetValues() {
        values_t val = GameStatic.GetSkillValues(Skill());
        if (val != null)
            switch (Level()) {
                case SkillLevel.BASIC:
                    return val.values.basic;
                case SkillLevel.ADVANCED:
                    return val.values.advanced;
                case SkillLevel.EXPERT:
                    return val.values.expert;
                default:
                    break;
            }

        return 0;
    }
}
