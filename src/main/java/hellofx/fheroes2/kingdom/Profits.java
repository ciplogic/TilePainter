package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.resource.Funds;

import java.util.ArrayList;
import java.util.List;

public class Profits {
    public final static Profits Instance = new Profits();
    List<profitstats_t> _profits = new ArrayList<>();

    private Profits() {
        setup();
    }

    void setup() {
        add("castle", new Funds(1000, 0, 0, 0, 0, 0, 0));
        add("town", new Funds(250, 0, 0, 0, 0, 0, 0));
        add("statue", new Funds(250, 0, 0, 0, 0, 0, 0));
        add("dungeon", new Funds(500, 0, 0, 0, 0, 0, 0));

        add("sawmill", new Funds(0, 2, 0, 0, 0, 0, 0));
        add("alchemylab", new Funds(0, 0, 1, 0, 0, 0, 0));
        add("mine_ore", new Funds(0, 0, 0, 2, 0, 0, 0));
        add("mine_sulfur", new Funds(0, 0, 0, 0, 1, 0, 0));
        add("mine_crystal", new Funds(0, 0, 0, 0, 0, 1, 0));
        add("mine_gems", new Funds(0, 0, 0, 0, 0, 0, 1));
        add("mine_gold", new Funds(1000, 0, 0, 0, 0, 0, 0));

        add("ultimate_golden_goose", new Funds(10000, 0, 0, 0, 0, 0, 0));
        add("tax_lien", new Funds(250, 0, 0, 0, 0, 0, 0));
        add("endless_sack_gold", new Funds(1000, 0, 0, 0, 0, 0, 0));
        add("endless_bag_gold", new Funds(750, 0, 0, 0, 0, 0, 0));
        add("endless_purse_gold", new Funds(500, 0, 0, 0, 0, 0, 0));
        add("endless_cord_wood", new Funds(0, 1, 0, 0, 0, 0, 0));
        add("endless_vial_mercury", new Funds(0, 0, 1, 0, 0, 0, 0));
        add("endless_cart_ore", new Funds(0, 0, 0, 1, 0, 0, 0));
        add("endless_pouch_sulfur", new Funds(0, 0, 0, 0, 1, 0, 0));
        add("endless_pouch_crystal", new Funds(0, 0, 0, 0, 0, 1, 0));
        add("endless_pouch_gems", new Funds(0, 0, 0, 0, 0, 0, 1));
    }

    private void add(String id, Funds cost) {
        var item = new profitstats_t(id, cost);
        _profits.add(item);
    }
}
