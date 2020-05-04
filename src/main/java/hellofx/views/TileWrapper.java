package hellofx.views;


public class TileWrapper {
    public int _value = 0;
    public void update(int value){
        _value = value;
    }

    public void setField(int shiftBits, int value, int bitsToSet){
        var valueShifted = value<<shiftBits;
        var mask = (1<<bitsToSet)-1;
        var notMask = ~mask;
        _value = value & notMask;
        _value= value | valueShifted;
    }

    public int getField(int shiftBits, int bitsToRead){
        var mask = (1<<bitsToRead)-1;
        var result = (_value>>shiftBits) & mask;
        return result;
    }
}
