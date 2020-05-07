package hellofx.graphics;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageRepo {
    Map<String, Image> _items = new HashMap<>();

    public Image get(String image) {
        if (_items.containsKey(image))
            return _items.get(image);
        var file = new File(image);
        var resultPath = file.toURI().toString();
        var imageFx = new Image(resultPath);
        _items.put(image, imageFx);
        return imageFx;
    }
}
