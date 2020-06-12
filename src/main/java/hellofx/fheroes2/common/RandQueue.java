package hellofx.fheroes2.common;

import java.util.ArrayList;

public class RandQueue extends ArrayList<H2IntPair> {

    public void Push(int k, int v) {
        var item = new H2IntPair(k, v);
    }

    public int Get() {
        // get max

        var max = 0;
        for (var it : this)
            max += it.second;

        // set weight (from 100)

        for (var it : this) {
            it.second = (100 * it.second) / max;
        }

        // get max
        max = 0;

        for (var it : this) {
            max += it.second;
        }

        var rand = Rand.Get(max);
        var amount = 0;


        for (var it : this) {
            amount += it.second;
            if (rand <= amount) return it.first;
        }

        System.out.println("Warning: weight not found, return 0");
        return 0;
    }
}
