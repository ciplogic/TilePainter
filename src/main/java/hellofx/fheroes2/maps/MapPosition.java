package hellofx.fheroes2.maps;

import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.maps.objects.Maps;

public class MapPosition {
    public H2Point center = new H2Point();

    public MapPosition() {

    }

    public MapPosition(H2Point h2Point) {
        center = h2Point;
    }

    public H2Point GetCenter() {
        return center;
    }

    public void SetIndex(int index) {
        center = Maps.isValidAbsIndex(index) ? Maps.GetPoint(index) : new H2Point(-1, -1);
    }
}
