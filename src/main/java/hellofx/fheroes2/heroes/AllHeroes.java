package hellofx.fheroes2.heroes;

import hellofx.fheroes2.castle.Castle;
import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.game.GameConsts;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.system.Settings;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import static hellofx.common.Utilities.find_if;

public class AllHeroes extends VecHeroes {

    public void Init() {


        var loyalty = Settings.Get().PriceLoyaltyVersion();

        // knight: LORDKILBURN, SIRGALLANTH, ECTOR, GVENNETH, TYRO, AMBROSE, RUBY, MAXIMUS, DIMITRY
        for (var hid = HeroesKind.LORDKILBURN; hid <= HeroesKind.DIMITRY; ++hid)
            _items.add(new Heroes(hid, RaceKind.KNGT));

        // barbarian: THUNDAX, FINEOUS, JOJOSH, CRAGHACK, JEZEBEL, JACLYN, ERGON, TSABU, ATLAS
        for (var hid = HeroesKind.THUNDAX; hid <= HeroesKind.ATLAS; ++hid)
            _items.add(new Heroes(hid, RaceKind.BARB));

        // sorceress: ASTRA, NATASHA, TROYAN, VATAWNA, REBECCA, GEM, ARIEL, CARLAWN, LUNA
        for (var hid = HeroesKind.ASTRA; hid <= HeroesKind.LUNA; ++hid)
            _items.add(new Heroes(hid, RaceKind.SORC));

        // warlock: ARIE, ALAMAR, VESPER, CRODO, BAROK, KASTORE, AGAR, FALAGAR, WRATHMONT
        for (var hid = HeroesKind.ARIE; hid <= HeroesKind.WRATHMONT; ++hid)
            _items.add(new Heroes(hid, RaceKind.WRLK));

        // wizard: MYRA, FLINT, DAWN, HALON, MYRINI, WILFREY, SARAKIN, KALINDRA, MANDIGAL
        for (var hid = HeroesKind.MYRA; hid <= HeroesKind.MANDIGAL; ++hid)
            _items.add(new Heroes(hid, RaceKind.WZRD));

        // necromancer: ZOM, DARLANA, ZAM, RANLOO, CHARITY, RIALDO, ROXANA, SANDRO, CELIA
        for (var hid = HeroesKind.ZOM; hid <= HeroesKind.CELIA; ++hid)
            _items.add(new Heroes(hid, RaceKind.NECR));

        // from campain
        _items.add(new Heroes(HeroesKind.ROLAND, RaceKind.WZRD));
        _items.add(new Heroes(HeroesKind.CORLAGON, RaceKind.KNGT));
        _items.add(new Heroes(HeroesKind.ELIZA, RaceKind.SORC));
        _items.add(new Heroes(HeroesKind.ARCHIBALD, RaceKind.WRLK));
        _items.add(new Heroes(HeroesKind.HALTON, RaceKind.KNGT));
        _items.add(new Heroes(HeroesKind.BAX, RaceKind.NECR));

        // loyalty version
        _items.add(new Heroes(loyalty ? HeroesKind.SOLMYR : HeroesKind.UNKNOWN, RaceKind.WZRD));
        _items.add(new Heroes(loyalty ? HeroesKind.DAINWIN : HeroesKind.UNKNOWN, RaceKind.WRLK));
        _items.add(new Heroes(loyalty ? HeroesKind.MOG : HeroesKind.UNKNOWN, RaceKind.NECR));
        _items.add(new Heroes(loyalty ? HeroesKind.UNCLEIVAN : HeroesKind.UNKNOWN, RaceKind.BARB));
        _items.add(new Heroes(loyalty ? HeroesKind.JOSEPH : HeroesKind.UNKNOWN, RaceKind.KNGT));
        _items.add(new Heroes(loyalty ? HeroesKind.GALLAVANT : HeroesKind.UNKNOWN, RaceKind.KNGT));
        _items.add(new Heroes(loyalty ? HeroesKind.ELDERIAN : HeroesKind.UNKNOWN, RaceKind.WRLK));
        _items.add(new Heroes(loyalty ? HeroesKind.CEALLACH : HeroesKind.UNKNOWN, RaceKind.KNGT));
        _items.add(new Heroes(loyalty ? HeroesKind.DRAKONIA : HeroesKind.UNKNOWN, RaceKind.WZRD));
        _items.add(new Heroes(loyalty ? HeroesKind.MARTINE : HeroesKind.UNKNOWN, RaceKind.SORC));
        _items.add(new Heroes(loyalty ? HeroesKind.JARKONAS : HeroesKind.UNKNOWN, RaceKind.BARB));

        // devel
        _items.add(new Heroes(Settings.Get().IS_DEVEL() ? HeroesKind.SANDYSANDY : HeroesKind.UNKNOWN, RaceKind.WRLK));
        _items.add(new Heroes(HeroesKind.UNKNOWN, RaceKind.KNGT));
    }

    public Heroes GetFreeman(int race) {
        var conf = Settings.Get();

        int min = HeroesKind.UNKNOWN;
        int max = HeroesKind.UNKNOWN;

        switch (race) {
            case RaceKind.KNGT:
                min = HeroesKind.LORDKILBURN;
                max = HeroesKind.DIMITRY;
                break;

            case RaceKind.BARB:
                min = HeroesKind.THUNDAX;
                max = HeroesKind.ATLAS;
                break;

            case RaceKind.SORC:
                min = HeroesKind.ASTRA;
                max = HeroesKind.LUNA;
                break;

            case RaceKind.WRLK:
                min = HeroesKind.ARIE;
                max = HeroesKind.WRATHMONT;
                break;

            case RaceKind.WZRD:
                min = HeroesKind.MYRA;
                max = HeroesKind.MANDIGAL;
                break;

            case RaceKind.NECR:
                min = HeroesKind.ZOM;
                max = HeroesKind.CELIA;
                break;

            default:
                min = HeroesKind.LORDKILBURN;
                max = conf.ExtCastleAllowRecruitSpecialHeroes()
                        ? (conf.PriceLoyaltyVersion()
                        ? HeroesKind.JARKONAS
                        : HeroesKind.BAX)
                        : HeroesKind.CELIA;
                break;
        }

        IntArrayList freeman_heroes = new IntArrayList(GameConsts.HEROESMAXCOUNT);

        // find freeman in race (skip: manual changes)
        for (int ii = min; ii <= max; ++ii)
            if (_items.get(ii).isFreeman() && !_items.get(ii).bitModes.Modes(HeroFlags.NOTDEFAULTS))
                freeman_heroes.add(ii);

        // not found, find any race
        if (RaceKind.NONE != race && freeman_heroes.size() == 0) {
            min = HeroesKind.LORDKILBURN;
            max = conf.ExtCastleAllowRecruitSpecialHeroes()
                    ? (conf.PriceLoyaltyVersion() ? HeroesKind.JARKONAS : HeroesKind.BAX)
                    : HeroesKind.CELIA;

            for (int ii = min; ii <= max; ++ii)
                if (_items.get(ii).isFreeman()) freeman_heroes.add(ii);
        }

        // not found, all heroes busy
        if (freeman_heroes.size() == 0) {
            return null;
        }

        return _items.get(Rand.Get(freeman_heroes));
    }

    static boolean InCastleNotGuardian(Castle castle, Heroes hero) {
        return castle.GetCenter().isEqual(hero.GetCenter()) && !hero.bitModes.Modes(HeroFlags.GUARDIAN);
    }

    static boolean InCastleAndGuardian(Castle castle, Heroes hero) {
        var cpt = castle.GetCenter();
        var hpt = hero.GetCenter();
        return cpt.x == hpt.x && cpt.y == hpt.y + 1 && hero.bitModes.Modes(HeroFlags.GUARDIAN);
    }

    public Heroes GetGuest(Castle castle) {
        return find_if(_items, hero -> InCastleNotGuardian(castle, hero));
    }

    public Heroes GetGuard(Castle castle) {
        return Settings.Get().ExtCastleAllowGuardians()
                ? find_if(_items, (Heroes hero) -> InCastleAndGuardian(castle, hero))
                : null;
    }
}
