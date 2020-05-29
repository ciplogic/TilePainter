package hellofx.fheroes2.heroes;

public class secondary_t {
    public byte archery;
    public byte ballistics;
    public byte diplomacy;
    public byte eagleeye;
    public byte estates;
    public byte leadership;
    public byte logistics;
    public byte luck;
    public byte mysticism;
    public byte navigation;
    public byte necromancy;
    public byte pathfinding;
    public byte scouting;
    public byte wisdom;

    public secondary_t(
            int archery,
            int ballistics,
            int diplomacy,
            int eagleeye,
            int estates,
            int leadership,
            int logistics,
            int luck,
            int mysticism,
            int navigation,
            int necromancy,
            int pathfinding,
            int scouting,
            int wisdom
    ) {
        this.archery = (byte) archery;
        this.ballistics = (byte) ballistics;
        this.diplomacy = (byte) diplomacy;
        this.eagleeye = (byte) eagleeye;
        this.estates = (byte) estates;
        this.leadership = (byte) leadership;
        this.logistics = (byte) logistics;
        this.luck = (byte) luck;
        this.mysticism = (byte) mysticism;
        this.navigation = (byte) navigation;
        this.necromancy = (byte) necromancy;
        this.pathfinding = (byte) pathfinding;
        this.scouting = (byte) scouting;
        this.wisdom = (byte) wisdom;
    }
}
