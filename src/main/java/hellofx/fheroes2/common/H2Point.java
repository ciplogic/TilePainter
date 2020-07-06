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

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    }

    public H2Point add(H2Point other) {
        return add(other.x, other.y);
    }

    public H2Point add(int ax, int ay) {
        var result = pointClone();
        result.x += ax;
        result.y += ay;
        return result;
    }

    private H2Point pointClone() {
        return new H2Point(x, y);
    }

    public H2Point vectorFrom(H2Point point) {
        return new H2Point(x - point.x, y - point.y);
    }

    public H2Point invert() {
        return new H2Point(-x, -y);
    }

    public boolean isEqual(H2Point other) {
        return x == other.x && y == other.y;
    }

    public H2Point sub(H2Point other) {
        return add(other.invert());
    }
}
