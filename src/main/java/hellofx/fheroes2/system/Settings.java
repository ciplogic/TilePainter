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
}
