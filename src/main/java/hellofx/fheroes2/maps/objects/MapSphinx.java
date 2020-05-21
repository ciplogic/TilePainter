package hellofx.fheroes2.maps.objects;

import hellofx.fheroes2.maps.resource.Artifact;
import hellofx.fheroes2.maps.resource.Funds;
import hellofx.fheroes2.serialize.ByteVectorReader;

import java.util.ArrayList;
import java.util.List;

public class MapSphinx extends MapObjectSimple {
    public void LoadFromMP2(int findobject, ByteVectorReader bvr) {
        //TODO
    }

    public Funds resources = new Funds();
    public Artifact artifact = new Artifact();
    public List<String> answers = new ArrayList<>();
    public String message;
    public boolean valid;
}
