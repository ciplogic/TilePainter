package hellofx.fheroes2.serialize;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {
    public static boolean Exists(String fname) {
        var f = new File(fname);
        return f.exists();
    }

    public static byte[] ReadAllBytes(String filename) {
        var file = new File(filename);
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public static void downloadFile(String url, String destinationFile) {
        try {
            var in = new URL(url).openStream();
            Files.copy(in, Paths.get(destinationFile), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
