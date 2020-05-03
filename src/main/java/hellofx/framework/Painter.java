package hellofx.framework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

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

    /**
     * Sets the transform for the GraphicsContext to rotate around a pivot point.
     *
     * @param angle the angle of rotation.
     * @param px    the x pivot co-ordinate for the rotation (in canvas co-ordinates).
     * @param py    the y pivot co-ordinate for the rotation (in canvas co-ordinates).
     */
    public void rotate(double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    /**
     * Draws an image on a graphics context.
     * <p>
     * The image is drawn at (tlpx, tlpy) rotated by angle pivoted around the point:
     * (tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2)
     *
     * @param angle the angle of rotation.
     * @param tlpx  the top left x co-ordinate where the image will be plotted (in canvas co-ordinates).
     * @param tlpy  the top left y co-ordinate where the image will be plotted (in canvas co-ordinates).
     */
    public void drawRotatedImage(Image image, double angle, double tlpx, double tlpy) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy);
        gc.restore(); // back to original state (before rotation)
    }
}
