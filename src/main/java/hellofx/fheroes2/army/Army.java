package hellofx.fheroes2.army;

import hellofx.fheroes2.castle.Captain;
import hellofx.fheroes2.game.GameConsts;
import hellofx.fheroes2.heroes.HeroBase;
import hellofx.fheroes2.kingdom.H2Color;

import java.util.stream.IntStream;

public class Army {
    public Troops m_troops = new Troops();
    public HeroBase commander;
    public boolean combat_format;
    public int color;

    public Army(HeroBase s) {
        commander = s;
        combat_format = true;
        color = H2Color.NONE;
        var troops = new Troop[GameConsts.ARMYMAXTROOPS];
        IntStream.range(0, GameConsts.ARMYMAXTROOPS)
                .forEach(i -> {
                    troops[i] = new ArmyTroop(this);
                });
        m_troops.Assign(troops);
    }

    public Army() {
    }

    public void SetColor(int col) {
        color = col;
    }

    public void Reset(boolean b) {
    }

    public void SetCommander(Captain captain) {
    }
}
