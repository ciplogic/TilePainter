package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.castle.Castle;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.game.GameConsts;
import hellofx.fheroes2.game.GameStatic;
import hellofx.fheroes2.heroes.HeroFlags;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.heroes.HeroesKind;
import hellofx.fheroes2.maps.IndexObject;
import hellofx.fheroes2.resource.Funds;
import hellofx.fheroes2.system.BitModes;
import hellofx.fheroes2.system.H2Control;
import hellofx.fheroes2.system.Players;
import hellofx.fheroes2.system.Settings;

import java.util.ArrayList;
import java.util.List;

public class Kingdom extends H2Control {
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

    KingdomHeroes heroes_cond_loss = new KingdomHeroes();

    public boolean AllowRecruitHero(boolean check_payment, int level) {
        //TODO
        return heroes._items.size() < GetMaxHeroes()
                &&
                (!check_payment || AllowPayment(PaymentConditions.RecruitHero(level)));
    }

    public boolean AllowPayment(Funds funds) {
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

    public KingdomCastles GetCastles() {
        return castles;
    }

    public void clear() {
        //TODO
    }

    public void Init(int clr) {
        clear();
        color = clr;

        if (0 != (H2Color.ALL & color)) {
            UpdateStartingResource();
        }
    }

    private void UpdateStartingResource() {
        resource = GameStatic.GetKingdomStartingResource(isControlAI() ? 5 : Settings.Get().GameDifficulty());

    }

    @Override
    public int GetControl() {
        return Players.GetPlayerControl(color);
    }

    public void OddFundsResource(Funds payment) {
        resource = resource.substract(payment);
    }

    public int GetLostTownDays() {
        return lost_town_days;
    }

    public void ApplyPlayWithStartingHero() {
        if (isPlay() && castles._items.size() != 0) {
            // get first castle
            Castle first = castles.GetFirstCastle();
            if (null == first) first = castles._items.get(0);

            // check manual set hero (castle position + point(0, 1))?
            H2Point cp = first.GetCenter();
            var world = World.Instance;
            Heroes hero = world.GetTiles(cp.x, cp.y + 1).GetHeroes();

            // and move manual set hero to castle
            if (hero != null && hero.color.GetColor() == GetColor()) {
                boolean patrol = hero.bitModes.Modes(HeroFlags.PATROL);
                hero.SetFreeman(0);
                hero.Recruit(first);

                if (patrol) {
                    hero.bitModes.SetModes(HeroFlags.PATROL);
                    hero.SetCenterPatrol(cp);
                }
            } else if (Settings.Get().GameStartWithHeroes()) {
                hero = world.GetFreemanHeroes(first.GetRace());
                if (hero != null && AllowRecruitHero(false, 0)) hero.Recruit(first);
            }
        }
    }

    private int GetColor() {
        return color;
    }

    public boolean isPlay() {
        //TODO
        return false;
    }

    public int GetCountMarketplace() {
        //TODO
        return 0;
    }

    public void SetLastLostHero(Heroes hero) {
        lost_hero.first = hero.GetID();
        var world = World.Instance;
        lost_hero.second = world.CountDay();
    }

    public Recruits GetRecruits() {
        var world = World.Instance;
        // update hero1
        if (HeroesKind.UNKNOWN == recruits.GetID1() || (recruits.GetHero1() != null && !recruits.GetHero1().isFreeman()))
            recruits.SetHero1(world.GetFreemanHeroes(GetRace()));

        // update hero2
        if (HeroesKind.UNKNOWN == recruits.GetID2() || (recruits.GetHero2() != null && !recruits.GetHero2().isFreeman()))
            recruits.SetHero2(world.GetFreemanHeroes());

        if (recruits.GetID1() == recruits.GetID2()) world.UpdateRecruits(recruits);

        return recruits;
    }

    public void RemoveHeroes(Heroes hero) {
        //TODO
    }
}
