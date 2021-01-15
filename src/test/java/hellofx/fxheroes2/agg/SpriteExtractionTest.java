package hellofx.fxheroes2.agg;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Sprite;
import hellofx.fheroes2.agg.icn.IcnKind;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SpriteExtractionTest {
    @Test
    void testSpriteReading() {
        Agg agg = Agg.Get();
        agg.setup();

        for (int i = 0; i < IcnKind.LASTICN; i++) {
            int spritesPerIcon = agg.IcnSpriteCount(i);
            for (int j = 0; j < spritesPerIcon; j++) {
                Sprite sprite = agg.GetICN(i, j);

                String fileName = String.format(
                        "/tmp/h2/pic_%d_%d.png",
                        i,
                        j
                );
                sprite.saveToFile(new File(fileName));
            }
        }
    }
}
