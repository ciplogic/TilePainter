package hellofx.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Utilities {

    public static <TView extends Parent, TCtrl>
    Tuple<TView, TCtrl> loadFxml(String fxmlFileName) {
        Tuple<TView, TCtrl> result = new Tuple<>();
        FXMLLoader loader = null;
        var file = new File(fxmlFileName);
        if (!file.exists()) {
            throw new RuntimeException("FXML file does not exist on disk: " + fxmlFileName);
        }
        var resourceUrl = file.getAbsolutePath();
        try {
            loader = new FXMLLoader(new URL("file:" + resourceUrl));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
        try {
            TView parent = loader.load();
            result.setV1(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.setV2(loader.getController());
        return result;
    }

    public static <T> T newInstanceOf(Class<?> clazz) {
        try {
            return (T) clazz.getConstructor().newInstance();

        } catch (Exception ex) {
            return null;
        }

    }

    public static void setBackground(Region root, Color color) {
        root.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    public static void timeIt(String message, Runnable action) {
        var start = System.currentTimeMillis();
        action.run();
        var end = System.currentTimeMillis();
        System.out.println("Time to do " + message + " is in ms: " + (end - start));
    }

    public static String getResource(String path) {
        var file = new File(path);
        var result = file.toURI().toString();
        return result;
    }
}