package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.resource.Funds;

public class profitstats_t {
    public String id;
    public Funds cost;

    public profitstats_t(String aId, Funds aCost) {
        id = aId;
        cost = aCost;
    }
}
