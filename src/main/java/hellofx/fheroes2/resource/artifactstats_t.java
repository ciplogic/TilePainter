package hellofx.fheroes2.resource;

public class artifactstats_t {
    byte bits;
    byte extra;
    byte type;
    String name;
    String description;

    public artifactstats_t(int bits,
                           int extra,
                           int type,
                           String name,
                           String description) {
        this.bits = (byte) bits;
        this.extra = (byte) extra;
        this.type = (byte) type;
        this.name = name;
        this.description = description;
    }
}
