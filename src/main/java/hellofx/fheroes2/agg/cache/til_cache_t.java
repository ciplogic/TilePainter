package hellofx.fheroes2.agg.cache;

import hellofx.fheroes2.agg.Bitmap;
import javafx.scene.image.Image;

public class til_cache_t {
    public Bitmap[] sprites;
    public Image[] surfaces;
    public int count;

    public void setSize(int newCount) {
        count = newCount;
        sprites = new Bitmap[count];
        surfaces = new Image[count];

    }

    public void setSprite(int index, Bitmap bmp) {
        sprites[index] = bmp;
        surfaces[index] = bmp.toImage();
    }

    public Image getImage(int index) {
        return surfaces[index];
    }
}
