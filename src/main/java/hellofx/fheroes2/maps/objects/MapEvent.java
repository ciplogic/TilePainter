package hellofx.fheroes2.maps.objects;

import hellofx.fheroes2.game.Game;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.resource.Artifact;
import hellofx.fheroes2.resource.Funds;
import hellofx.fheroes2.serialize.ByteVectorReader;

public class MapEvent extends MapObjectSimple {
    public Funds resources = new Funds();
    public Artifact artifact = new Artifact();
    public boolean computer;
    public boolean cancel;
    public int colors;
    public String message = "";

    public void LoadFromMP2(int index, ByteVectorReader st) {
        // id
        if (1 == st.get()) {
            mapPosition.SetIndex(index);

            // resource
            resources.wood = (short) st.getLE32();
            resources.mercury = (short) st.getLE32();
            resources.ore = (short) st.getLE32();
            resources.sulfur = (short) st.getLE32();
            resources.crystal = (short) st.getLE32();
            resources.gems = (short) st.getLE32();
            resources.gold = st.getLE32();

            // artifact
            artifact = new Artifact(st.getLE16());

            // allow computer
            computer = st.get() != 0;

            // cancel event after first visit
            cancel = st.get() != 0;

            st.skip(10);

            colors = 0;
            // blue
            if (st.get() != 0) colors |= H2Color.BLUE;
            // green
            if (st.get() != 0) colors |= H2Color.GREEN;
            // red
            if (st.get() != 0) colors |= H2Color.RED;
            // yellow
            if (st.get() != 0) colors |= H2Color.YELLOW;
            // orange
            if (st.get() != 0) colors |= H2Color.ORANGE;
            // purple
            if (st.get() != 0) colors |= H2Color.PURPLE;

            // message
            message = Game.GetEncodeString(st.toString(0));
        }
    }
}
