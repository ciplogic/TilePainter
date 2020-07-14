package hellofx.fheroes2.ai;

import hellofx.fheroes2.heroes.Heroes;

public class AI {
    public static void HeroesPostLoad(Heroes hero) {
        hero.SetModes(AiModes.HEROES_HUNTER);
    }
}
