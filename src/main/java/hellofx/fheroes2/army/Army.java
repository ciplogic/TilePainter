package hellofx.fheroes2.army;

import hellofx.fheroes2.heroes.HeroBase;

public class Army {
    public Troops m_troops = new Troops();
    public HeroBase commander;
    public boolean combat_format;
    public int color;

    public void SetColor(int col) {
        color = col;
    }

    public void Reset(boolean b) {
    }
}
