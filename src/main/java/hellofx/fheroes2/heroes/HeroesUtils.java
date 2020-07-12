package hellofx.fheroes2.heroes;

public class HeroesUtils {
    /* calc level from exp */
    public static int GetLevelFromExperience(int exp) {
        for (int lvl = 1; lvl < 255; ++lvl) if (exp < GetExperienceFromLevel(lvl)) return lvl;

        return 0;
    }

    public static int GetExperienceFromLevel(int lvl) {

        switch (lvl) {
            case 0:
                return 0;
            case 1:
                return 1000;
            case 2:
                return 2000;
            case 3:
                return 3200;
            case 4:
                return 4500;
            case 5:
                return 6000;
            case 6:
                return 7700;
            case 7:
                return 9000;
            case 8:
                return 11000;
            case 9:
                return 13200;
            case 10:
                return 15500;
            case 11:
                return 18500;
            case 12:
                return 22100;
            case 13:
                return 26400;
            case 14:
                return 31600;
            case 15:
                return 37800;
            case 16:
                return 45300;
            case 17:
                return 54200;
            case 18:
                return 65000;
            case 19:
                return 78000;
            case 20:
                return 93600;
            case 21:
                return 112300;
            case 22:
                return 134700;
            case 23:
                return 161600;
            case 24:
                return 193900;
            case 25:
                return 232700;
            case 26:
                return 279300;
            case 27:
                return 335200;
            case 28:
                return 402300;
            case 29:
                return 482800;
            case 30:
                return 579400;
            case 31:
                return 695300;
            case 32:
                return 834400;
            case 33:
                return 1001300;
            case 34:
                return 1201600;
            case 35:
                return 1442000;
            case 36:
                return 1730500;
            case 37:
                return 2076700;
            case 38:
                return 2492100;
            case 39:
                return 2990600;

            default:
                break;
        }

        var l1 = GetExperienceFromLevel(lvl - 1);
        var l2 = GetExperienceFromLevel(lvl - 2);
        return l1 + (l1 - l2) * 120 / 100;
    }
}
