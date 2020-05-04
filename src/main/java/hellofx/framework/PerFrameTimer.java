package hellofx.framework;

import javafx.animation.AnimationTimer;

public class PerFrameTimer extends AnimationTimer {
    public MainContext ctx;

    @Override
    public void handle(long now) {
        ctx.notify(EventNames.onFrame, now);
    }
}
