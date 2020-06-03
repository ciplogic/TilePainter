package hellofx.fheroes2.agg;

import hellofx.fheroes2.common.H2Size;
import hellofx.fheroes2.common.MutPoint;
import hellofx.fheroes2.icns.IcnHeader;
import hellofx.fheroes2.serialize.ByteVectorReader;

import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class AggPaint {
    public static Bitmap RenderImageFromArray(byte[] body, int startIndex, int width, int height) {
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

    public static IcnSprite RenderICNSprite(Agg agg, int icn, int index) {
        var body = agg.ReadICNChunk(icn);
        if (body.length == 0) return new IcnSprite();
        var res = new IcnSprite();

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

    private static void Render(IcnSprite res, IcnHeader header1, byte[] body, int sizeData) {
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

}
