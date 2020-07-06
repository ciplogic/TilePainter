package hellofx.fheroes2.battle;

public class direction_t {

    public static final int UNKNOWN = 0x00;
    public static final int TOP_LEFT = 0x01;
    public static final int TOP_RIGHT = 0x02;
    public static final int RIGHT = 0x04;
    public static final int BOTTOM_RIGHT = 0x08;
    public static final int BOTTOM_LEFT = 0x10;
    public static final int LEFT = 0x20;
    public static final int CENTER = 0x40;
    public static final int RIGHT_SIDE = TOP_RIGHT | RIGHT | BOTTOM_RIGHT;
    public static final int LEFT_SIDE = TOP_LEFT | LEFT | BOTTOM_LEFT;
    public static final int AROUND = RIGHT_SIDE | LEFT_SIDE;
}
