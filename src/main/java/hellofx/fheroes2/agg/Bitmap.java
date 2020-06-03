package hellofx.fheroes2.agg;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Math.min;

public class Bitmap {
    private final int[] pixels;
    public int Width;
    public int Height;

    public static final Bitmap Empty = new Bitmap(0, 0);

    public Bitmap(int width, int height) {
        this.Width = width;
        this.Height = height;
        pixels = new int[width * height];
    }

    public Bitmap(Bitmap other) {
        this.Width = other.Width;
        this.Height = other.Height;
        this.pixels = Arrays.copyOf(other.pixels, other.pixels.length);
    }

    public static boolean isValid(Bitmap bitmap) {
        return bitmap != null && bitmap.isValid();
    }

    public void SetPixel(int x, int y, int argbColor) {
        pixels[Width * y + x] = argbColor;
    }

    public void SetPixelDirect(int index, int palColor) {
        pixels[index] = palColor;
    }

    public int GetIndex(int x, int y) {
        return Width * y + x;
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

    public boolean isValid() {
        return Width != 0;
    }

    public static Bitmap RenderReflect(Bitmap _this, int shape /* 0: none, 1 : vert, 2: horz, 3: both */) {
        Bitmap res = new Bitmap(_this.Width, _this.Height);

        switch (shape % 4) {
            // normal
            default:
                return new Bitmap(_this);

            // vertical reflect
            case 1:

                for (int yy = 0; yy < _this.Height; ++yy)
                    for (int xx = 0; xx < _this.Width; ++xx)
                        res.SetPixel(xx, _this.Height - yy - 1, _this.GetPixel(xx, yy));
                break;

            // horizontal reflect
            case 2:
                for (int yy = 0; yy < _this.Height; ++yy)
                    for (int xx = 0; xx < _this.Width; ++xx)
                        res.SetPixel(_this.Width - xx - 1, yy, _this.GetPixel(xx, yy));
                break;

            // both variants
            case 3:
                for (int yy = 0; yy < _this.Height; ++yy)
                    for (int xx = 0; xx < _this.Width; ++xx)
                        res.SetPixel(_this.Width - xx - 1, _this.Height - yy - 1, _this.GetPixel(xx, yy));
                break;
        }
        return res;
    }

    public void BlitTo(Bitmap res) {
        var minWidth = min(res.Width, Width);
        var minHeight = min(res.Height, Height);
        for (int y = 0; y < minHeight; ++y) {
            for (int x = 0; x < minWidth; ++x) {
                res.setPixelBlended(x, y, GetPixel(x, y));
            }
        }
    }

    private void setPixelBlended(int x, int y, int pixel) {
        var a = Pixel.getA(pixel);
        if (a == 255) {
            SetPixel(x, y, pixel);
            return;
        }
        if (a == 0) {
            return;
        }
        var srcPixel = GetPixel(x, y);
        int newPixel = Pixel.lerpPixelsI(srcPixel, pixel, a);
        SetPixel(x, y, newPixel);
    }

    public void saveToFile(File outputFile) {
        Image image = toImage();
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
