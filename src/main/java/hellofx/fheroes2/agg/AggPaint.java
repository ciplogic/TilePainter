package hellofx.fheroes2.agg;

import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.H2Size;
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
        var iconSprite = new IcnSprite();

        var st = new ByteVectorReader(body);

        var count = st.getLE16();
        if (index >= count) return iconSprite;

        var blockSize = st.getLE32();

        if (index != 0) st.skip(index * 13);

        var iconHeader = IcnHeader.ReadData(st);

        int sizeData = 0;
        if (index + 1 != count) {
            var header2 = IcnHeader.ReadData(st);
            sizeData = header2.offsetData - iconHeader.offsetData;
        } else {
            sizeData = blockSize - iconHeader.offsetData;
        }

        Render(iconSprite, iconHeader, body, sizeData);

        return iconSprite;
    }

    /**
     * Decodes the information from the body, and populates the icon sprite information.
     * The IcnSprite is divided in a `first` icon, that's the actual image, and
     * `second`, that's the shadow. It also includes an offset that points to
     * the place where the image should be rendered.
     * @param iconSprite The icon sprite that will get its first, second and offset
     *                  data populated/
     * @param iconHeader
     * @param body
     * @param sizeData
     */
    private static void Render(IcnSprite iconSprite, IcnHeader iconHeader, byte[] body, int sizeData) {
        // start render
        var sz = new H2Size(iconHeader.width, iconHeader.height);

        int bufPos = 6 + iconHeader.offsetData;
        var max = bufPos + sizeData;

        iconSprite.offset = new H2Point(iconHeader.offsetX, iconHeader.offsetY);
        iconSprite.SetSize(true, sz.w, sz.h, false);

        var shadowCol = H2RgbaColor.FromArgb(0, 0, 0, 0x40);

        var pt = new H2Point();

        while (true) {
            var cur = toByte(body[bufPos]);
            int c;

            if (0 == cur) { // 0x00 - end line
                ++pt.y;
                pt.x = 0;
                bufPos++;
            } else if (0x80 > cur) { // 0x7F - count data
                c = body[bufPos];
                ++bufPos;
                while (c-- != 0 && bufPos < max) {
                    iconSprite.first.DrawPointPalette(pt.x, pt.y, body[bufPos]);
                    ++pt.x;
                    ++bufPos;
                }
            } else if (0x80 == cur) { // 0x80 - end data
                break;
            } else if (0xC0 > cur) { // 0xBF - skip data
                pt.x += cur - 0x80;
                ++bufPos;
            } else if (0xC0 == cur) { // 0xC0 - shadow
                ++bufPos;
                if (toByte(body[bufPos]) % 4 != 0) {
                    c = toByte(body[bufPos]) % 4;
                } else {
                    ++bufPos;
                    c = toByte(body[bufPos]);
                }

                if (iconSprite.second == null) iconSprite.SetSize(false, sz.w, sz.h, true);

                while (c-- != 0) {
                    iconSprite.second.SetPixel(pt.x, pt.y, shadowCol);
                    ++pt.x;
                }

                ++bufPos;
            } else if (0xC1 == cur) {
                ++bufPos;
                c = toByte(body[bufPos]);
                ++bufPos;
                while (c-- != 0) {
                    iconSprite.first.DrawPointPalette(pt.x, pt.y, body[bufPos]);
                    ++pt.x;
                }

                ++bufPos;
            } else {
                c = cur - 0xC0;
                ++bufPos;
                while (c-- != 0) {
                    iconSprite.first.DrawPointPalette(pt.x, pt.y, body[bufPos]);
                    ++pt.x;
                }

                ++bufPos;
            }

            if (bufPos >= max) break;
        }
    }
}
