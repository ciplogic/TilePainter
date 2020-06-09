package hellofx.fheroes2.gui;

import hellofx.framework.controls.Painter;
import javafx.scene.image.Image;

public class GameCamera {
    public int left = -300;
    public int top = -200;
    public int originalTileSize = 32;
    public int tileSize = 64;

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

    public void drawImageOnTile(Painter dst, Image tileSurface, int tileX, int tileY) {
        var tileXMap = coordinateOfTile(tileX);
        var tileYMap = coordinateOfTile(tileY);
        var screenX = tileXMap - left;
        var screenY = tileYMap - top;
        dst.drawImage(tileSurface, screenX, screenY);

    }

    public ImageScaler getScaler() {
        return (bmp) -> {
            var scaleFactor = tileSize / originalTileSize;
            return bmp.bilinearScale(bmp.Width * scaleFactor, bmp.Height * scaleFactor).toImage();
        };
    }
}
