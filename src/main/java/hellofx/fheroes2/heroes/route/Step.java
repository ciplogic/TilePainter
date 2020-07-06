package hellofx.fheroes2.heroes.route;

public class Step {
    public int from;
    public int direction;
    public int penalty;

    public Step(int index, int dir, int cost) {
        from = index;
        direction = dir;
        penalty = cost;
    }
}
