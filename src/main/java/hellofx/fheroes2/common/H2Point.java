package hellofx.fheroes2.common;

import java.util.Objects;

public class H2Point {
    public int x, y;

    public H2Point(int aX, int aY) {
        x = aX;
        y = aY;
    }

    public H2Point() {
    }

    public H2Point(H2Point pt) {
        x = pt.x;
        y = pt.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var h2Point = (H2Point) o;
        return x == h2Point.x &&
                y == h2Point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
