
package hellofx;

import hellofx.common.Tuple;
import hellofx.dialogs.controllers.ResourcesController;
import hellofx.framework.*;
import hellofx.game.MapView;
import hellofx.graphics.ImageRepo;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
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

        CanvasWrap canvasWrap = context.inject(CanvasWrap.class);
        MainStackPane mainStackPane = context.inject(MainStackPane.class);
        mainStackPane.push(canvasWrap.canvas);

        context.inject(Gameplay.class);


        stage.setScene(new Scene(mainStackPane.stackPane));
        stage.show();
        timer.start();
    }
}
