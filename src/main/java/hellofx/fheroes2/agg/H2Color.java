package hellofx.fheroes2.agg;

public class H2Color {
    public static int FromArgb(int r, int g, int b) {
        return FromArgb(r, g, b, 255);
    }

    public static int FromArgb(int r, int g, int b, int a) {
        return b + (g << 8) + (r << 16) + (a << 24);
    }
}
