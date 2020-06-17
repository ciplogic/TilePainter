package hellofx.fheroes2.game;

public class Game {
    private static int animationFrame;

    public static int MapsAnimationFrame() {
        return animationFrame / 10;
    }

    public static void nextFrame() {
        animationFrame++;
    }
}
