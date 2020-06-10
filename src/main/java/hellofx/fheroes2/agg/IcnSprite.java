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

    public void doubleImage(boolean useAntialiasing) {
        if (useAntialiasing) {
            if (first != null)
                first = first.doublePictureAa();
            if (second != null)
                second = second.doublePictureAa();
        } else {
            if (first != null)
                first = first.doublePicture();
            if (second != null)
                second = second.doublePicture();
        }
    }

    public Sprite CreateSprite(boolean reflect, boolean shadow) {
        var res = new Bitmap(first.Width, first.Height);
        first.BlitTo(res);

        if (shadow && Bitmap.isValid(second))
            second.BlitTo(res);

        return new Sprite(reflect ? Bitmap.RenderReflect(res, 2) : res, offset.x, offset.y);
    }

    public boolean isValid() {
        return first.isValid();
    }

}

