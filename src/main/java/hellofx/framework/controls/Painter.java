package hellofx.framework.controls;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Painter {
    private GraphicsContext gc;
    private int w;
    private int h;

    void setSize(int w, int h){
        this.w = w;
        this.h = h;
    }
    public void updateGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public void drawImage(Image image, int x, int y) {
        gc.drawImage(image, x, y);
    }

    public void clear(Color royalblue) {
        gc.setFill(royalblue);
        gc.fillRect(0, 0, w, h);
    }

    public GraphicsContext getGc(){
        return gc;
    }

    void textAlign(TextAlignment alignment){
        gc.setTextAlign(alignment);
    }

    public void draw(String text, int x, int y, int thickness) {
        gc.setLineWidth(thickness);
        gc.strokeText(text, x, y);
    }
}

