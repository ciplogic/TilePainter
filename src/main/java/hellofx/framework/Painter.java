package hellofx.framework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
        gc.fillRect(0,0,w,h);
    }
}
