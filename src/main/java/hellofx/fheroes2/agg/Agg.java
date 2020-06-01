package hellofx.fheroes2.agg;

import hellofx.fheroes2.agg.cache.icn_cache_t;
import hellofx.fheroes2.agg.cache.til_cache_t;
import hellofx.fheroes2.common.music.Music;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.serialize.FileUtils;
import hellofx.fheroes2.serialize.ResourceDownloader;
import hellofx.framework.MainContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Agg {
    private static final Agg StaticInstance = new Agg();
    private final AggFile mainAgg = new AggFile();
    private final AggFile aggX = new AggFile();
    public MainContext context;
    List<icn_cache_t> icn_cache = new ArrayList<>();
    List<til_cache_t> til_cache = new ArrayList<>();

    public static Agg Get() {
        return StaticInstance;
    }

    public static byte[] ReadICNChunk(int icn) {
        // hard fix artifact "ultimate stuff" sprite for loyalty version
        return Read(IcnKind.GetString(icn));
    }

    /**
     * In FHeroes2 it was returning false, now it would return null if nothing is to render
     *
     * @param icn
     * @param index
     * @param reflect
     * @return
     */
    static Bitmap LoadOrgICN(int icn, int index, boolean reflect) {
        IcnSprite icnSprite = AggPaint.RenderICNSprite(icn, index);
        if (!icnSprite.isValid())
            return null;
        return icnSprite.CreateSprite(reflect, !IcnKind.SkipLocalAlpha(icn));
    }

    public static Bitmap GetICN(int icn, int index) {
        return GetICN(icn, index, false);
    }

    public static Bitmap GetICN(int icn, int index, boolean reflect) {
        var v = StaticInstance.icn_cache.get(icn);
        if (v.sprites == null) {
            byte[] body = ReadChunk(IcnKind.GetString(icn));

            if (body.length == 0)
                return null;
            var bvr = new ByteVectorReader(body);
            var count = bvr.getLE16();
            v.sprites = new Sprite[count];
            v.reflect = new Sprite[count];
        }

        var icnBmp = reflect ? v.reflect[index] : v.sprites[index];
        if (icnBmp == null || !icnBmp.isValid()) {
            icnBmp = LoadOrgICN(icn, index, reflect);
            if (reflect)
                v.reflect[index] = icnBmp;
            else v.sprites[index] = icnBmp;

        }
        return icnBmp;
    }

    public static byte[] Read(String file) {
        return ReadChunk(file);
    }

    public static byte[] ReadChunk(String file) {
        var result = Get().mainAgg.Read(file);
        if (result != null)
            return result;
        return Get().aggX.Read(file);
    }

    public void setup() {
        if (!FileUtils.Exists("DATA/HEROES2.AGG")) {
            ResourceDownloader.downloadAllPacks(".");
            return;
        }
        mainAgg.Open("DATA/HEROES2.AGG");

        var fname = "DATA/HEROES2X.AGG";
        if (FileUtils.Exists(fname)) {
            aggX.Open(fname);
        }

        Init();

    }

    private void Init() {
        IntStream.range(0, IcnKind.LASTICN + 256).forEach(i -> icn_cache.add(new icn_cache_t()));
        IntStream.range(0, TilKind.LASTTIL).forEach(i -> til_cache.add(new til_cache_t()));
    }

    void LoadICN(int icn, int index, boolean reflect) {

    }

    public int IcnSpriteCount(int icn) {
        var body = ReadICNChunk(icn);
        if (body.length == 0) return 0;

        var st = new ByteVectorReader(body);

        return st.getLE16();
    }

    public byte[] LoadMID(int xmi) {
        var body = ReadChunk(XmiKind.GetString(xmi));
        return Music.Xmi2Mid(body);
    }
}
