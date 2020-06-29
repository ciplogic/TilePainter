package hellofx.fheroes2.gui;

import hellofx.fheroes2.agg.Bitmap;
import hellofx.fheroes2.agg.Sprite;
import hellofx.fheroes2.common.H2Point;
import hellofx.framework.controls.Painter;

public class GameCamera {
    public int left;
    public int top;
    public static final int originalTileSize = 32;
    public int tileSize = 32;
    int scaleBy = scaleByRatio();

    public int coordinateOfTile(int countTiles) {
        return countTiles * tileSize;
    }

    public int tileCount(int coordinate) {
        if (coordinate > 0)
            return coordinate / tileSize;
        return (coordinate - tileSize + 1) / tileSize;
    }

    public int getLeftTile() {
        return tileCount(left);
    }

    public int getTopTile() {
        return tileCount(top);
    }

    public void moveCameraByTiles(int tilesX, int tilesY) {
        var addX = coordinateOfTile(tilesX);
        var addY = coordinateOfTile(tilesY);
        left += addX;
        top += addY;
    }

    public void drawImageOnTile(Painter dst, Bitmap tileSurface, int tileX, int tileY) {
        if (tileSurface == null) {
            return;
        }
        var tileXMap = coordinateOfTile(tileX);
        var tileYMap = coordinateOfTile(tileY);
        var screenX = tileXMap - left;
        var screenY = tileYMap - top;
        dst.drawImage(tileSurface.renderedImage(getScaler()), screenX, screenY);

    }

    public ImageScaler getScaler() {
        return (bmp) -> {
            //return bmp.bilinearScale(bmp.Width * scaleBy, bmp.Height * scaleBy).toImage();
            var start = originalTileSize;
            var scaledBmp = bmp;
            while (start != tileSize) {
                scaledBmp = scaledBmp.doublePicture();
                start *= 2;
            }
            return scaledBmp.toImage();
        };
    }

    int scaleByRatio() {
        return tileSize / originalTileSize;
    }

    public void drawSpriteOnTile(Painter dst, Sprite tileSurface, int tileX, int tileY) {
        if (tileSurface == null) {
            return;
        }
        var tileXMap = coordinateOfTile(tileX) + tileSurface.pos.x * scaleBy;
        var tileYMap = coordinateOfTile(tileY) + tileSurface.pos.y * scaleBy;
        var screenX = tileXMap - left;
        var screenY = tileYMap - top;
        dst.drawImage(tileSurface.renderedImage(getScaler()), screenX, screenY);
    }

    public void moveCameraByPoint(H2Point delta) {
        left += delta.x;
        top += delta.y;
    }
}
