
package hellofx;

import hellofx.framework.CanvasWrap;
import hellofx.framework.GamePreferences;
import hellofx.framework.MainContext;
import hellofx.framework.ObjectNames;
import hellofx.game.MapView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author ciprian
 */
public class HelloFx extends Application {
    public static void main(String[] args) {
        
        Application.launch(HelloFx.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var context = new MainContext();
        var timer = context.inject(PerFrameTimer.class);
        context.inject(GamePreferences.class);

        context.injectInstance(new MapView(128, 128));

        CanvasWrap canvas = context.inject(CanvasWrap.class);
        var mainStackPane =new StackPane(canvas.canvas);
        context.setObj(ObjectNames.mainStack, mainStackPane);


        var image = new Image(Utilities.getResource("data/tree.png"));
        var gameplay = context.inject(Gameplay.class);

        stage.setScene(new Scene(mainStackPane));
        stage.show();
        timer.start();
    }
}
