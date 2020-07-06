package hellofx.fheroes2.battle;

public class stats_t {
    public static final int TR_RESPONSED = 0x00000001;
    public static final int TR_MOVED = 0x00000002;
    public static final int TR_HARDSKIP = 0x00000004;
    public static final int TR_SKIPMOVE = 0x00000008;
    public static final int LUCK_GOOD = 0x00000100;
    public static final int LUCK_BAD = 0x00000200;
    public static final int MORALE_GOOD = 0x00000400;
    public static final int MORALE_BAD = 0x00000800;
    public static final int CAP_TOWER = 0x00001000;
    public static final int CAP_SUMMONELEM = 0x00002000;
    public static final int CAP_MIRROROWNER = 0x00004000;
    public static final int CAP_MIRRORIMAGE = 0x00008000;
    public static final int TR_DEFENSED = 0x00010000;
    public static final int SP_BLOODLUST = 0x00020000;
    public static final int SP_BLESS = 0x00040000;
    public static final int SP_HASTE = 0x00080000;
    public static final int SP_SHIELD = 0x00100000;
    public static final int SP_STONESKIN = 0x00200000;
    public static final int SP_DRAGONSLAYER = 0x00400000;
    public static final int SP_STEELSKIN = 0x00800000;
    public static final int SP_ANTIMAGIC = 0x01000000;
    public static final int SP_CURSE = 0x02000000;
    public static final int SP_SLOW = 0x04000000;
    public static final int SP_BERSERKER = 0x08000000;
    public static final int SP_HYPNOTIZE = 0x10000000;
    public static final int SP_BLIND = 0x20000000;
    public static final int SP_PARALYZE = 0x40000000;
    public static final int SP_STONE = 0x80000000;
    public static final int IS_GOOD_MAGIC = 0x00FE0000;
    public static final int IS_PARALYZE_MAGIC = 0xC0000000;
    public static final int IS_MIND_MAGIC = 0x78000000;
    public static final int IS_BAD_MAGIC = 0xFE000000;
    public static final int IS_MAGIC = 0xFFFE0000;
    public static final int IS_RED_STATUS = IS_BAD_MAGIC;
    public static final int IS_GREEN_STATUS = SP_SHIELD | SP_STEELSKIN | SP_STONESKIN | SP_DRAGONSLAYER | SP_BLOODLUST | SP_BLESS | SP_HASTE | SP_ANTIMAGIC;

}
