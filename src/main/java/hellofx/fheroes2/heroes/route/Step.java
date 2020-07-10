package hellofx.fheroes2.heroes.route;

import hellofx.fheroes2.maps.objects.Maps;

public class Step {
    public int from;
    public int direction;
    public int penalty;

    public Step(int index, int dir, int cost) {
        from = index;
        direction = dir;
        penalty = cost;
    }

    public int GetIndex() {
        return from < 0 ? -1 : Maps.GetDirectionIndex(from, direction);
    }

    public int GetPenalty() {
        return penalty;
    }

    public int GetDirection() {
        return direction;
    }
}
