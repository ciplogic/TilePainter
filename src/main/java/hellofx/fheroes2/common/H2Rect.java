package hellofx.fheroes2.common;

public class H2Rect {
    public int x, y, w, h;

    public H2Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public H2Rect() {

    }

    public boolean contains(H2Point pt) {
        return !(pt.x < x || pt.y < y || pt.x >= x + w || pt.y >= y + h);
    }

    public boolean contains(H2Rect rt) {
        return !(x > rt.x + rt.w || x + w < rt.x || y > rt.y + rt.h || y + h < rt.y);
    }
}
