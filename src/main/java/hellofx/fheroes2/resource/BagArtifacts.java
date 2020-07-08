package hellofx.fheroes2.resource;

import java.util.ArrayList;
import java.util.List;

import static hellofx.common.Utilities.count;

public class BagArtifacts {
    public List<Artifact> _items = new ArrayList<>();

    public int Count(Artifact art) {
        return count(_items, a -> a.id == art.id);
    }

    public boolean isPresentArtifact(Artifact art) {
        //TODO
        return false;
    }

    public boolean PushArtifact(Artifact art) {
        //TODO
        return false;
    }

    public boolean MakeBattleGarb() {
        //TODO
        return false;
    }
}
