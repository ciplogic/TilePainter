package hellofx.fheroes2.heroes;

import hellofx.fheroes2.maps.objects.MapPosition;
import hellofx.fheroes2.system.BitModes;

import static hellofx.fheroes2.agg.IcnKind.*;

public class HeroBase {
    public BitModes bitModes = new BitModes();
    public MapPosition mapPosition = new MapPosition();
    public Primary primary = new Primary();

    public static int PORTxxxx(int heroId) {
        return switch (heroId) {
            case HeroesKinds.LORDKILBURN -> PORT0000;
            case HeroesKinds.SIRGALLANTH -> PORT0001;
            case HeroesKinds.ECTOR -> PORT0002;
            case HeroesKinds.GVENNETH -> PORT0003;
            case HeroesKinds.TYRO -> PORT0004;
            case HeroesKinds.AMBROSE -> PORT0005;
            case HeroesKinds.RUBY -> PORT0006;
            case HeroesKinds.MAXIMUS -> PORT0007;
            case HeroesKinds.DIMITRY -> PORT0008;
            case HeroesKinds.THUNDAX -> PORT0009;
            case HeroesKinds.FINEOUS -> PORT0010;
            case HeroesKinds.JOJOSH -> PORT0011;
            case HeroesKinds.CRAGHACK -> PORT0012;
            case HeroesKinds.JEZEBEL -> PORT0013;
            case HeroesKinds.JACLYN -> PORT0014;
            case HeroesKinds.ERGON -> PORT0015;
            case HeroesKinds.TSABU -> PORT0016;
            case HeroesKinds.ATLAS -> PORT0017;
            case HeroesKinds.ASTRA -> PORT0018;
            case HeroesKinds.NATASHA -> PORT0019;
            case HeroesKinds.TROYAN -> PORT0020;
            case HeroesKinds.VATAWNA -> PORT0021;
            case HeroesKinds.REBECCA -> PORT0022;
            case HeroesKinds.GEM -> PORT0023;
            case HeroesKinds.ARIEL -> PORT0024;
            case HeroesKinds.CARLAWN -> PORT0025;
            case HeroesKinds.LUNA -> PORT0026;
            case HeroesKinds.ARIE -> PORT0027;
            case HeroesKinds.ALAMAR -> PORT0028;
            case HeroesKinds.VESPER -> PORT0029;
            case HeroesKinds.CRODO -> PORT0030;
            case HeroesKinds.BAROK -> PORT0031;
            case HeroesKinds.KASTORE -> PORT0032;
            case HeroesKinds.AGAR -> PORT0033;
            case HeroesKinds.FALAGAR -> PORT0034;
            case HeroesKinds.WRATHMONT -> PORT0035;
            case HeroesKinds.MYRA -> PORT0036;
            case HeroesKinds.FLINT -> PORT0037;
            case HeroesKinds.DAWN -> PORT0038;
            case HeroesKinds.HALON -> PORT0039;
            case HeroesKinds.MYRINI -> PORT0040;
            case HeroesKinds.WILFREY -> PORT0041;
            case HeroesKinds.SARAKIN -> PORT0042;
            case HeroesKinds.KALINDRA -> PORT0043;
            case HeroesKinds.MANDIGAL -> PORT0044;
            case HeroesKinds.ZOM -> PORT0045;
            case HeroesKinds.DARLANA -> PORT0046;
            case HeroesKinds.ZAM -> PORT0047;
            case HeroesKinds.RANLOO -> PORT0048;
            case HeroesKinds.CHARITY -> PORT0049;
            case HeroesKinds.RIALDO -> PORT0050;
            case HeroesKinds.ROXANA -> PORT0051;
            case HeroesKinds.SANDRO -> PORT0052;
            case HeroesKinds.CELIA -> PORT0053;
            case HeroesKinds.ROLAND -> PORT0054;
            case HeroesKinds.CORLAGON -> PORT0055;
            case HeroesKinds.ELIZA -> PORT0056;
            case HeroesKinds.ARCHIBALD -> PORT0057;
            case HeroesKinds.HALTON -> PORT0058;
            case HeroesKinds.BAX -> PORT0059;
            case HeroesKinds.SOLMYR -> PORT0060;
            case HeroesKinds.DAINWIN -> PORT0061;
            case HeroesKinds.MOG -> PORT0062;
            case HeroesKinds.UNCLEIVAN -> PORT0063;
            case HeroesKinds.JOSEPH -> PORT0064;
            case HeroesKinds.GALLAVANT -> PORT0065;
            case HeroesKinds.ELDERIAN -> PORT0066;
            case HeroesKinds.CEALLACH -> PORT0067;
            case HeroesKinds.DRAKONIA -> PORT0068;
            case HeroesKinds.MARTINE -> PORT0069;
            case HeroesKinds.JARKONAS -> PORT0070;
            case HeroesKinds.SANDYSANDY -> PORT0059;
            default -> UNKNOWN;
        };
    }
}
