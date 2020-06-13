package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.castle.AllCastles;
import hellofx.fheroes2.heroes.AllHeroes;
import hellofx.fheroes2.system.Settings;

import java.util.stream.IntStream;

import static hellofx.fheroes2.game.GameConsts.KINGDOMMAX;

public class Kingdoms {
    Kingdom[] kingdoms;

    Kingdoms() {
        kingdoms = new Kingdom[KINGDOMMAX + 1];
        IntStream.range(0, KINGDOMMAX + 1)
                .forEach(i -> kingdoms[i] = new Kingdom());
    }

    public Kingdom GetKingdom(int color) {
        switch (color) {
            case H2Color.BLUE:
                return kingdoms[0];
            case H2Color.GREEN:
                return kingdoms[1];
            case H2Color.RED:
                return kingdoms[2];
            case H2Color.YELLOW:
                return kingdoms[3];
            case H2Color.ORANGE:
                return kingdoms[4];
            case H2Color.PURPLE:
                return kingdoms[5];
            default:
                break;
        }

        return kingdoms[6];
    }

    public void Init() {
        H2Colors colors = new H2Colors(Settings.Get().GetPlayers().GetColors());

        clear();

        for (int color : colors._items)
            GetKingdom(color).Init(color);
    }

    private void clear() {
        for (var ii = 0; ii < size(); ++ii)
            kingdoms[ii].clear();
    }

    public int size() {
        return KINGDOMMAX + 1;
    }

    public void AddHeroes(AllHeroes vec_heroes) {
        //TODO
    }

    public void AddCastles(AllCastles vec_castles) {
        //TODO
    }

    public void ApplyPlayWithStartingHero() {
        //TODO
    }

    public void AddCondLossHeroes(AllHeroes vec_heroes) {
        //TODO
    }
}
