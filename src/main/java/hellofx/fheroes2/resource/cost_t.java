package hellofx.fheroes2.resource;

public class cost_t {
    public int gold;
    public short wood;
    public short mercury;
    public short ore;
    public short sulfur;
    public short crystal;
    public short gems;

    public cost_t(int gold, int wood, int mercury, int ore, int sulfur, int crystal, int gems) {
        this.gold = gold;
        this.wood = (short) wood;
        this.mercury = (short) mercury;
        this.ore = (short) ore;
        this.sulfur = (short) sulfur;
        this.crystal = (short) crystal;
        this.gems = (short) gems;
    }

    public cost_t() {
    }
}
