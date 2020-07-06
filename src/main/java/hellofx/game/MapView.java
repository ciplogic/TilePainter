package hellofx.game;

import hellofx.fheroes2.agg.TilKind;
import hellofx.fheroes2.common.Engine;
import hellofx.framework.controls.Painter;
import hellofx.graphics.ImageRepo;
import hellofx.views.GroundValues;
import javafx.scene.image.Image;

import java.util.stream.IntStream;

public class MapView {
    public Tile[] tiles;
    public int width;
    public int height;

    public int cameraWidth;
    public int cameraHeight;

    GroundValues _ground;

    public MapView(int width, int height) {
        int count = width * height;
        tiles = new Tile[count];
        this.width = width;
        this.height = height;
        _ground = new GroundValues(width, height);
        IntStream.range(0, count).forEach(i -> {
            tiles[i] = new Tile();
        });
    }

    public void setCameraWidth(int width) {
        cameraWidth = width;
    }

    public void setCameraHeight(int height) {
        cameraHeight = height;
    }

    public void paintGround(Engine engine, ImageRepo imageRepo, Painter painter, int left, int top, int shiftWidth) {
        //var sandImage = imageRepo.get("data/sand_small.png");
        Image sandImage = engine.surfaces[TilKind.GROUND32]._surfaces.get(32);
        var image = imageRepo.get("data/tree_tiled.png");

        var ground = getGround();
        var tileWidth = 32;
        var tileHeight = 18;
        var shiftX = shiftWidth - 64;
        ground.visitRange(left, top, tileWidth, tileHeight, (x, y, value) -> {
            painter.drawImage(sandImage, 64 * x + shiftX, 64 * y);

        });

        ground.visitRange(left, top, tileWidth, tileHeight, (x, y, value) -> {
            if (value > 0)
                painter.drawImage(image, 64 * x + shiftX, 64 * y);
        });

    }

    public GroundValues getGround() {
        return _ground;
    }
}
