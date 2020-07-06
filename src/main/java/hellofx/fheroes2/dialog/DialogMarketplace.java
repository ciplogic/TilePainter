package hellofx.fheroes2.dialog;

import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.resource.ResourceKind;
import hellofx.fheroes2.system.Settings;

import static hellofx.fheroes2.castle.Marketplace.*;

public class DialogMarketplace {
    public static int GetTradeCosts(int rs_from, int rs_to, boolean tradingPost) {
        var world = World.Instance;
        var markets = tradingPost ? 3 : world.GetKingdom(Settings.Get().CurrentColor()).GetCountMarketplace();

        if (rs_from == rs_to) return 0;

        switch (rs_from) {
            // uncostly
            case ResourceKind.WOOD:
            case ResourceKind.ORE:

                switch (rs_to) {
                    // sale uncostly
                    case ResourceKind.GOLD:
                        if (1 == markets) return SALE_UNCOSTLY1;
                        if (2 == markets) return SALE_UNCOSTLY2;
                        if (3 == markets) return SALE_UNCOSTLY3;
                        if (4 == markets) return SALE_UNCOSTLY4;
                        if (5 == markets) return SALE_UNCOSTLY5;
                        if (6 == markets) return SALE_UNCOSTLY6;
                        if (7 == markets) return SALE_UNCOSTLY7;
                        if (8 == markets) return SALE_UNCOSTLY8;
                        if (8 < markets) return SALE_UNCOSTLY9;
                        break;

                    // change uncostly to costly
                    case ResourceKind.MERCURY:
                    case ResourceKind.SULFUR:
                    case ResourceKind.CRYSTAL:
                    case ResourceKind.GEMS:
                        if (1 == markets) return UNCOSTLY_COSTLY1;
                        if (2 == markets) return UNCOSTLY_COSTLY2;
                        if (3 == markets) return UNCOSTLY_COSTLY3;
                        if (4 == markets) return UNCOSTLY_COSTLY4;
                        if (5 == markets) return UNCOSTLY_COSTLY5;
                        if (6 == markets) return UNCOSTLY_COSTLY6;
                        if (7 == markets) return UNCOSTLY_COSTLY7;
                        if (8 == markets) return UNCOSTLY_COSTLY8;
                        if (8 < markets) return UNCOSTLY_COSTLY9;
                        break;

                    // change uncostly to uncostly
                    case ResourceKind.WOOD:
                    case ResourceKind.ORE:
                        if (1 == markets) return COSTLY_COSTLY1;
                        if (2 == markets) return COSTLY_COSTLY2;
                        if (3 == markets) return COSTLY_COSTLY3;
                        if (4 == markets) return COSTLY_COSTLY4;
                        if (5 == markets) return COSTLY_COSTLY5;
                        if (6 == markets) return COSTLY_COSTLY6;
                        if (7 == markets) return COSTLY_COSTLY7;
                        if (8 == markets) return COSTLY_COSTLY8;
                        if (8 < markets) return COSTLY_COSTLY9;
                        break;
                }
                break;

            // costly
            case ResourceKind.MERCURY:
            case ResourceKind.SULFUR:
            case ResourceKind.CRYSTAL:
            case ResourceKind.GEMS:

                switch (rs_to) {
                    // sale costly
                    case ResourceKind.GOLD:
                        if (1 == markets) return SALE_COSTLY1;
                        if (2 == markets) return SALE_COSTLY2;
                        if (3 == markets) return SALE_COSTLY3;
                        if (4 == markets) return SALE_COSTLY4;
                        if (5 == markets) return SALE_COSTLY5;
                        if (6 == markets) return SALE_COSTLY6;
                        if (7 == markets) return SALE_COSTLY7;
                        if (8 == markets) return SALE_COSTLY8;
                        if (8 < markets) return SALE_COSTLY9;
                        break;

                    // change costly to costly
                    case ResourceKind.MERCURY:
                    case ResourceKind.SULFUR:
                    case ResourceKind.CRYSTAL:
                    case ResourceKind.GEMS:
                        if (1 == markets) return COSTLY_COSTLY1;
                        if (2 == markets) return COSTLY_COSTLY2;
                        if (3 == markets) return COSTLY_COSTLY3;
                        if (4 == markets) return COSTLY_COSTLY4;
                        if (5 == markets) return COSTLY_COSTLY5;
                        if (6 == markets) return COSTLY_COSTLY6;
                        if (7 == markets) return COSTLY_COSTLY7;
                        if (8 == markets) return COSTLY_COSTLY8;
                        if (8 < markets) return COSTLY_COSTLY9;
                        break;

                    // change costly to uncostly
                    case ResourceKind.WOOD:
                    case ResourceKind.ORE:
                        if (1 == markets) return COSTLY_UNCOSTLY1;
                        if (2 == markets) return COSTLY_UNCOSTLY2;
                        if (3 == markets) return COSTLY_UNCOSTLY3;
                        if (4 == markets) return COSTLY_UNCOSTLY4;
                        if (5 == markets) return COSTLY_UNCOSTLY5;
                        if (6 == markets) return COSTLY_UNCOSTLY6;
                        if (7 == markets) return COSTLY_UNCOSTLY7;
                        if (8 == markets) return COSTLY_UNCOSTLY8;
                        if (8 < markets) return COSTLY_UNCOSTLY9;
                        break;
                }
                break;

            // gold
            case ResourceKind.GOLD:

                switch (rs_to) {
                    default:
                        break;

                    // buy costly
                    case ResourceKind.MERCURY:
                    case ResourceKind.SULFUR:
                    case ResourceKind.CRYSTAL:
                    case ResourceKind.GEMS:
                        if (1 == markets) return BUY_COSTLY1;
                        if (2 == markets) return BUY_COSTLY2;
                        if (3 == markets) return BUY_COSTLY3;
                        if (4 == markets) return BUY_COSTLY4;
                        if (5 == markets) return BUY_COSTLY5;
                        if (6 == markets) return BUY_COSTLY6;
                        if (7 == markets) return BUY_COSTLY7;
                        if (8 == markets) return BUY_COSTLY8;
                        if (8 < markets) return BUY_COSTLY9;
                        break;

                    // buy uncostly
                    case ResourceKind.WOOD:
                    case ResourceKind.ORE:
                        if (1 == markets) return BUY_UNCOSTLY1;
                        if (2 == markets) return BUY_UNCOSTLY2;
                        if (3 == markets) return BUY_UNCOSTLY3;
                        if (4 == markets) return BUY_UNCOSTLY4;
                        if (5 == markets) return BUY_UNCOSTLY5;
                        if (6 == markets) return BUY_UNCOSTLY6;
                        if (7 == markets) return BUY_UNCOSTLY7;
                        if (8 == markets) return BUY_UNCOSTLY8;
                        if (8 < markets) return BUY_UNCOSTLY9;
                        break;
                }
                break;

            // not select
            default:
                break;
        }

        return 0;
    }
}
