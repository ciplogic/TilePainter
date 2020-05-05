package hellofx.framework.controls;

import hellofx.framework.EventNames;
import hellofx.framework.MainContext;
import javafx.animation.AnimationTimer;

public class PerFrameTimer extends AnimationTimer {
    public MainContext ctx;

    @Override
    public void handle(long now) {
        ctx.notify(EventNames.onFrame, now);
    }
}
