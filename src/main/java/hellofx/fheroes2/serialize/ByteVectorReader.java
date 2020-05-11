package hellofx.fheroes2.serialize;

public class ByteVectorReader {
    private final byte[] _fileContent;
    private int _pos;

    public ByteVectorReader(byte[] fileContent) {
        _fileContent = fileContent;
    }

    public int getLE16() {
        var lo = Get8();
        var hi = Get8();
        return lo + (hi << 8);
    }

    public static int toByte(byte x) {
        return x & 0xFF;
    }

    public int Get8() {
        var result = _fileContent[_pos];
        _pos++;
        return toByte(result);
    }

    public void seek(int pos) {
        _pos = pos;
    }

    public String toString(int lenText) {
        var chars = new char[lenText];
        var lenTextFinal = -1;
        for (var i = 0; i < lenText; i++) {
            var curChar = (char) Get8();
            if (curChar == 0 && lenTextFinal == -1) lenTextFinal = i;

            chars[i] = curChar;
        }

        if (lenTextFinal == -1)
            lenTextFinal = lenText;
        var s = new String(chars, 0, lenTextFinal);
        return s;
    }

    public int getLE32() {
        var lo = getLE16();
        var hi = getLE16();
        return lo + (hi << 16);
    }

    public byte[] getRaw(int size) {
        var result = new byte[size];
        for (var i = 0; i < size; i++) result[i] = _fileContent[i + _pos];

        _pos += size;
        return result;
    }

    public void skip(int s) {
        _pos += s;
    }

    public int getBE32() {
        var lo1 = Get8();
        var hi1 = Get8();
        var lo2 = Get8();
        var hi2 = Get8();
        var lo = (hi1 << 16) + (lo1 << 24);
        var hi = hi2 + (lo2 << 8);
        return (hi + lo);
    }

    public int size() {
        return _fileContent.length;
    }

    public int get() {
        return Get8();
    }

    public int tell() {
        return _pos;
    }
}
