package hellofx.fheroes2.agg;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

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

    public Image toImage() {
        WritableImage img = new WritableImage(Width, Height);
        PixelWriter pw = img.getPixelWriter();
        pw.setPixels(0, 0, Width, Height, PixelFormat.getIntArgbInstance(), pixels, 0, Width);
        return img;
    }
}

