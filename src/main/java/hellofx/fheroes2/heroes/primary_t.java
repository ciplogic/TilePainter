package hellofx.fheroes2.heroes;

public class primary_t {
    public byte attack;
    public byte defense;
    public byte power;
    public byte knowledge;

    public primary_t(int attack, int defense, int power, int knowledge) {
        this.attack = (byte) attack;
        this.defense = (byte) defense;
        this.power = (byte) power;
        this.knowledge = (byte) knowledge;
    }
};