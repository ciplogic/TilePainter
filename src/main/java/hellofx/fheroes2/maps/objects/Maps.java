package hellofx.fheroes2.maps.objects;

import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.kingdom.World;

public class Maps {

    public static H2Point GetPoint(int index) {
        var world = World.Instance;
        return new H2Point(index % world.w, index / world.w);
    }
}
