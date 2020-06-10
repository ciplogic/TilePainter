package hellofx.fheroes2.monster;

import hellofx.fheroes2.resource.Funds;

public class monstats_t {
    public byte attack;
    public byte defense;
    public byte damageMin;
    public byte damageMax;
    public short hp;
    public byte speed;
    public byte grown;
    public byte shots;
    public String name;
    public String multiname;
    public Funds cost = new Funds();

    public monstats_t() {
    }

    public monstats_t(int attack,
                      int defense,
                      int damageMin,
                      int damageMax,
                      int hp,
                      int speed,
                      int grown,
                      int shots,
                      String name,
                      String multiname,
                      Funds cost) {
        this.attack = (byte) attack;
        this.defense = (byte) defense;
        this.damageMin = (byte) damageMin;
        this.damageMax = (byte) damageMax;
        this.hp = (short) hp;
        this.speed = (byte) speed;
        this.grown = (byte) grown;
        this.shots = (byte) shots;
        this.name = name;
        this.multiname = multiname;
        this.cost = cost;
    }
}
