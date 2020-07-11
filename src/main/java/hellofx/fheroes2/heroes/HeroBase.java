package hellofx.fheroes2.heroes;

import hellofx.fheroes2.army.Army;
import hellofx.fheroes2.castle.Castle;
import hellofx.fheroes2.kingdom.ColorBase;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.maps.MapPosition;
import hellofx.fheroes2.resource.Artifact;
import hellofx.fheroes2.resource.BagArtifacts;
import hellofx.fheroes2.spell.SpellBook;
import hellofx.fheroes2.system.BitModes;
import hellofx.fheroes2.system.Settings;

import static hellofx.fheroes2.agg.icn.IcnKind.*;

public abstract class HeroBase {
    public BitModes bitModes = new BitModes();
    public MapPosition mapPosition = new MapPosition();
    public ColorBase color = new ColorBase();

    public Primary primary = new Primary();


    int magic_point;
    int move_point;

    SpellBook spell_book = new SpellBook();
    BagArtifacts bag_artifacts = new BagArtifacts();

    public HeroBase(int type, int race) {
        LoadDefaults(type, race);
    }

    public void LoadDefaults(int type, int race) {
    }

    public int GetMoraleKindModificator(String strs) {

        int result = ArtifactsModifiersMorale(this, strs);

        // check castle modificator
        Castle castle = inCastle();

        if (castle != null)
            result += castle.GetMoraleModificator(strs);

        // army modificator
        if (GetArmy().m_troops.AllTroopsIsRace(RaceKind.NECR)) {
            if (strs != null) strs = "";
            result = 0;
        }

        result += GetArmy().GetMoraleModificator(strs);

        return result;
    }

    protected abstract Army GetArmy();

    protected abstract Castle inCastle();

    private int ArtifactsModifiersMorale(HeroBase heroBase, String strs) {
        //TODO
        return 0;
    }

    public static int PORTxxxx(int heroId) {
        return switch (heroId) {
            case HeroesKind.LORDKILBURN -> PORT0000;
            case HeroesKind.SIRGALLANTH -> PORT0001;
            case HeroesKind.ECTOR -> PORT0002;
            case HeroesKind.GVENNETH -> PORT0003;
            case HeroesKind.TYRO -> PORT0004;
            case HeroesKind.AMBROSE -> PORT0005;
            case HeroesKind.RUBY -> PORT0006;
            case HeroesKind.MAXIMUS -> PORT0007;
            case HeroesKind.DIMITRY -> PORT0008;
            case HeroesKind.THUNDAX -> PORT0009;
            case HeroesKind.FINEOUS -> PORT0010;
            case HeroesKind.JOJOSH -> PORT0011;
            case HeroesKind.CRAGHACK -> PORT0012;
            case HeroesKind.JEZEBEL -> PORT0013;
            case HeroesKind.JACLYN -> PORT0014;
            case HeroesKind.ERGON -> PORT0015;
            case HeroesKind.TSABU -> PORT0016;
            case HeroesKind.ATLAS -> PORT0017;
            case HeroesKind.ASTRA -> PORT0018;
            case HeroesKind.NATASHA -> PORT0019;
            case HeroesKind.TROYAN -> PORT0020;
            case HeroesKind.VATAWNA -> PORT0021;
            case HeroesKind.REBECCA -> PORT0022;
            case HeroesKind.GEM -> PORT0023;
            case HeroesKind.ARIEL -> PORT0024;
            case HeroesKind.CARLAWN -> PORT0025;
            case HeroesKind.LUNA -> PORT0026;
            case HeroesKind.ARIE -> PORT0027;
            case HeroesKind.ALAMAR -> PORT0028;
            case HeroesKind.VESPER -> PORT0029;
            case HeroesKind.CRODO -> PORT0030;
            case HeroesKind.BAROK -> PORT0031;
            case HeroesKind.KASTORE -> PORT0032;
            case HeroesKind.AGAR -> PORT0033;
            case HeroesKind.FALAGAR -> PORT0034;
            case HeroesKind.WRATHMONT -> PORT0035;
            case HeroesKind.MYRA -> PORT0036;
            case HeroesKind.FLINT -> PORT0037;
            case HeroesKind.DAWN -> PORT0038;
            case HeroesKind.HALON -> PORT0039;
            case HeroesKind.MYRINI -> PORT0040;
            case HeroesKind.WILFREY -> PORT0041;
            case HeroesKind.SARAKIN -> PORT0042;
            case HeroesKind.KALINDRA -> PORT0043;
            case HeroesKind.MANDIGAL -> PORT0044;
            case HeroesKind.ZOM -> PORT0045;
            case HeroesKind.DARLANA -> PORT0046;
            case HeroesKind.ZAM -> PORT0047;
            case HeroesKind.RANLOO -> PORT0048;
            case HeroesKind.CHARITY -> PORT0049;
            case HeroesKind.RIALDO -> PORT0050;
            case HeroesKind.ROXANA -> PORT0051;
            case HeroesKind.SANDRO -> PORT0052;
            case HeroesKind.CELIA -> PORT0053;
            case HeroesKind.ROLAND -> PORT0054;
            case HeroesKind.CORLAGON -> PORT0055;
            case HeroesKind.ELIZA -> PORT0056;
            case HeroesKind.ARCHIBALD -> PORT0057;
            case HeroesKind.HALTON -> PORT0058;
            case HeroesKind.BAX -> PORT0059;
            case HeroesKind.SOLMYR -> PORT0060;
            case HeroesKind.DAINWIN -> PORT0061;
            case HeroesKind.MOG -> PORT0062;
            case HeroesKind.UNCLEIVAN -> PORT0063;
            case HeroesKind.JOSEPH -> PORT0064;
            case HeroesKind.GALLAVANT -> PORT0065;
            case HeroesKind.ELDERIAN -> PORT0066;
            case HeroesKind.CEALLACH -> PORT0067;
            case HeroesKind.DRAKONIA -> PORT0068;
            case HeroesKind.MARTINE -> PORT0069;
            case HeroesKind.JARKONAS -> PORT0070;
            case HeroesKind.SANDYSANDY -> PORT0059;
            default -> UNKNOWN;
        };
    }

    public abstract int GetType();

    public abstract int GetRace();

    public boolean isHeroes() {
        return GetType() == HeroType.HEROES;
    }

    public boolean HasArtifact(int artId) {
        var art = new Artifact(artId);
        boolean unique = true;

        switch (art.Type()) {
            case 1:
                unique = Settings.Get().ExtWorldUseUniqueArtifactsML();
                break; /* morale/luck arts. */
            case 2:
                unique = Settings.Get().ExtWorldUseUniqueArtifactsRS();
                break; /* resource affecting arts. */
            case 3:
                unique = Settings.Get().ExtWorldUseUniqueArtifactsPS();
                break; /* primary/mp/sp arts. */
            case 4:
                unique = Settings.Get().ExtWorldUseUniqueArtifactsSS();
                break; /* sec. skills arts. */
            default:
                break;
        }

        return !unique ? bag_artifacts.Count(art) != 0 : bag_artifacts.isPresentArtifact(art);
    }

    public boolean isCaptain() {
        return GetType() == HeroType.CAPTAIN;
    }

    public abstract boolean isValid();
}
