package hellofx;

import java.io.File;

public class Utilities {

    public static String getResource(String path){
        var file = new File(path);
        var result = file.toURI().toString();
        return result;
    }
}
