package hellofx.fheroes2.common;

public class H2Size {
    public short w;
    public short h;

    public H2Size(int width, int height) {
        w = (short) width;
        h = (short) height;
    }

    @Override
    public String toString() {
        return "w=" + w + ", h=" + h;
    }
}
