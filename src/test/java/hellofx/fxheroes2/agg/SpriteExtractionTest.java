package hellofx.fxheroes2.agg;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Sprite;
import hellofx.fheroes2.agg.icn.IcnKind;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpriteExtractionTest {
    private static Agg agg;

    @BeforeAll
    public static void initTests() {
        agg = Agg.Get();
        agg.setup();
    }
    
//    @Test
    void testWriteSpritesToDisk() {
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

    @Test
    void testCreatureSizeShouldBeNormal() {
        Sprite sprite = agg.GetICN(227, 38);
        sprite = agg.GetICN(227, 39);

        assertTrue(sprite.Width < 100,
                String.format(
                        "The icon width was supposed to be less than a 100 pixels but it was %d pixels",
                        sprite.Width
                ));
        assertTrue(sprite.Height < 100,
                String.format(
                        "The icon height was supposed to be less than a 100 pixels but it was %d pixels",
                        sprite.Height
                ));
    }

    @Test
    void testHeroSizeShouldBeNormal() {
        Sprite sprite = agg.GetICN(106, 14);
        sprite = agg.GetICN(106, 15);


        assertTrue(sprite.Width < 100,
                String.format(
                        "The icon width was supposed to be less than a 100 pixels but it was %d pixels",
                        sprite.Width
                ));
        assertTrue(sprite.Height < 100,
                String.format(
                        "The icon height was supposed to be less than a 100 pixels but it was %d pixels",
                        sprite.Height
                ));
    }
}
