package hellofx;

import hellofx.common.Tuple;
import hellofx.dialogs.atoms.ViewAtoms;
import hellofx.dialogs.controllers.ResourcesController;
import hellofx.fheroes2.agg.AggFile;
import hellofx.fheroes2.agg.IcnKind;
import hellofx.fheroes2.common.Engine;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Random;

import static hellofx.common.Utilities.timeIt;

public class Gameplay {
    public MainContext ctx;
    public CanvasWrap canvasWrap;
    public MapView mapView;
    public ImageRepo imageRepo;

    public MainStackPane stackPane;
    public MainBorderPane borderPane;

    public Engine engine;
    public AggFile aggFile;


    PlayerList playerList;
    Tuple<Parent, ResourcesController> fxmlRes;
    double idx = 0;
    private Image heroesImg;

    public void setup() {
        setupRandomLevel();
        playerList = new PlayerList(2);
        ctx.listen(EventNames.onFrame, (Long l) -> {
            onFrameUpdate();
        });
        ctx.listen(EventNames.onMouse, (MouseEvent mEvent) -> {

        });
        stackPane = ctx.getObj(ObjectNames.mainStack);

        var topDialog = ViewAtoms.buildViewModel("Gold", "data/dlg_img/cash.png");
        borderPane.borderPane.setRight(topDialog.View);

        engine.loadTiles(aggFile);
        for (var i = 0; i < 100; i++)
            timeIt("doubling image " + i, () -> {
                this.heroesImg = aggFile.RenderICNSprite(IcnKind.HEROES, 0).first.bilinearScale(1920, 1080).toImage();
            });

    }

    private void setupRandomLevel() {
        var ground = mapView.getGround();
        var random = new Random();
        for (var i = 0; i < 1000; i++) {
            var x = random.nextInt(mapView.width);
            var y = random.nextInt(mapView.height);
            ground.setValue(x, y, 7);
        }

    }

    void onFrameUpdate() {
        idx += 0.0002;
        var painter = canvasWrap.getPainter();
        painter.clear(Color.ROYALBLUE);
        var tileX = (int) (idx * 1000.0);
        //mapView.paintGround(engine, imageRepo, painter, tileX, 1, tileX % 1000);
        painter.drawImage(heroesImg, 0, 0);

        //fxmlRes.getV2().setValues(200, 50, 12, 15);
    }
}
