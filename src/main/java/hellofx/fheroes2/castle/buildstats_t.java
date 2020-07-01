package hellofx.fheroes2.castle;

import hellofx.fheroes2.resource.Funds;

public class buildstats_t {
    public int id2;
    public byte race;
    public Funds cost;

    public buildstats_t(int build, int race, Funds cost) {
        id2 = build;
        this.race = (byte) race;
        this.cost = cost;
    }
}
