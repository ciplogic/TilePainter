package hellofx.fheroes2.maps.objects;

import hellofx.fheroes2.maps.resource.Artifact;
import hellofx.fheroes2.maps.resource.Funds;
import hellofx.fheroes2.serialize.ByteVectorReader;

public class MapEvent extends MapObjectSimple {
    public Funds resources;
    public Artifact artifact;
    public boolean computer;
    public boolean cancel;
    public int colors;
    public String message;

    public void LoadFromMP2(int findobject, ByteVectorReader bvr) {
        //TODO
    }
}
