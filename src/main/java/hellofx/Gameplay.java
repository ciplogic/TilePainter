package hellofx;

import hellofx.common.Tuple;
import hellofx.dialogs.atoms.ImageWithPicViewBuilder;
import hellofx.dialogs.atoms.ImageWithPicViewModel;
import hellofx.dialogs.controllers.ResourcesController;
import hellofx.framework.EventNames;
import hellofx.framework.MainContext;
import hellofx.framework.ObjectNames;
import hellofx.framework.controls.CanvasWrap;
import hellofx.framework.controls.MainBorderPane;
import hellofx.framework.controls.MainStackPane;
import hellofx.game.MapView;
import hellofx.graphics.ImageRepo;
import hellofx.player.PlayerList;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Random;

public class Gameplay {
    public MainContext ctx;
    public CanvasWrap canvasWrap;
    public MapView mapView;
    public ImageRepo imageRepo;

    public MainStackPane stackPane;
    public MainBorderPane borderPane;

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

        var topDialog = new ImageWithPicViewBuilder();
        topDialog.setup(ImageWithPicViewModel.class);
        topDialog.ViewModel.text = "Gold";
        topDialog.ViewModel.setImage("data/dlg_img/cash.png");
        borderPane.borderPane.setTop(topDialog.View);

        //fxmlRes = Utilities.loadFxml("data/dialogs/res.fxml");
        //stackPane.push(fxmlRes.getV1(), Pos.TOP_CENTER);
        //fxmlRes.getV2().setup(imageRepo);
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
        mapView.paintGround(imageRepo, painter, (int) idx, 1);
        //fxmlRes.getV2().setValues(200, 50, 12, 15);
    }
}
