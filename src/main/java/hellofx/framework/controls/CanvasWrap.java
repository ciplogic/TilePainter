package hellofx.framework.controls;

import hellofx.framework.EventNames;
import hellofx.framework.GamePreferences;
import hellofx.framework.MainContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class CanvasWrap {
    public MainContext ctxt;
    public Canvas canvas;
    public GamePreferences prefs;
    Painter painter = new Painter();

    public CanvasWrap() {
    }

    public void setup() {
        canvas = new Canvas(prefs.w, prefs.h);
        painter.setSize(prefs.w, prefs.h);

        canvas.setOnMouseClicked(mouseEvent -> {
            ctxt.notify(EventNames.onMouse, mouseEvent);
        });

        canvas.setOnMousePressed(mouseEvent -> {
            ctxt.notify(EventNames.onMousePressed, mouseEvent);
        });
        canvas.setOnMouseReleased(mouseEvent -> {
            ctxt.notify(EventNames.onMouseReleased, mouseEvent);
        });
        canvas.setOnMouseMoved(mouseEvent -> {
            ctxt.notify(EventNames.onMouseMoved, mouseEvent);
        });
        canvas.setOnMouseDragged(mouseEvent -> {
            ctxt.notify(EventNames.onMouseMoved, mouseEvent);
        });
    }

    public void drawShapes(GraphicsContext gc) {
        painter.updateGc(gc);
        ctxt.notify(EventNames.onPaint, painter);
    }

    public Painter getPainter() {
        painter.updateGc(canvas.getGraphicsContext2D());
        return painter;
    }
}
