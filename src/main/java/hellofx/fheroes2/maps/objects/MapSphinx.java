package hellofx.fheroes2.maps.objects;

import hellofx.fheroes2.resource.Artifact;
import hellofx.fheroes2.resource.Funds;
import hellofx.fheroes2.serialize.ByteVectorReader;

import java.util.ArrayList;
import java.util.List;

import static hellofx.common.Utilities.writeTodo;

public class MapSphinx extends MapObjectSimple {
    public void LoadFromMP2(int findobject, ByteVectorReader bvr) {
        //TODO
        writeTodo("MapSphinx");
    }

    public Funds resources = new Funds();
    public Artifact artifact = new Artifact();
    public List<String> answers = new ArrayList<>();
    public String message;
    public boolean valid;
}
