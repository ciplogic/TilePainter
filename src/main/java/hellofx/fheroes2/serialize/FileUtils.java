package hellofx.fheroes2.serialize;

import hellofx.fheroes2.agg.IcnKind;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, Integer> EnumFieldsOfClass(Class<?> clazz) {

        var fields = IcnKind.class.getFields();
        Map<String, Integer> _names = new HashMap<>();
        for (var f : fields) {
            var name = f.getName();
            if (name.charAt(0) == '_')
                continue;
            Class<?> t = f.getType();
            if (t != int.class)
                continue;
            if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                try {
                    var enumValue = f.getInt(null);
                    _names.put(name, enumValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.toString());
                }

            }
        }
        return _names;

    }

}
