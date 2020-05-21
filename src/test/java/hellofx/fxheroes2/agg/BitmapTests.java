package hellofx.fxheroes2.agg;

import hellofx.fheroes2.agg.Pixel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BitmapTests {
    @Test
    void testArgb() {
        Assertions.assertEquals(255, Pixel.FromArgb(0, 0, 255, 0));
        Assertions.assertEquals(65535, Pixel.FromArgb(0, 255, 255, 0));
    }
}
