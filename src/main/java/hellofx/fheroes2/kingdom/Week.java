package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.common.H2IntPair;
import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.monster.MonsterKind;
import hellofx.fheroes2.system.Settings;

public class Week extends H2IntPair {
    public Week(int type, int mons) {
        first = type;
        second = mons;
    }

    public Week(int type) {
        first = type;
        second = MonsterKind.UNKNOWN;
    }

    public Week() {
        first = WeekKind.UNNAMED;
        second = MonsterKind.UNKNOWN;
    }

    public static int WeekRand() {
        var world = World.Instance;
        return 0 == (world.CountWeek() + 1) % 3 && !Settings.Get().ExtWorldBanWeekOf() ? WeekKind.MONSTERS : Rand.Get(WeekKind.ANT, WeekKind.CONDOR);

    }

    public int GetType() {
        return first;
    }

    public int GetMonster() {
        return second;
    }
}
