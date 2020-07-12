package hellofx.fheroes2.system;

import static hellofx.player.PlayerControl.CONTROL_AI;

public abstract class H2Control {
    public boolean isControlAI() {
        return (CONTROL_AI & GetControl()) != 0;
    }

    public abstract int GetControl();
}
