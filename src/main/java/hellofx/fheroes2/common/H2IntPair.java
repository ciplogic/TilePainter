package hellofx.fheroes2.common;

public class H2IntPair {
    public int first, second;

    public H2IntPair() {
    }

    public H2IntPair(int k, int v) {
        first = k;
        second = v;
    }

    @Override
    public String toString() {
        return "[" + first + ", " + second + "]";
    }
}
