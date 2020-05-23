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
        for (var hid = HeroesKinds.LORDKILBURN; hid <= HeroesKinds.DIMITRY; ++hid)
            _items.add(new Heroes(hid, RaceKind.KNGT));

        // barbarian: THUNDAX, FINEOUS, JOJOSH, CRAGHACK, JEZEBEL, JACLYN, ERGON, TSABU, ATLAS
        for (var hid = HeroesKinds.THUNDAX; hid <= HeroesKinds.ATLAS; ++hid)
            _items.add(new Heroes(hid, RaceKind.BARB));

        // sorceress: ASTRA, NATASHA, TROYAN, VATAWNA, REBECCA, GEM, ARIEL, CARLAWN, LUNA
        for (var hid = HeroesKinds.ASTRA; hid <= HeroesKinds.LUNA; ++hid)
            _items.add(new Heroes(hid, RaceKind.SORC));

        // warlock: ARIE, ALAMAR, VESPER, CRODO, BAROK, KASTORE, AGAR, FALAGAR, WRATHMONT
        for (var hid = HeroesKinds.ARIE; hid <= HeroesKinds.WRATHMONT; ++hid)
            _items.add(new Heroes(hid, RaceKind.WRLK));

        // wizard: MYRA, FLINT, DAWN, HALON, MYRINI, WILFREY, SARAKIN, KALINDRA, MANDIGAL
        for (var hid = HeroesKinds.MYRA; hid <= HeroesKinds.MANDIGAL; ++hid)
            _items.add(new Heroes(hid, RaceKind.WZRD));

        // necromancer: ZOM, DARLANA, ZAM, RANLOO, CHARITY, RIALDO, ROXANA, SANDRO, CELIA
        for (var hid = HeroesKinds.ZOM; hid <= HeroesKinds.CELIA; ++hid)
            _items.add(new Heroes(hid, RaceKind.NECR));

        // from campain
        _items.add(new Heroes(HeroesKinds.ROLAND, RaceKind.WZRD));
        _items.add(new Heroes(HeroesKinds.CORLAGON, RaceKind.KNGT));
        _items.add(new Heroes(HeroesKinds.ELIZA, RaceKind.SORC));
        _items.add(new Heroes(HeroesKinds.ARCHIBALD, RaceKind.WRLK));
        _items.add(new Heroes(HeroesKinds.HALTON, RaceKind.KNGT));
        _items.add(new Heroes(HeroesKinds.BAX, RaceKind.NECR));

        // loyalty version
        _items.add(new Heroes(loyalty ? HeroesKinds.SOLMYR : HeroesKinds.UNKNOWN, RaceKind.WZRD));
        _items.add(new Heroes(loyalty ? HeroesKinds.DAINWIN : HeroesKinds.UNKNOWN, RaceKind.WRLK));
        _items.add(new Heroes(loyalty ? HeroesKinds.MOG : HeroesKinds.UNKNOWN, RaceKind.NECR));
        _items.add(new Heroes(loyalty ? HeroesKinds.UNCLEIVAN : HeroesKinds.UNKNOWN, RaceKind.BARB));
        _items.add(new Heroes(loyalty ? HeroesKinds.JOSEPH : HeroesKinds.UNKNOWN, RaceKind.KNGT));
        _items.add(new Heroes(loyalty ? HeroesKinds.GALLAVANT : HeroesKinds.UNKNOWN, RaceKind.KNGT));
        _items.add(new Heroes(loyalty ? HeroesKinds.ELDERIAN : HeroesKinds.UNKNOWN, RaceKind.WRLK));
        _items.add(new Heroes(loyalty ? HeroesKinds.CEALLACH : HeroesKinds.UNKNOWN, RaceKind.KNGT));
        _items.add(new Heroes(loyalty ? HeroesKinds.DRAKONIA : HeroesKinds.UNKNOWN, RaceKind.WZRD));
        _items.add(new Heroes(loyalty ? HeroesKinds.MARTINE : HeroesKinds.UNKNOWN, RaceKind.SORC));
        _items.add(new Heroes(loyalty ? HeroesKinds.JARKONAS : HeroesKinds.UNKNOWN, RaceKind.BARB));

        // devel
        _items.add(new Heroes(Settings.Get().IS_DEVEL() ? HeroesKinds.SANDYSANDY : HeroesKinds.UNKNOWN, RaceKind.WRLK));
        _items.add(new Heroes(HeroesKinds.UNKNOWN, RaceKind.KNGT));
    }
}
