package hellofx.fheroes2.castle;

import hellofx.fheroes2.monster.Monster;

public class DwellingItem {
    public int type;
    public Monster mons;

    public DwellingItem(Castle castle, int dw) {
        type = castle.GetActualDwelling(dw);
        mons = new Monster(castle.GetRace(), type);
    }
}
