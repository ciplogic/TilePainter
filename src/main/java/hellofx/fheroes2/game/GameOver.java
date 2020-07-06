package hellofx.fheroes2.game;

public class GameOver {
    public static final int COND_NONE = 0x0000;
    public static final int WINS_ALL = 0x0001;
    public static final int WINS_TOWN = 0x0002;
    public static final int WINS_HERO = 0x0004;
    public static final int WINS_ARTIFACT = 0x0008;
    public static final int WINS_SIDE = 0x0010;
    public static final int WINS_GOLD = 0x0020;
    public static final int WINS = WINS_ALL | WINS_TOWN | WINS_HERO | WINS_ARTIFACT | WINS_SIDE | WINS_GOLD;
    public static final int LOSS_ALL = 0x0100;
    public static final int LOSS_TOWN = 0x0200;
    public static final int LOSS_HERO = 0x0400;
    public static final int LOSS_TIME = 0x0800;
    public static final int LOSS_STARTHERO = 0x1000;
    public static final int LOSS = LOSS_ALL | LOSS_TOWN | LOSS_HERO | LOSS_TIME | LOSS_STARTHERO;
}
