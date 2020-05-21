package hellofx.fheroes2.serialize;

import java.io.IOException;

public class ResourceDownloader {

    public static final String FullUrlToPacks = "https://github.com/ciplogic/fheroes2enh/releases/download/0.9.1/";
    public static final String[] Packs =
            {
                    "h2demo.zip", "EssentialMapPack.zip", "AdditionalMapPack.zip", "MegaMapPack.zip"
            };

    public static boolean downloadAllPacks(String destDir) {
        for (var pack : Packs) {
            var url = FullUrlToPacks + pack;
            System.out.println("Downloading: " + pack);
            FileUtils.downloadFile(url, pack);
        }
        for (var localZip : Packs) {
            try {
                System.out.println("Extracting: " + localZip);
                FileUtils.extractZip(localZip, destDir);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        System.out.println("... done");
        return true;
    }
}
