package hellofx.fheroes2.agg.cache;

import hellofx.fheroes2.agg.Bitmap;

public class til_cache_t {
    public Bitmap[] sprites;
    public int count;

    public void setSize(int newCount) {
        count = newCount;
        sprites = new Bitmap[count];

    }

    public void setSprite(int index, Bitmap bmp) {
        sprites[index] = bmp;
    }

    public Bitmap getImage(int index) {
        return sprites[index];
    }
}
