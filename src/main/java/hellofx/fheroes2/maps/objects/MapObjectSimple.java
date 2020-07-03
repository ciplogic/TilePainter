package hellofx.fheroes2.maps.objects;

import hellofx.fheroes2.maps.MapPosition;

public class MapObjectSimple {
    public MapPosition mapPosition = new MapPosition();
    public int uid;
    public int type;

    public int GetUID() {
        return uid;
    }

    public boolean JoinConditionSkip() {
        //TODO
        return false;
    }

    public boolean JoinConditionFree() {
        //TODO
        return false;
    }

    public boolean JoinConditionForce() {
        //TODO
        return false;
    }
}
