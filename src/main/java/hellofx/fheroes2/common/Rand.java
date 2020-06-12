package hellofx.fheroes2.common;

import hellofx.fheroes2.spell.Spell;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Rand {
    public static int Get(int min, int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }

    public static boolean GetBool() {
        return Get(1) != 0;
    }

    public static int Get(int max) {
        return Get(0, max);
    }

    public static int Get(IntArrayList values) {
        var index = Get(values.size() - 1);
        return values.getInt(index);
    }

    public static Spell Get(List<Spell> values) {
        if (values.size() == 0)
            return null;
        var index = Get(values.size() - 1);
        return values.get(index);
    }
}


