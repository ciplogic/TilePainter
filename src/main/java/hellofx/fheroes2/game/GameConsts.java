package hellofx.fheroes2.game;

import hellofx.fheroes2.resource.Funds;

public class GameConsts {
    public static final int TILEWIDTH = 32;

    public static final int MP2OFFSETDATA = 428;
    public static final int SIZEOFMP2TILE = 20;
    public static final int SIZEOFMP2ADDON = 15;
    public static final int SIZEOFMP2CASTLE = 0x46;
    public static final int SIZEOFMP2HEROES = 0x4c;

    public static final int SIZEOFMP2SIGN = 0x0a;
    public static final int SIZEOFMP2RUMOR = 0x09;
    public static final int SIZEOFMP2EVENT = 0x32;
    public static final int SIZEOFMP2RIDDLE = 0x8a;

    // hardcore defines: kingdom
    public static final int KINGDOMMAX = 6;

    // hardcore defines: world
    public static final int MAXCASTLES = 72;
    public static final int DAYOFWEEK = 7;
    public static final int WEEKOFMONTH = 4;

    // hardcore defines: castle
    public static final int CASTLEMAXMONSTER = 6;

    // hardcore defines: heroes
    public static final int HEROESMAXARTIFACT = 14;
    public static final int HEROESMAXSKILL = 8;
    public static final int HEROESMAXCOUNT = 71;

    // hardcore defines: skill
    public static final int MAXPRIMARYSKILL = 4;
    public static final int MAXSECONDARYSKILL = 14;

    // hardcore defines: army
    public static final int ARMYMAXTROOPS = 5;

    // hardcore defines: interface
    public static final int RADARWIDTH = 144;
    public static final int BORDERWIDTH = 16;

    // ai/hero speed
    public static final int DEFAULT_SPEED_DELAY = 5;

    /* town, castle, heroes, artifact_telescope, object_observation_tower, object_magi_eyes */
    public static final int[] overview_distance = {4, 5, 4, 1, 10, 9, 8};
    public static final int gameover_lost_days = 7;
    // kingdom
    public static final Funds[] kingdom_starting_resource = {
            new Funds(10000, 30, 10, 30, 10, 10, 10),
            new Funds(7500, 20, 5, 20, 5, 5, 5),
            new Funds(5000, 10, 2, 10, 2, 2, 2),
            new Funds(2500, 5, 0, 5, 0, 0, 0),
            new Funds(0, 0, 0, 0, 0, 0, 0),
            // ai resource
            new Funds(10000, 30, 10, 30, 10, 10, 10)
    };
    // castle
    public static final int castle_grown_well = 2;
    public static final int castle_grown_wel2 = 8;
    public static final int castle_grown_week_of = 5;
    public static final int castle_grown_month_of = 100;
    public static final int[] mageguild_restore_spell_points_day = {20, 40, 60, 80, 100};
    // heroes
    public static final int heroes_spell_points_day = 1;
    // spells
    public static final int spell_dd_distance = 0;
    public static final int spell_dd_sp = 0;
    public static final int spell_dd_hp = 0;
    // monsters
    public static final float monster_upgrade_ratio = 1.0f;
    // visit objects mod:	OBJ_BUOY, OBJ_OASIS, OBJ_WATERINGHOLE, OBJ_TEMPLE, OBJ_GRAVEYARD, OBJ_DERELICTSHIP,
    //			        OBJ_SHIPWRECK, OBJ_MERMAID, OBJ_FAERIERING, OBJ_FOUNTAIN, OBJ_IDOL, OBJ_PYRAMID
    public static final int[] objects_mod = {1, 1, 1, 2, -1, -1, -1, 1, 1, 1, 1, -2};
    // world
    public static final int uniq = 0;
    public static int kingdom_max_heroes = 8;
    public static int whirlpool_lost_percent = 50;
}
