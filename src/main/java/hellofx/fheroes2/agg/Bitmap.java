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

    private static boolean areEqualFloats(double v1, double v2) {
        return Math.abs(v1 - v2) < 1e-5;
    }

    public int GetPixel(int x, int y) {
        return pixels[Width * y + x];
    }

    public Bitmap doublePicture() {
        var result = new Bitmap(this.Width * 2, this.Height * 2);
        for (var y = 0; y < Height; y++) {
            for (var x = 0; x < Width; x++) {
                var pixel = GetPixel(x, y);
                result.SetPixel(x * 2, y * 2, pixel);
                result.SetPixel(x * 2 + 1, y * 2, pixel);
                result.SetPixel(x * 2, y * 2 + 1, pixel);
                result.SetPixel(x * 2 + 1, y * 2 + 1, pixel);
            }
        }
        return result;
    }

    public Bitmap bilinearScale(int newWidth, int newHeight) {
        var result = new Bitmap(newWidth, newHeight);
        var oldMaxW = Width - 1;
        var oldMaxH = Height - 1;
        var newMaxW = newWidth - 1;
        var newMaxH = newHeight - 1;
        var stepY = oldMaxH * 1.0 / newMaxH;
        var stepX = oldMaxW * 1.0 / newMaxW;
        var srcY = 0.0;
        for (var y = 0; y <= newMaxH; y++) {
            var lowY = (int) srcY;
            var highY = areEqualFloats(lowY, srcY) ? lowY : lowY + 1;

            var tRatioY = srcY - lowY;
            var srcX = 0.0;
            for (var x = 0; x <= newMaxW; x++, srcX += stepX) {
                var lowX = (int) srcX;
                var highX = areEqualFloats(lowX, srcX) ? lowX : lowX + 1;

                var topLeft = GetPixel(lowX, lowY);
                if (lowX == highX && lowY == highY) {
                    result.SetPixel(x, y, topLeft);
                    continue;
                }
                var tRatioX = srcX - lowX;

                var topRight = GetPixel(highX, lowY);
                var bottomLeft = GetPixel(lowX, highY);
                var bottomRight = GetPixel(highX, highY);


                var midTop = Pixel.lerpPixels(topLeft, topRight, tRatioX);
                var midBottom = Pixel.lerpPixels(bottomLeft, bottomRight, tRatioX);
                var lerpPixel = Pixel.lerpPixels(midTop, midBottom, tRatioY);
                result.SetPixel(x, y, lerpPixel);
            }
            srcY += stepY;
        }
        return result;
    }

    public Bitmap doublePictureAa() {
        return bilinearScale(this.Width * 2, this.Height * 2);
    }

    public Image toImage() {
        WritableImage img = new WritableImage(Width, Height);
        PixelWriter pw = img.getPixelWriter();
        pw.setPixels(0, 0, Width, Height, PixelFormat.getIntArgbInstance(), pixels, 0, Width);
        return img;
    }

    public void DrawPointPalette(int x, int y, byte palette) {
        if (x < 0 || y < 0)
            return;
        Bitmap srf = this;
        if (x >= srf.Width || y >= srf.Height)
            return;
        srf.SetPixel(x, y, Pixel.paletteToArgb(palette));
    }
}
