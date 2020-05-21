package hellofx.fheroes2.agg;

import hellofx.fheroes2.common.H2Size;
import hellofx.fheroes2.common.MutPoint;
import hellofx.fheroes2.icns.IcnHeader;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.serialize.FileUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class AggFile {
    private static final int FATSIZENAME = 15;

    private final Map<String, AggFat> fat = new TreeMap<>();

    private String filename;
    private ByteVectorReader stream;


    public boolean Open(String aggFileName) {
        filename = aggFileName;
        if (!FileUtils.Exists(aggFileName))
            return false;

        var fileContent = FileUtils.ReadAllBytes(filename);
        var size = fileContent.length;
        stream = new ByteVectorReader(fileContent);

        var count_items = stream.getLE16();

        stream.seek(size - FATSIZENAME * count_items);
        var vectorNames = new ArrayList<String>(count_items);

        for (var ii = 0; ii < count_items; ++ii) vectorNames.add(stream.toString(FATSIZENAME));

        stream.seek(2);
        fat.clear();
        for (var ii = 0; ii < count_items; ++ii) {
            var itemName = vectorNames.get(ii);
            var f = new AggFat();
            var crc = stream.getLE32();
            f.crc = crc;
            var offset = stream.getLE32();
            f.offset = offset;
            var sizeChunk = stream.getLE32();
            f.size = sizeChunk;
            fat.put(itemName, f);
        }

        return true;
    }

    public byte[] Read(String fileName) {
        var f = fat.get(fileName);
        if (f == null) return new byte[0];
        if (f.size == 0) return new byte[0];

        stream.seek(f.offset);
        var body = stream.getRaw(f.size);
        return body;
    }


    private byte[] ReadICNChunk(int icn) {
        // hard fix artifact "ultimate stuff" sprite for loyalty version
        return Read(IcnKind.GetString(icn));
    }
    public int IcnSpriteCount(int icn) {
        var body = ReadICNChunk(icn);
        if (body.length == 0) return 0;

        var st = new ByteVectorReader(body);

        var count = st.getLE16();
        return count;
    }

    public IcnSprite RenderICNSprite(int icn, int index) {
        var res = new IcnSprite();

        var body = ReadICNChunk(icn);
        if (body.length == 0) return res;

        var st = new ByteVectorReader(body);

        var count = st.getLE16();
        if (index >= count) return res;

        var blockSize = st.getLE32();

        if (index != 0) st.skip(index * 13);

        var header1 = IcnHeader.ReadData(st);

        int sizeData = 0;
        if (index + 1 != count) {
            var header2 = IcnHeader.ReadData(st);
            sizeData = header2.offsetData - header1.offsetData;
        } else {
            sizeData = blockSize - header1.offsetData;
        }

        Render(res, header1, body, sizeData);

        return res;
    }

    private void Render(IcnSprite res, IcnHeader header1, byte[] body, int sizeData) {
        // start render
        var sz = new H2Size(header1.width, header1.height);

        int bufPos = 6 + header1.offsetData;
        var max = bufPos + sizeData;

        res.offset = new MutPoint(header1.offsetX, header1.offsetY);
        res.SetSize(true, sz.Width, sz.Height, false);

        var shadowCol = H2RgbaColor.FromArgb(0, 0, 0, 0x40);

        var pt = new MutPoint();

        while (true) {
            var cur = toByte(body[bufPos]);
            // 0x00 - end line
            if (0 == cur) {
                ++pt.y;
                pt.x = 0;
                bufPos++;
            } else
            // 0x7F - count data
            {
                int c;
                if (0x80 > cur) {
                    c = body[bufPos];
                    ++bufPos;
                    while (c-- != 0 && bufPos < max) {
                        res.first.DrawPointPalette(pt.x, pt.y, body[bufPos]);
                        ++pt.x;
                        ++bufPos;
                    }
                } else
                    // 0x80 - end data
                    if (0x80 == cur) {
                        break;
                    } else
                        // 0xBF - skip data
                        if (0xC0 > cur) {
                            pt.x += cur - 0x80;
                            ++bufPos;
                        } else
                            // 0xC0 - shadow
                            if (0xC0 == cur) {
                                ++bufPos;
                                if (toByte(body[bufPos]) % 4 != 0) {
                                    c = toByte(body[bufPos]) % 4;
                                } else {
                                    ++bufPos;
                                    c = toByte(body[bufPos]);
                                }

                                if (res.second == null) res.SetSize(false, sz.Width, sz.Height, true);

                                while (c-- != 0) {
                                    res.second.SetPixel(pt.x, pt.y, shadowCol);
                                    ++pt.x;
                                }

                                ++bufPos;
                            } else
                                // 0xC1
                                if (0xC1 == cur) {
                                    ++bufPos;
                                    c = toByte(body[bufPos]);
                                    ++bufPos;
                                    while (c-- != 0) {
                                        res.first.DrawPointPalette(pt.x, pt.y, body[bufPos]);
                                        ++pt.x;
                                    }

                                    ++bufPos;
                                } else {
                                    c = cur - 0xC0;
                                    ++bufPos;
                                    while (c-- != 0) {
                                        res.first.DrawPointPalette(pt.x, pt.y, body[bufPos]);
                                        ++pt.x;
                                    }

                                    ++bufPos;
                                }
            }

            if (bufPos >= max) break;
        }
    }


    public Bitmap RenderImageFromArray(byte[] body, int startIndex, int width, int height) {
        var imgNumbers = new Bitmap(width, height);
        var pos = startIndex;
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                imgNumbers.DrawPointPalette(x, y, body[pos]);
                pos++;
            }
        }
        return imgNumbers;

    }
}
