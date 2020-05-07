package hellofx.views;

public class GroundValues {
    int[] _values;
    private final int _h;
    private final int _w;

    public GroundValues(int w, int h) {
        _values = new int[w * h];
        this._w = w;
        this._h = h;
    }

    int getValue(int x, int y) {
        if (x < 0 || y < 0)
            return -1;
        if (x >= _w || y >= _h)
            return -1;

        return _values[x + y * _w];
    }

    public void setValue(int x, int y, int value) {
        _values[x + y * _w] = value;
    }

    public void visitRange(int x, int y, int w, int h, ITileVisitor visitor) {
        for (var j = 0; j < h; j++) {
            for (var i = 0; i < w; i++) {
                var value = getValue(x + i, y + j);
                visitor.onTileVisit(i, j, value);
            }
        }
    }

    public interface ITileVisitor {
        void onTileVisit(int x, int y, int value);
    }
}
