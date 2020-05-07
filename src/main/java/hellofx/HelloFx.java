package hellofx;

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
