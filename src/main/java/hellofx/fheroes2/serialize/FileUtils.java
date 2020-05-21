package hellofx.fheroes2.serialize;

import hellofx.fheroes2.agg.IcnKind;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

    static void createDirsForPath(String fileName) {
        var tmp = new File(fileName);
        var parentFile = tmp.getParentFile();
        if (parentFile == null)
            return;
        File directory = new File(parentFile.getAbsolutePath());
        directory.mkdirs();
    }

    public static void extractZip(String fileZip, String destinationDir) throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry entry = zis.getNextEntry();
        Path root = Paths.get(destinationDir).normalize();
        while (entry != null) {
            Path path = root.resolve(entry.getName()).normalize();
            if (!path.startsWith(root)) {
                // throw new IOException("Invalid ZIP");
            }
            if (entry.isDirectory()) {
                Files.createDirectories(path);
            } else {
                createDirsForPath(path.toString());
                try (OutputStream os = Files.newOutputStream(path)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        os.write(buffer, 0, len);
                    }
                }
            }
            entry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
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
