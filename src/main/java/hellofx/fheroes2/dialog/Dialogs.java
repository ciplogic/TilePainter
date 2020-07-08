package hellofx.fheroes2.dialog;

import hellofx.fheroes2.resource.Artifact;

public class Dialogs {

    public static final int ZERO = 0x0000;
    public static final int YES = 0x0001;
    public static final int OK = 0x0002;
    public static final int NO = 0x0004;
    public static final int CANCEL = 0x0008;
    public static final int DISMISS = 0x0010;
    public static final int UPGRADE = 0x0020;
    public static final int MAX = 0x0040;
    public static final int PREV = 0x0080;
    public static final int NEXT = 0x0100;
    public static final int WORLD = 0x0200;
    public static final int PUZZLE = 0x0400;
    public static final int INFO = 0x0800;
    public static final int DIG = 0x1000;
    public static final int UPGRADE_DISABLE = MAX;
    public static final int READONLY = 0x2000;
    public static final int BUTTONS = YES | OK | NO | CANCEL;

    public static int ArtifactInfo(String hdr, String msg, Artifact art, int buttons) {
        //TODO
        return 0;
    }

    public static int Message(String header, String message, int ft, int buttons) {
        //TODO
        return 0;
    }

    public static int ArtifactInfo(String s, String tr, int battleGarb) {
        //TODO
        return 0;
    }
}
