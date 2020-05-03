package hellofx.game;

public class GroundValues {
    private int _h;
    private int _w;
    int[] _values;
    public GroundValues(int w, int h){
        _values = new int[w*h];
        this._w = w;
        this._h = h;
    }

    int getValue(int x, int y){
        if (x<0 || y<0)
            return -1;
        return _values[x+y*_w];
    }

    public void setValue(int x, int y, int value) {
        _values[x+y*_w] = value;
    }

    public interface ITileVisitor{
        void onTileVisit(int x, int y, int value);
    }

    public void visitRange(int x, int y, int w, int h, ITileVisitor visitor){
        for(var j = 0; j<h; j++) {
            for (var i = 0; i < w; i++) {
                var value = getValue(x+i, y+j);
                visitor.onTileVisit(i,j, value);
            }
        }
    }
}
