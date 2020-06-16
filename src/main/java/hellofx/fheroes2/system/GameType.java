package hellofx.fheroes2.system;

public class GameType {
    public static final int TYPE_MENU = 0;
    public static final int TYPE_STANDARD = 0x01;
    public static final int TYPE_CAMPAIGN = 0x02;
    public static final int TYPE_HOTSEAT = 0x04;
    public static final int TYPE_NETWORK = 0x08;
    public static final int TYPE_BATTLEONLY = 0x10;
    public static final int TYPE_LOADFILE = 0x80;
    public static final int TYPE_MULTI = TYPE_HOTSEAT | TYPE_NETWORK;
}
