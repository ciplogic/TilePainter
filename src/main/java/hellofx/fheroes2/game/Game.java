package hellofx.fheroes2.game;

import hellofx.fheroes2.army.Army;
import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.heroes.SkillLevel;

import static hellofx.fheroes2.common.Tools.GetStringShort;
import static hellofx.fheroes2.common.Tools.Int2Str;

public class Game {
    private static int animationFrame;

    public static int MapsAnimationFrame() {
        return animationFrame;
    }

    public static void nextFrame() {
        animationFrame++;
    }

    public static String GetEncodeString(String text) {
        return text;
    }


    String CountScoute(int count, int scoute, boolean shorts) {
        double infelicity = 0;
        String res = "";

        switch (scoute) {
            case SkillLevel.BASIC:
                infelicity = count * 30 / 100.0;
                break;

            case SkillLevel.ADVANCED:
                infelicity = count * 15 / 100.0;
                break;

            case SkillLevel.EXPERT:
                res = shorts ? GetStringShort(count) : Int2Str(count);
                break;

            default:
                return Army.SizeString(count);
        }

        if (res.length() == 0) {
            int min = Rand.Get((int) Math.floor(count - infelicity + 0.5),
                    (int) Math.floor(count + infelicity + 0.5));
            int max = 0;

            if (min > count) {
                max = min;
                min = (int) Math.floor(count - infelicity + 0.5);
            } else
                max = (int) Math.floor(count + infelicity + 0.5);

            res = Int2Str(min);

            if (min != max) {
                res += "-";
                res += Int2Str(max);
            }
        }

        return res;
    }

}
