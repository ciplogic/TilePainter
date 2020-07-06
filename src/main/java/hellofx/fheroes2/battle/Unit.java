package hellofx.fheroes2.battle;

import hellofx.fheroes2.agg.Bitmap;

public class Unit {
    public int uid = 0;
    public int hp = 0;
    public int count0 = 0;
    public int dead = 0;
    public int shots = 0;
    public int disruptingray = 0;
    public boolean reflect = false;

    public int animstate;
    public int animframe = 0;
    public int animstep = 0;

    public Position position;
    public ModesAffected affected;
    public Unit mirror;
    public Bitmap[] contours = new Bitmap[4];

    public boolean blindanswer = false;
}
