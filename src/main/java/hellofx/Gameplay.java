package hellofx;

import hellofx.framework.CanvasWrap;
import hellofx.framework.EventNames;
import hellofx.framework.MainContext;
import hellofx.game.MapView;
import hellofx.graphics.ImageRepo;
import hellofx.player.PlayerList;
import hellofx.views.ResourceViewer;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Random;

public class Gameplay {
    public MainContext ctx;
    public CanvasWrap canvasWrap;
    public MapView mapView;
    public ImageRepo imageRepo;

    PlayerList playerList;

    Image image;
    Image sandImage;
    public void setup() {
        image = new Image(Utilities.getResource("data/tree_tiled.png"));
        sandImage = new Image(Utilities.getResource("data/sand_small.png"));
        setupRandomLevel();
        playerList = new PlayerList(2);
        ctx.listen(EventNames.onFrame, (Long l) -> {
            onFrameUpdate();
        });
        ctx.listen(EventNames.onMouse, (MouseEvent mEvent) -> {

        });
    }

    private void setupRandomLevel() {
        var ground = mapView.getGround();
        var random = new Random();
        for(var i = 0; i<10000; i++){
            var x = random.nextInt(mapView.width);
            var y = random.nextInt(mapView.height);
            ground.setValue(x, y, 7);
        }

    }

    double idx = 0;

    void onFrameUpdate(){
        idx += 0.3;
        var painter = canvasWrap.getPainter();
        painter.clear(Color.ROYALBLUE);
        painter.drawImage(image, 100, 100);
        mapView.paintGround(imageRepo, painter, (int)idx, 1);

        ResourceViewer.paint(painter, playerList.get(0).resources);
    }
}
