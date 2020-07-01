package hellofx.fheroes2.army;

import hellofx.fheroes2.common.H2IntPair;

public class JoinCount extends H2IntPair {
    public JoinCount() {
        super(JoinKind.JOIN_NONE, 0);
    }

    public JoinCount(int reason, int count) {
        super(reason, count);
    }
}
