package hellofx;

import hellofx.common.Tuple;
import hellofx.dialogs.controllers.ResourcesController;
import hellofx.framework.*;
import hellofx.game.MapView;
import hellofx.graphics.ImageRepo;
import hellofx.player.PlayerList;
import hellofx.views.ResourceViewer;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.Random;

public class Gameplay {
    public MainContext ctx;
    public CanvasWrap canvasWrap;
    public MapView mapView;
    public ImageRepo imageRepo;

    MainStackPane stackPane;

    PlayerList playerList;
    Tuple<Parent, ResourcesController> fxmlRes;

    public void setup() {
        setupRandomLevel();
        playerList = new PlayerList(2);
        ctx.listen(EventNames.onFrame, (Long l) -> {
            onFrameUpdate();
        });
        ctx.listen(EventNames.onMouse, (MouseEvent mEvent) -> {

        });
        stackPane = ctx.getObj(ObjectNames.mainStack);

        fxmlRes = Utilities.loadFxml("data/dialogs/res.fxml");
        stackPane.push(fxmlRes.getV1(),  Pos.TOP_CENTER);
        fxmlRes.getV2().setValues(200, 50, 12, 15);
    }

    private void setupRandomLevel() {
        var ground = mapView.getGround();
        var random = new Random();
        for(var i = 0; i<1000; i++){
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
        mapView.paintGround(imageRepo, painter, (int)idx, 1);

        ResourceViewer.paint(painter, playerList.get(0).resources);
    }
}
