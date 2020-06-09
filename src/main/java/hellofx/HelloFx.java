package hellofx;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.common.Engine;
import hellofx.fheroes2.gui.GameArea;
import hellofx.fheroes2.gui.GameCamera;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.heroes.HeroesKind;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.kingdom.World;
import hellofx.framework.GamePreferences;
import hellofx.framework.MainContext;
import hellofx.framework.controls.CanvasWrap;
import hellofx.framework.controls.MainBorderPane;
import hellofx.framework.controls.MainStackPane;
import hellofx.framework.controls.PerFrameTimer;
import hellofx.game.MapView;
import hellofx.graphics.ImageRepo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author ciprian
 */
public class HelloFx extends Application {
    public static void main(String[] args) {
        Application.launch(HelloFx.class);
    }


    @Override
    public void start(Stage stage) {
        var context = new MainContext();

        var agg = Agg.Get();
        agg.setup();
        //Music.ExtractAllMus(agg);
        //Music.ExtractAllWav(agg);
        context.injectInstance(agg);

        context.inject(GameCamera.class);
        context.inject(GameArea.class);

        var engine = context.inject(Engine.class);

        var world = World.Instance;
        world.loadMapMp2("maps/BROKENA.MP2");

        var hero = new Heroes(HeroesKind.ARIEL, RaceKind.SORC);
        var heroBmp = Heroes.GetPortrait(hero.portrait, 1).doublePicture().doublePictureAa();
        heroBmp.saveToFile(new File("cursor.png"));
        var timer = context.inject(PerFrameTimer.class);
        context.inject(GamePreferences.class);
        context.inject(ImageRepo.class);

        context.injectInstance(new MapView(128, 128));

        var mainBorderPane = context.inject(MainBorderPane.class);

        CanvasWrap canvasWrap = context.inject(CanvasWrap.class);
        MainStackPane mainStackPane = context.inject(MainStackPane.class);
        mainStackPane.push(canvasWrap.canvas);

        context.inject(Gameplay.class);

        mainBorderPane.borderPane.setCenter(mainStackPane.stackPane);

        stage.setScene(new Scene(mainBorderPane.borderPane));
        stage.show();
        timer.start();
    }
}
