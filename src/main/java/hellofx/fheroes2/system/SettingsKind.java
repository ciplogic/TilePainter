package hellofx.fheroes2.system;

public class SettingsKind {
   public static final int GAME_AUTOSAVE_BEGIN_DAY = 0x10000010;
   public static final int GAME_REMEMBER_LAST_FOCUS = 0x10000020;
   public static final int GAME_SAVE_REWRITE_CONFIRM = 0x10000040;
   public static final int GAME_CASTLE_FLASH_BUILDING = 0x10000080;
   public static final int GAME_SHOW_SYSTEM_INFO = 0x10000100;
   public static final int GAME_AUTOSAVE_ON = 0x10000200;
   public static final int GAME_USE_FADE = 0x10000400;
   public static final int GAME_SHOW_SDL_LOGO = 0x10000800;
   public static final int GAME_EVIL_INTERFACE = 0x10001000;
   public static final int GAME_HIDE_INTERFACE = 0x10002000;
   public static final int GAME_ALSO_CONFIRM_AUTOSAVE = 0x10004000;
   //UNUSED			= 0x10008000;
   public static final int GAME_DYNAMIC_INTERFACE = 0x10010000;
   public static final int GAME_BATTLE_SHOW_GRID = 0x10020000;
   public static final int GAME_BATTLE_SHOW_MOUSE_SHADOW = 0x10040000;
   public static final int GAME_BATTLE_SHOW_MOVE_SHADOW = 0x10080000;
   public static final int GAME_BATTLE_SHOW_DAMAGE = 0x10100000;
   public static final int GAME_CONTINUE_AFTER_VICTORY = 0x10200000;
   public static final int GAME_QUICKCOMBAT_ON = 0x18000000;
   /* influence on game balance: save to savefile */
   public static final int WORLD_SHOW_VISITED_CONTENT = 0x20000001;
   public static final int WORLD_ABANDONED_MINE_RANDOM = 0x20000002;
   public static final int WORLD_SAVE_MONSTER_BATTLE = 0x20000004;
   public static final int WORLD_ALLOW_SET_GUARDIAN = 0x20000008;
   public static final int WORLD_NOREQ_FOR_ARTIFACTS = 0x20000010;
   public static final int WORLD_ARTIFACT_CRYSTAL_BALL = 0x20000020;
   public static final int WORLD_SCOUTING_EXTENDED = 0x20000040;
   public static final int WORLD_ONLY_FIRST_MONSTER_ATTACK = 0x20000080;
   public static final int WORLD_EYE_EAGLE_AS_SCHOLAR = 0x20000100;
   public static final int HEROES_BUY_BOOK_FROM_SHRINES = 0x20000200;
   public static final int WORLD_BAN_WEEKOF = 0x20000400;
   public static final int WORLD_BAN_PLAGUES = 0x20000800;
   public static final int UNIONS_ALLOW_HERO_MEETINGS = 0x20001000;
   public static final int UNIONS_ALLOW_CASTLE_VISITING = 0x20002000;
   //UNUSED			= 0x20004000;
   public static final int HEROES_AUTO_MOVE_BATTLE_DST = 0x20008000;
   public static final int WORLD_BAN_MONTHOF_MONSTERS = 0x20010000;
   public static final int HEROES_TRANSCRIBING_SCROLLS = 0x20020000;
   public static final int WORLD_NEW_VERSION_WEEKOF = 0x20040000;
   public static final int CASTLE_ALLOW_GUARDIANS = 0x20080000;
   public static final int CASTLE_ALLOW_BUY_FROM_WELL = 0x20100000;
   public static final int HEROES_LEARN_SPELLS_WITH_DAY = 0x20200000;
   public static final int HEROES_ALLOW_BANNED_SECSKILLS = 0x20400000;
   public static final int HEROES_COST_DEPENDED_FROM_LEVEL = 0x20800000;
   public static final int HEROES_REMEMBER_POINTS_RETREAT = 0x21000000;
   public static final int HEROES_SURRENDERING_GIVE_EXP = 0x22000000;
   public static final int HEROES_RECALCULATE_MOVEMENT = 0x24000000;
   public static final int HEROES_PATROL_ALLOW_PICKUP = 0x28000000;

   public static final int CASTLE_MAGEGUILD_POINTS_TURN = 0x30000001;
   public static final int WORLD_ARTSPRING_SEPARATELY_VISIT = 0x30000002;
   public static final int CASTLE_ALLOW_RECRUITS_SPECIAL = 0x30000004;
   public static final int WORLD_STARTHERO_LOSSCOND4HUMANS = 0x30000008;
   public static final int WORLD_1HERO_HIRED_EVERY_WEEK = 0x30000010;
   public static final int WORLD_DWELLING_ACCUMULATE_UNITS = 0x30000020;
   public static final int WORLD_GUARDIAN_TWO_DEFENSE = 0x30000040;
   public static final int HEROES_ARENA_ANY_SKILLS = 0x30000080;
   public static final int WORLD_USE_UNIQUE_ARTIFACTS_ML = 0x30000100;
   public static final int WORLD_USE_UNIQUE_ARTIFACTS_RS = 0x30000200;
   public static final int WORLD_USE_UNIQUE_ARTIFACTS_PS = 0x30000400;
   public static final int WORLD_USE_UNIQUE_ARTIFACTS_SS = 0x30000800;
   public static final int WORLD_DISABLE_BARROW_MOUNDS = 0x30001000;
   public static final int WORLD_EXT_OBJECTS_CAPTURED = 0x30004000;
   public static final int CASTLE_1HERO_HIRED_EVERY_WEEK = 0x30008000;

   public static final int BATTLE_ARCHMAGE_RESIST_BAD_SPELL = 0x40001000;
   public static final int BATTLE_MAGIC_TROOP_RESIST = 0x40002000;
   //UNUSED			= 0x40008000;
   public static final int BATTLE_SOFT_WAITING = 0x40010000;
   public static final int BATTLE_REVERSE_WAIT_ORDER = 0x40020000;
   public static final int BATTLE_MERGE_ARMIES = 0x40100000;
   public static final int BATTLE_SKIP_INCREASE_DEFENSE = 0x40200000;
   public static final int BATTLE_OBJECTS_ARCHERS_PENALTY = 0x42000000;

   public static final int SETTINGS_LAST = BATTLE_OBJECTS_ARCHERS_PENALTY + 1;
}
