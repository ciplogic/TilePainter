package hellofx.fheroes2.kingdom;

import static hellofx.fheroes2.system.Translate.tr;

public class H2Color {
    public static final int NONE = 0x00;
    public static final int BLUE = 0x01;
    public static final int GREEN = 0x02;
    public static final int RED = 0x04;
    public static final int YELLOW = 0x08;
    public static final int ORANGE = 0x10;
    public static final int PURPLE = 0x20;
    public static final int UNUSED = 0x80;
    public static final int ALL = BLUE | GREEN | RED | YELLOW | ORANGE | PURPLE;

    public static int GetFirst(int colors) {
        //TODO
        return 0;
    }

    private static final String[] str_color = {
            "None", tr("Blue"), tr("Green"), tr("Red"), tr("Yellow"), tr("Orange"), tr("Purple"),
            "uknown"
    };

    public static String String(int color) {

        return switch (color) {
            case BLUE -> str_color[1];
            case GREEN -> str_color[2];
            case RED -> str_color[3];
            case YELLOW -> str_color[4];
            case ORANGE -> str_color[5];
            case PURPLE -> str_color[6];
            case UNUSED -> str_color[7];
            default -> str_color[0];
        };
    }

    public static int GetIndex(int color) {
        return switch (color) {
            case BLUE -> 0;
            case GREEN -> 1;
            case RED -> 2;
            case YELLOW -> 3;
            case ORANGE -> 4;
            case PURPLE -> 5;
            default -> 6; // NONE
        };
    }
}

