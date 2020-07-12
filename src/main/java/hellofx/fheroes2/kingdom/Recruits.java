package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.common.H2IntPair;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.heroes.HeroesKind;

public class Recruits extends H2IntPair {
    public int GetID1() {
        return first;
    }

    public int GetID2() {
        return second;
    }

    public Heroes GetHero1() {
        var world = World.Instance;
        return world.GetHeroes(first);
    }

    public Heroes GetHero2() {
        var world = World.Instance;
        return world.GetHeroes(second);
    }

    public void SetHero1(Heroes hero) {
        first = hero != null ? hero.hid : HeroesKind.UNKNOWN;
    }

    public void SetHero2(Heroes hero) {
        second = hero != null ? hero.hid : HeroesKind.UNKNOWN;
    }
}
