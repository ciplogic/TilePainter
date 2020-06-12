package hellofx.fheroes2.gui;

import hellofx.fheroes2.agg.Bitmap;
import hellofx.fheroes2.agg.Sprite;
import hellofx.framework.controls.Painter;

public class GameCamera {
    public int left;
    public int top;
    public int originalTileSize = 32;
    public int tileSize = 32;

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
            var scaleFactor = tileSize / originalTileSize;
            return bmp.bilinearScale(bmp.Width * scaleFactor, bmp.Height * scaleFactor).toImage();
        };
    }

    public void drawSpriteOnTile(Painter dst, Sprite tileSurface, int tileX, int tileY) {
        if (tileSurface == null) {
            return;
        }
        var tileXMap = coordinateOfTile(tileX) + tileSurface.pos.x;
        var tileYMap = coordinateOfTile(tileY) + tileSurface.pos.y;
        var screenX = tileXMap - left;
        var screenY = tileYMap - top;
        dst.drawImage(tileSurface.renderedImage(getScaler()), screenX, screenY);
    }

    public void drawSpritesOnTile(Painter dst, Sprite[] sprites, int tileX, int tileY) {
        if (sprites == null) {
            return;
        }
        for (var sprite : sprites) {
            drawSpriteOnTile(dst, sprite, tileX, tileY);
        }
    }
}
