package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.game.GameConsts;
import hellofx.fheroes2.maps.IndexObject;
import hellofx.fheroes2.resource.Funds;
import hellofx.fheroes2.system.BitModes;

import java.util.ArrayList;
import java.util.List;

public class Kingdom {
    public BitModes bitModes = new BitModes();

    int color;
    Funds resource = new Funds();

    int lost_town_days;

    KingdomCastles castles = new KingdomCastles();
    KingdomHeroes heroes = new KingdomHeroes();

    Recruits recruits = new Recruits();
    LastLoseHero lost_hero;

    List<IndexObject> visit_object = new ArrayList<>();

    Puzzle puzzle_maps = new Puzzle();
    int visited_tents_colors;

    KingdomHeroes heroes_cond_loss;

    public boolean AllowRecruitHero(boolean check_payment, int level) {
        //TODO
        return (heroes._items.size() < GetMaxHeroes())
                &&
                (!check_payment || AllowPayment(PaymentConditions.RecruitHero(level)));
    }

    private boolean AllowPayment(Funds funds) {
        return
                (resource.wood >= funds.wood || 0 == funds.wood) &&
                        (resource.mercury >= funds.mercury || 0 == funds.mercury) &&
                        (resource.ore >= funds.ore || 0 == funds.ore) &&
                        (resource.sulfur >= funds.sulfur || 0 == funds.sulfur) &&
                        (resource.crystal >= funds.crystal || 0 == funds.crystal) &&
                        (resource.gems >= funds.gems || 0 == funds.gems) &&
                        (resource.gold >= funds.gold || 0 == funds.gold);
    }

    private int GetMaxHeroes() {
        return GameConsts.kingdom_max_heroes;
    }

    public int GetRace() {
        return 0;
    }
}
