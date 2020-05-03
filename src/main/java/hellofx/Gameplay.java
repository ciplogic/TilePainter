package hellofx;

import hellofx.framework.CanvasWrap;
import hellofx.framework.EventNames;
import hellofx.framework.MainContext;
import hellofx.game.MapView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Random;

public class Gameplay {
    public MainContext ctx;
    public CanvasWrap canvasWrap;
    public MapView mapView;


    Image image;
    Image sandImage;
    public void setup() {
        image = new Image(Utilities.getResource("data/tree_tiled.png"));
        sandImage = new Image(Utilities.getResource("data/sand_small.png"));
        setupRandomLevel();
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

    void onFrameUpdate(){
        var painter = canvasWrap.getPainter();
        painter.clear(Color.ROYALBLUE);
        painter.drawImage(image, 100, 100);
        var ground = mapView.getGround();
        ground.visitRange(0,0,20, 20, ((x, y, value) -> {
            painter.drawImage(sandImage, 64*x, 64*y);
            if (value>0)
            painter.drawImage(image, 64*x, 64*y);
        }));
    }
}
