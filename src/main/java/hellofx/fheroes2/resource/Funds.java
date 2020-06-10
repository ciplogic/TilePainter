package hellofx.fheroes2.resource;

public class Funds {
    public static Funds NONE = new Funds(0, 0, 0, 0, 0, 0, 0);
    public int gold;
    public short wood;
    public short mercury;
    public short ore;
    public short sulfur;
    public short crystal;
    public short gems;

    public Funds(int gold, int wood, int mercury, int ore, int sulfur, int crystal, int gems) {
        this.gold = gold;
        this.wood = (short) wood;
        this.mercury = (short) mercury;
        this.ore = (short) ore;
        this.sulfur = (short) sulfur;
        this.crystal = (short) crystal;
        this.gems = (short) gems;
    }


    public Funds() {
    }

    public Funds copy() {
        return new Funds(gold, wood, mercury, ore, sulfur, crystal, gems);
    }
}
