package hellofx.fheroes2.army;

import hellofx.fheroes2.castle.Captain;
import hellofx.fheroes2.castle.building_t;
import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.game.GameConsts;
import hellofx.fheroes2.heroes.HeroBase;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.monster.Monster;

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

    public void Reset(boolean soft) {
        m_troops.Clean();

        if (commander == null || !commander.isHeroes())
            return;
        Monster mons1 = new Monster(commander.GetRace(), building_t.DWELLING_MONSTER1);

        if (soft) {
            Monster mons2 = new Monster(commander.GetRace(), building_t.DWELLING_MONSTER2);

            switch (Rand.Get(1, 3)) {
                case 1:
                    m_troops.JoinTroop(mons1, 3 * mons1.GetGrown());
                    break;
                case 2:
                    m_troops.JoinTroop(mons2, mons2.GetGrown() + mons2.GetGrown() / 2);
                    break;
                default:
                    m_troops.JoinTroop(mons1, 2 * mons1.GetGrown());
                    m_troops.JoinTroop(mons2, mons2.GetGrown());
                    break;
            }
        } else {
            m_troops.JoinTroop(mons1, 1);
        }
    }

    public void SetCommander(Captain captain) {
        //TODO
    }
}
