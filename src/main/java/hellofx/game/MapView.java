package hellofx.game;

import hellofx.framework.controls.Painter;
import hellofx.graphics.ImageRepo;
import hellofx.views.GroundValues;

import java.util.stream.IntStream;

public class MapView {
    public Tile[] tiles;
    public int width;
    public int height;

    public int cameraWidth;
    public int cameraHeight;

    GroundValues _ground;
    public MapView(int width, int height){
        int count = width * height;
        tiles = new Tile[count];
        this.width = width;
        this.height = height;
        _ground = new GroundValues(width, height);
        IntStream.range(0, count).forEach(i->{
            tiles[i] = new Tile();
        });
    }

    public void setCameraWidth(int width) {
        cameraWidth = width;
    }

    public void setCameraHeight(int height) {
        cameraHeight = height;
    }

    public void paintGround(ImageRepo imageRepo, Painter painter, int left, int top) {
        var sandImage = imageRepo.get("data/sand_small.png");
        var image = imageRepo.get("data/tree_tiled.png");

        var ground = getGround();
        ground.visitRange(left, top, 13, 10, ((x, y, value) -> {
            painter.drawImage(sandImage, 64 * x, 64 * y);

        }));

        ground.visitRange(left, top, 13, 10, ((x, y, value) -> {
            if (value > 0)
                painter.drawImage(image, 64 * x, 64 * y);
        }));

    }

    public GroundValues getGround(){
        return _ground;
    }
}
