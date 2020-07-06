package hellofx.fheroes2.maps.objects;

import hellofx.fheroes2.game.Game;
import hellofx.fheroes2.resource.Artifact;
import hellofx.fheroes2.resource.Funds;
import hellofx.fheroes2.serialize.ByteVectorReader;

import java.util.ArrayList;
import java.util.List;

import static hellofx.common.Utilities.StringLower;

public class MapSphinx extends MapObjectSimple {
    public void LoadFromMP2(int index, ByteVectorReader st) {
        // id
        if (0 != st.get())
            return;
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

        // count answers
        int count = st.get();

        // answers
        for (var i = 0; i < 8; ++i) {
            var answer = Game.GetEncodeString(st.toString(13));

            if (count-- > 0 && answer.length() > 0)
                answers.add(StringLower(answer));
        }

        // message
        message = Game.GetEncodeString(st.toString(0));

        valid = true;
    }

    public Funds resources = new Funds();
    public Artifact artifact = new Artifact();
    public List<String> answers = new ArrayList<>();
    public String message;
    public boolean valid;
}
