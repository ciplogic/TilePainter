package hellofx.fheroes2.army;

import hellofx.fheroes2.monster.Monster;

public class Troop {
    public Monster _monster = new Monster(0);
    public int count;

    public void SetMonster(Monster monster) {
        _monster.id = monster.id;
    }
}
