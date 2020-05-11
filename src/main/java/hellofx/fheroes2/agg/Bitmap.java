package hellofx.fheroes2.agg;

public class Bitmap {
    private final int[] pixels;
    public int Width;
    public int Height;

    public Bitmap(int width, int height) {
        this.Width = width;
        this.Height = height;
        pixels = new int[width * height];
    }

    public void SetPixel(int x, int y, int palColor) {
        pixels[Width * y + x] = palColor;
    }
}

