package hellofx.fheroes2.common;


import static java.lang.Math.abs;

public class Tools {
    public static String Int2Str(int value) {
        return Integer.toString(value);
    }

    public static String GetStringShort(int value) {
        if (abs(value) <= 1000)
            return Int2Str(value);
        String result = "";

        if (abs(value) > 1000000)
            result += (value / 1000000) + "M";
        else
            result += (value / 1000) + "K";

        return result;
    }

}
