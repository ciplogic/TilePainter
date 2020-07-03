package hellofx;

import hellofx.dialogs.DownloadDialog;
import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.common.Engine;
import hellofx.fheroes2.gui.GameArea;
import hellofx.fheroes2.gui.GameCamera;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.heroes.HeroesKind;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.kingdom.WorldLoad;
import hellofx.fheroes2.maps.FileInfo;
import hellofx.fheroes2.system.Players;
import hellofx.fheroes2.system.Settings;
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

/**
 * @author ciprian
 */
public class HelloFx extends Application {
    public static void main(String[] args) {
        Application.launch(HelloFx.class);
    }


    @Override
    public void start(Stage stage) {

        var downloadDialog = new DownloadDialog();
        downloadDialog.BuildView();
        //ResourceDownloader.downloadAllPacks(".");
        var context = new MainContext();

        var agg = Agg.Get();
        agg.setup();
        //Music.ExtractAllMus(agg);
        //Music.ExtractAllWav(agg);
        context.injectInstance(agg);

        context.inject(new GameCamera());
        context.inject(new GameArea());

        var engine = context.inject(new Engine());
        var conf = Settings.Get();
        Players players = conf.GetPlayers();
        var fileInfo = new FileInfo();
        fileInfo.ReadMP2("MAPS/BROKENA.MP2");
        players.Init(fileInfo);
        players.SetStartGame();

        var world = World.Instance;
        WorldLoad.loadMapMp2("MAPS/BROKENA.MP2");

        var hero = new Heroes(HeroesKind.ARIEL, RaceKind.SORC);
        //var heroBmp = Heroes.GetPortrait(hero.portrait, 1).doublePicture().doublePictureAa();
        //heroBmp.saveToFile(new File("cursor.png"));
        var timer = context.inject(new PerFrameTimer());
        context.inject(new GamePreferences());
        context.inject(new ImageRepo());

        context.injectInstance(new MapView(128, 128));

        var mainBorderPane = context.inject(new MainBorderPane());

        CanvasWrap canvasWrap = context.inject(new CanvasWrap());
        MainStackPane mainStackPane = context.inject(new MainStackPane());
        mainStackPane.push(canvasWrap.canvas);

        context.inject(new Gameplay());

        mainBorderPane.borderPane.setCenter(mainStackPane.stackPane);

        stage.setScene(new Scene(mainBorderPane.borderPane));
        stage.show();
        timer.start();
    }
}
