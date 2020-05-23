package hellofx.fheroes2.spell;

import static hellofx.fheroes2.spell.SpellType.NONE;
import static hellofx.fheroes2.spell.SpellType.STONE;

public class Spell {
    public int id;

    public Spell(int s) {
        id = s > STONE ? NONE : s;
    }

}
