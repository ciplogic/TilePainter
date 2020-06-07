package hellofx.fheroes2.agg;

import hellofx.fheroes2.agg.cache.icn_cache_t;
import hellofx.fheroes2.agg.cache.til_cache_t;
import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.common.music.Music;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.serialize.FileUtils;
import hellofx.fheroes2.serialize.ResourceDownloader;
import hellofx.framework.MainContext;
import javafx.scene.image.Image;

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

    /**
     * In FHeroes2 it was returning false, now it would return null if nothing is to render
     *
     * @param icn
     * @param index
     * @param reflect
     * @return
     */
    static Bitmap LoadOrgICN(Agg agg, int icn, int index, boolean reflect) {
        IcnSprite icnSprite = AggPaint.RenderICNSprite(agg, icn, index);
        if (!icnSprite.isValid())
            return null;
        return icnSprite.CreateSprite(reflect, !IcnKind.SkipLocalAlpha(icn));
    }

    public static Bitmap GetICN(int icn, int index) {
        return GetICN(icn, index, false);
    }

    public static Bitmap GetICN(int icn, int index, boolean reflect) {
        var agg = Agg.Get();
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
            icnBmp = LoadOrgICN(agg, icn, index, reflect);
            if (reflect)
                v.reflect[index] = icnBmp;
            else v.sprites[index] = icnBmp;

        }
        return icnBmp;
    }

    public static Image GetTIL(int til, int index, int shape) {
        var agg = StaticInstance;
        while (agg.til_cache.size() <= til)
            agg.til_cache.add(new til_cache_t());
        var tilCache = agg.til_cache.get(til);
        if (0 == tilCache.count) agg.LoadTIL(til);

        int index2 = index;

        if (shape != 0) {
            switch (til) {
                case TilKind.STON:
                    index2 += 36 * (shape % 4);
                    break;
                case TilKind.CLOF32:
                    index2 += 4 * (shape % 4);
                    break;
                case TilKind.GROUND32:
                    index2 += 432 * (shape % 4);
                    break;
                default:
                    break;
            }
        }
        if (index2 >= tilCache.count) {
            index2 = 0;
        }
        var surface = tilCache.sprites[index2];
        if (Bitmap.isValid(surface) || (shape == 0)) {
            return tilCache.getImage(index2);
        }
        var src = tilCache.sprites[index];
        var result = surface;
        result = Bitmap.RenderReflect(src, shape);
        tilCache.setSprite(index2, result);
        return tilCache.getImage(index2);
    }

    private void LoadTIL(int til) {

        til_cache_t v = til_cache.get(til);

        if (v.sprites != null)
            return;
        var max = 0;

        switch (til) {
            case TilKind.CLOF32:
                max = 4;
                break;
            case TilKind.GROUND32:
                max = 432;
                break;
            case TilKind.STON:
                max = 36;
                break;
            default:
                break;
        }
        // reserve for rotate sprites
        v.setSize(max * 4);
        LoadOrgTIL(til, max);
    }

    private boolean LoadOrgTIL(int til, int max) {
        var tileName = TilKind.GetString(til);
        var body = ReadChunk(tileName);

        if (body.length == 0)
            return false;
        ByteVectorReader st = new ByteVectorReader(body);

        var count = st.getLE16();
        var width = st.getLE16();
        var height = st.getLE16();

        var tile_size = width * height;
        var body_size = 6 + count * tile_size;

        til_cache_t v = til_cache.get(til);

        // check size
        if (body.length != body_size || count > max) {
            return false;
        }
        for (var ii = 0; ii < count; ++ii) {
            var bmp = new Bitmap(width, height);
            var pos = 6 + ii * tile_size;
            for (var y = 0; y < height; y++) {
                for (var x = 0; x < width; x++) {
                    bmp.DrawPointPalette(x, y, body[pos]);
                    pos++;
                }
            }
            v.setSprite(ii, bmp);
        }
        return true;
    }

    public byte[] ReadICNChunk(int icn) {
        // hard fix artifact "ultimate stuff" sprite for loyalty version
        return Read(IcnKind.GetString(icn));
    }

    public byte[] Read(String file) {
        return ReadChunk(file);
    }

    public static byte[] ReadChunk(String file) {
        var agg = Get();
        var result = agg.mainAgg.Read(file);
        if (result != null)
            return result;
        return agg.aggX.Read(file);
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

    public byte[] LoadMUS(int mus) {
        var xmi = XmiKind.FromMUS(mus);
        return LoadMID(xmi);
    }

    public byte[] LoadMID(int xmi) {
        var fileName = XmiKind.GetString(xmi);
        var body = ReadChunk(fileName);
        return Music.Xmi2Mid(body);
    }
}
