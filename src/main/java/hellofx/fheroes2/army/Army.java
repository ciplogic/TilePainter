package hellofx.fheroes2.army;

import hellofx.fheroes2.heroes.HeroBase;

public class Army {
    public Troop[] m_troops;
    public HeroBase commander;
    public boolean combat_format;
    public int color;

    public void SetColor(int col) {
        color = col;
    }
}
