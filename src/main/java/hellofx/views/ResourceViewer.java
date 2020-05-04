package hellofx.views;

import hellofx.framework.Painter;
import hellofx.player.Resources;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ResourceViewer {
    public static void paint(Painter painter, Resources resources) {
        var gc = painter.getGc();
        gc.setFill(Color.rgb(130, 130, 255, 0.5));
        gc.setStroke(Color.rgb(250, 230, 255, 1));
        gc.setLineWidth(2);
        gc.fillRoundRect(0,0, 300, 30, 10, 10);
        gc.strokeRoundRect(0,0, 300, 30, 10, 10);
        gc.setStroke(Color.rgb(250, 255, 255, 1));
        painter.draw("Gold: " + resources.gold, 40, 30, 1);
        painter.draw("Ore:" + resources.ore, 100, 30, 1);
        painter.draw("Wood:" + resources.wood, 160, 30, 1);
        painter.draw("Cry:" + resources.crystal, 220, 30, 1);
    }
}
