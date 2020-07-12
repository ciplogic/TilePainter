package hellofx.fheroes2.system;

import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.H2Size;
import hellofx.fheroes2.common.ListDirs;
import hellofx.fheroes2.maps.FileInfo;

public class Settings {
    public static Settings Instance;

    static {
        Instance = new Settings();
    }

    public static Settings Get() {
        return Instance;
    }

    private Settings() {
        ExtSetModes(SettingsKind.GAME_SHOW_SDL_LOGO);
        ExtSetModes(SettingsKind.GAME_AUTOSAVE_ON);
        opt_global.SetModes(GlobalSettingsKind.GLOBAL_SHOWRADAR);
        opt_global.SetModes(GlobalSettingsKind.GLOBAL_SHOWICONS);
        opt_global.SetModes(GlobalSettingsKind.GLOBAL_SHOWBUTTONS);
        opt_global.SetModes(GlobalSettingsKind.GLOBAL_SHOWSTATUS);
    }

    BitModes opt_global = new BitModes();
    BitModes opt_game = new BitModes();
    BitModes opt_battle = new BitModes();
    BitModes opt_world = new BitModes();
    BitModes opt_addons = new BitModes();

    int debug;
    H2Size video_mode = new H2Size(3440, 1440);
    boolean fullScreen;
    int game_difficulty;

    String path_program;
    String data_params;
    ListDirs maps_params;

    String font_normal = "";
    String font_small = "";
    String force_lang = "";
    String maps_charset = "";
    int size_normal;
    int size_small;

    boolean _isQuickCombat;
    boolean _isUiHeroesBar;


    FileInfo current_maps_file = new FileInfo();

    int sound_volume;
    int music_volume;
    int heroes_speed;
    int ai_speed;
    int scroll_speed;
    int battle_speed;
    int blit_speed;

    int game_type;
    int preferably_count_players;

    String video_driver = "";

    int port;
    int memory_limit;

    H2Point pos_radr = new H2Point();
    H2Point pos_bttn = new H2Point();
    H2Point pos_icon = new H2Point();
    H2Point pos_stat = new H2Point();

    Players players = new Players();

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

    public Players GetPlayers() {
        return players;
    }

    public boolean GameType(int f) {
        return (game_type & f) != 0;
    }

    public boolean ExtWorldBanWeekOf() {
        //TODO
        return false;
    }

    public boolean GameStartWithHeroes() {
        //TODO
        return false;
    }

    public boolean ExtCastleAllowGuardians() {
        //TODO
        return false;
    }

    public boolean ExtWorldUseUniqueArtifactsML() {
        return ExtModes(SettingsKind.WORLD_USE_UNIQUE_ARTIFACTS_ML);
    }

    private boolean ExtModes(int f) {
        var mask = 0x0FFFFFFF;
        switch (f >> 28) {
            case 0x01:
                return opt_game.Modes(f & mask);
            case 0x02:
                return opt_world.Modes(f & mask);
            case 0x03:
                return opt_addons.Modes(f & mask);
            case 0x04:
                return opt_battle.Modes(f & mask);
            default:
                break;
        }
        return false;
    }

    void ExtSetModes(int f) {
        var mask = 0x0FFFFFFF;
        switch (f >> 28) {
            case 0x01:
                opt_game.SetModes(f & mask);
                break;
            case 0x02:
                opt_world.SetModes(f & mask);
                break;
            case 0x03:
                opt_addons.SetModes(f & mask);
                break;
            case 0x04:
                opt_battle.SetModes(f & mask);
                break;
            default:
                break;
        }
    }

    void ExtResetModes(int f) {
        var mask = 0x0FFFFFFF;
        switch (f >> 28) {
            case 0x01:
                opt_game.ResetModes(f & mask);
                break;
            case 0x02:
                opt_world.ResetModes(f & mask);
                break;
            case 0x03:
                opt_addons.ResetModes(f & mask);
                break;
            case 0x04:
                opt_battle.ResetModes(f & mask);
                break;
            default:
                break;
        }
    }

    public boolean ExtWorldUseUniqueArtifactsRS() {
        return ExtModes(SettingsKind.WORLD_USE_UNIQUE_ARTIFACTS_RS);
    }

    public boolean ExtWorldUseUniqueArtifactsPS() {
        return ExtModes(SettingsKind.WORLD_USE_UNIQUE_ARTIFACTS_PS);
    }

    public boolean ExtWorldUseUniqueArtifactsSS() {
        return ExtModes(SettingsKind.WORLD_USE_UNIQUE_ARTIFACTS_SS);
    }

    public int CurrentColor() {
        //TODO
        return 0;
    }

    public int ConditionWins() {
        return current_maps_file.ConditionWins();
    }

    public H2Point WinsMapsPositionObject() {
        return current_maps_file.WinsMapsPositionObject();
    }

    public H2Point LossMapsPositionObject() {
        return current_maps_file.LossMapsPositionObject();
    }

    public int ConditionLoss() {
        return current_maps_file.ConditionLoss();
    }

    public boolean ExtHeroRememberPointsForRetreating() {
        //TODO
        return false;
    }
}
