package hellofx.fheroes2.system;

public class Translate {
    public static String tr(String translateFrom) {
        //TODO: it should contain a replace table for translations

        return translateFrom;
    }

    public static String StringReplace(String text, String from, String to) {
        return text.replace(from, to);
    }
}
