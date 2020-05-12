package hellofx;

import hellofx.fheroes2.agg.AggFile;
import hellofx.fheroes2.agg.IcnKind;
import hellofx.fheroes2.serialize.FileUtils;
import hellofx.framework.GamePreferences;
import hellofx.framework.MainContext;
import hellofx.framework.controls.CanvasWrap;
import hellofx.framework.controls.MainBorderPane;
import hellofx.framework.controls.MainStackPane;
import hellofx.framework.controls.PerFrameTimer;
import hellofx.game.MapView;
import hellofx.graphics.ImageRepo;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import static hellofx.common.Utilities.timeIt;

/**
 * @author ciprian
 */
public class HelloFx extends Application {
    public static void main(String[] args) {
        Application.launch(HelloFx.class);
    }

    public static void saveToFile(Image image, File outputFile) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) {
        if (!FileUtils.Exists("DATA/HEROES2.AGG")) {
            var urlToDownload = "https://github.com/ciplogic/fheroes2enh/releases/download/0.9.1/h2demo.zip";
            FileUtils.downloadFile(urlToDownload, "h2demo.zip");
            return;
        }
        var aggFile = new AggFile();
        aggFile.Open("DATA/HEROES2.AGG");
        timeIt("load imgs", () -> {
            IntStream.range(1, IcnKind.LASTICN).forEach(icn -> {
                //System.out.println("Pic: "+IcnKind.GetString(icn));
                var welcomeIcn = aggFile.RenderICNSprite(icn, 0);
                //var img = welcomeIcn.first.toImage();
            });
        });

        //saveToFile(img, new File("cursor.png"));
        var context = new MainContext();
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
