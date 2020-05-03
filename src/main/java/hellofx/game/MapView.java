package hellofx.game;

import java.util.stream.IntStream;

public class MapView {
    public Tile[] tiles;
    public int width;
    public int height;

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

    public GroundValues getGround(){
        return _ground;
    }
}
