package hellofx.fheroes2.agg;

public class H2Color {
    public static int FromArgb(int r, int g, int b) {
        return r + (g << 8) + (b << 16);
    }

    public static int FromArgb(int r, int g, int b, int a) {
        return FromArgb(r, g, b) + (a << 24);
    }
}
