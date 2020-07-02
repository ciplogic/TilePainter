package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.castle.BuildingInfo;
import hellofx.fheroes2.resource.Funds;
import hellofx.fheroes2.system.Settings;

import java.util.ArrayList;
import java.util.List;

public class PaymentConditions {
    private static final PaymentConditions StaticInstance;

    static {
        StaticInstance = new PaymentConditions();

    }

    List<paymentstats_t> _payments = new ArrayList<>();

    private PaymentConditions() {
        setup();
    }

    static paymentstats_t findById(String id) {
        return StaticInstance._payments.stream()
                .filter(i -> i.id.equals(id))
                .findFirst()
                .orElse(null);
    }

    public static PaymentConditions Get() {
        return StaticInstance;
    }

    public static Funds RecruitHero(int level) {
        var ptr = findById("recruit_hero");
        Funds result = new Funds();
        if (ptr != null) {
            result = ptr.cost.copy();
        }

        // level price
        if (Settings.Get().ExtHeroRecruitCostDependedFromLevel()) {
            ptr = findById("recruit_level");
            if (ptr != null && 1 < level) {
                if (0 != ptr.cost.gold) result.gold += (level - 1) * ptr.cost.gold;
                if (0 != ptr.cost.wood) result.wood += (level - 1) * ptr.cost.wood;
                if (0 != ptr.cost.mercury) result.mercury += (level - 1) * ptr.cost.mercury;
                if (0 != ptr.cost.ore) result.ore += (level - 1) * ptr.cost.ore;
                if (0 != ptr.cost.sulfur) result.sulfur += (level - 1) * ptr.cost.sulfur;
                if (0 != ptr.cost.crystal) result.crystal += (level - 1) * ptr.cost.crystal;
                if (0 != ptr.cost.gems) result.gems += (level - 1) * ptr.cost.gems;
            }
        }

        return result;
    }

    public static Funds BuyBuilding(int race, int build) {
        return BuildingInfo.GetCost(build, race);
    }

    private void setup() {
        add("buy_boat", new Funds(1000, 10, 0, 0, 0, 0, 0));
        add("buy_spell_book", new Funds(500, 0, 0, 0, 0, 0, 0));
        add("buy_spell_book_from_shrine1", new Funds(1250, 0, 0, 0, 0, 0, 0));
        add("buy_spell_book_from_shrine2", new Funds(1000, 0, 0, 0, 0, 0, 0));
        add("buy_spell_book_from_shrine3", new Funds(750, 0, 0, 0, 0, 0, 0));
        add("recruit_hero", new Funds(2500, 0, 0, 0, 0, 0, 0));
        add("recruit_level", new Funds(500, 0, 0, 0, 0, 0, 0));
        add("alchemist_payment", new Funds(750, 0, 0, 0, 0, 0, 0));

        add("", new Funds(0, 0, 0, 0, 0, 0, 0));
    }

    private void add(String name, Funds cost) {
        var item = new paymentstats_t();
        item.id = name;
        item.cost = cost;
        _payments.add(item);
    }
}
