package hellofx.fheroes2.common;

public class H2Pair<Key, Value> {
    public Key first;
    public Value second;

    public H2Pair() {
    }

    public H2Pair(Key k, Value v) {
        first = k;
        second = v;
    }

    @Override
    public String toString() {
        return "[" + first + ", " + second + "]";
    }
}
