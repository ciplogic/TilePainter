package hellofx.fheroes2.heroes;

import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.system.Settings;

public class AllHeroes extends VecHeroes {
    public Heroes GetFreeman(int race) {
        //TODO
        return null;
    }

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
}
