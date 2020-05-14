package hellofx.fheroes2.engine;

import java.util.concurrent.ThreadLocalRandom;

public class Rand {
    public static int Get(int min, int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }

    public static int Get(int max) {
        return Get(0, max);
    }

}
