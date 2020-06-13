package hellofx;

import hellofx.dialogs.atoms.ViewAtoms;
import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.common.Engine;
import hellofx.fheroes2.common.H2Rect;
import hellofx.fheroes2.game.Game;
import hellofx.fheroes2.gui.GameArea;
import hellofx.fheroes2.gui.LevelKind;
import hellofx.framework.EventNames;
import hellofx.framework.MainContext;
import hellofx.framework.ObjectNames;
import hellofx.framework.controls.CanvasWrap;
import hellofx.framework.controls.MainBorderPane;
import hellofx.framework.controls.MainStackPane;
import hellofx.game.MapView;
import hellofx.graphics.ImageRepo;
import hellofx.player.PlayerList;
import javafx.scene.image.Image;
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

    public GameArea gameArea;
    public Engine engine;
    public Agg agg;


    PlayerList playerList;
    double idx = 0;
    private Image heroesImg;
    private Image heroesImgSmooth;

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

        engine.loadTiles(agg);
        agg.imageScaler = gameArea.camera.getScaler();

        //this.heroesImg = AggPaint.RenderICNSprite(agg, IcnKind.HEROES, 0).first.doublePicture().toImage();
        //this.heroesImgSmooth = AggPaint.RenderICNSprite(agg, IcnKind.HEROES, 0).first.bilinearScale(1920, 1080).toImage();
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
        Game.nextFrame();
        idx += 1;
        //gameArea.camera.left = (int) idx/ 2;
        //gameArea.camera.top = (int) idx ;
        var painter = canvasWrap.getPainter();

        painter.clear(Color.ROYALBLUE);
        //mapView.paintGround(engine, imageRepo, painter, tileX, 1, tileX % 1000);
        //painter.drawImage(heroesImgSmooth, 0, 0);
        //painter.drawImage(heroesImg, 0, 0);

        var rect = new H2Rect(0, 0, 109, 46);

        gameArea.Repaint(painter, LevelKind.LEVEL_ALL, rect, agg);
    }
}
