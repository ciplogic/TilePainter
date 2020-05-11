package hellofx.fheroes2.agg;

import hellofx.fheroes2.common.MutPoint;

public class IcnSprite {
    public Bitmap first;
    public MutPoint offset;
    public Bitmap second;

    public void SetSize(boolean isFirst, int width, int height, boolean b) {
        var bmp = new Bitmap(width, height);
        if (isFirst)
            first = bmp;
        else
            second = bmp;
    }
}

