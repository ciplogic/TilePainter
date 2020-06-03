package hellofx.fheroes2.common.music;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.XmiKind;
import hellofx.fheroes2.common.music.m82.M82Kind;
import hellofx.fheroes2.serialize.ByteVectorWriter;
import hellofx.fheroes2.serialize.FileUtils;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class Music {
    public static byte[] Xmi2Mid(byte[] buf) {
        XmiData xmi = new XmiData(buf);
        ByteVectorWriter sb = new ByteVectorWriter(16 * 4096);

        if (xmi.isvalid()) {
            MidData mid = new MidData(xmi.tracks, 64);
            mid.writeTo(sb);
        }

        return sb.data();
    }

    public static byte[] LoadM82(Agg agg, int m82) {
        var body = agg.Read(M82Kind.GetString(m82));

        if (body.length == 0)
            return new byte[0];
        // create WAV format
        ByteVectorWriter wavHeader = new ByteVectorWriter(44);
        wavHeader.putLE32(0x46464952); // RIFF
        wavHeader.putLE32(body.length + 0x24); // size
        wavHeader.putLE32(0x45564157); // WAVE
        wavHeader.putLE32(0x20746D66); // FMT
        wavHeader.putLE32(0x10); // size_t
        wavHeader.putLE16(0x01); // format
        wavHeader.putLE16(0x01); // channels
        wavHeader.putLE32(22050); // samples
        wavHeader.putLE32(22050); // byteper
        wavHeader.putLE16(0x01); // align
        wavHeader.putLE16(0x08); // bitsper
        wavHeader.putLE32(0x61746164); // DATA
        wavHeader.putLE32(body.length); // size

        var vecData = wavHeader.data();
        var v = new ByteArrayList();

        v.addElements(0, vecData);
        v.addElements(v.size(), body);
        return v.toByteArray();
    }

    public static void ExtractAllMus(Agg agg) {
        var player = new MidPlay();
        player.prepare(false);
        player.setDevice(0);
        IntStream.range(1, XmiKind.MIDI0043 + 1)
                .forEach(xmiId -> {
                    extractWavFromXmiId(agg, xmiId, player);
                });
    }

    private static void extractWavFromXmiId(Agg agg, int xmiId, MidPlay player) {
        var midData = agg.LoadMID(xmiId);
        if (midData.length == 0)
            return;
        var xmi = XmiKind.GetString(xmiId);
        var baseName = xmi.replace(".XMI", "");
        var midFile = "gamedata/" + baseName + ".mid";
        var wavFile = "gamedata/" + baseName + ".wav";
        FileUtils.writeFile(midFile, midData);
        player.convWav(new File(midFile), new File(wavFile));
        try {
            Files.delete(Path.of(midFile));
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }
}
