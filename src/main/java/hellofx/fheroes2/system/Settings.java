package hellofx.fheroes2.system;

public class Settings {
    public static Settings Instance;

    static {
        Instance = new Settings();
    }

    public static Settings Get() {
        return Instance;
    }

    public int GameDifficulty() {
        //TODO
        return 0;
    }

    public boolean PriceLoyaltyVersion() {
        return false;
    }

    public boolean IS_DEVEL() {
        return false;
    }

    public boolean ExtWorldExtObjectsCaptured() {
        //TODO
        return false;
    }

    public boolean ExtWorldOnlyFirstMonsterAttack() {
        //TODO
        return false;
    }

    public boolean ExtHeroRecruitCostDependedFromLevel() {
        //TODO
        return false;
    }

    public boolean ExtCastleAllowRecruitSpecialHeroes() {
        //TODO
        return false;
    }

    public boolean ExtWorldStartHeroLossCond4Humans() {
        //TODO
        return false;
    }

    public boolean ExtWorldNoRequirementsForArtifacts() {
//TODO
        return false;
    }

    public boolean ExtWorldDisableBarrowMounds() {
//TODO
        return false;
    }

    public boolean ExtWorldAbandonedMineRandom() {
//TODO
        return false;
    }
}
