package hellofx.fheroes2.system;

public class GlobalSettingsKind {
    public static final int GLOBAL_PRICELOYALTY = 0x00000004;
    public static final int GLOBAL_POCKETPC = 0x00000010;
    public static final int GLOBAL_DEDICATEDSERVER = 0x00000020;
    public static final int GLOBAL_LOCALCLIENT = 0x00000040;

    public static final int GLOBAL_SHOWCPANEL = 0x00000100;
    public static final int GLOBAL_SHOWRADAR = 0x00000200;
    public static final int GLOBAL_SHOWICONS = 0x00000400;
    public static final int GLOBAL_SHOWBUTTONS = 0x00000800;
    public static final int GLOBAL_SHOWSTATUS = 0x00001000;

    public static final int GLOBAL_FONTRENDERBLENDED1 = 0x00020000;
    public static final int GLOBAL_FONTRENDERBLENDED2 = 0x00040000;
    public static final int GLOBAL_USESWSURFACE = 0x00800000;

    public static final int GLOBAL_SOUND = 0x01000000;
    public static final int GLOBAL_MUSIC_EXT = 0x02000000;
    public static final int GLOBAL_MUSIC_CD = 0x04000000;
    public static final int GLOBAL_MUSIC_MIDI = 0x08000000;

    //GLOBAL_UNUSED          = 0x20000000;
    public static final int GLOBAL_USEUNICODE = 0x40000000;
    public static final int GLOBAL_ALTRESOURCE = 0x80000000;

    public static final int GLOBAL_MUSIC = GLOBAL_MUSIC_CD | GLOBAL_MUSIC_EXT | GLOBAL_MUSIC_MIDI;
}
