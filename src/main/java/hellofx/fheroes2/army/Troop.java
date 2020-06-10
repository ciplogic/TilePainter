package hellofx.fheroes2.army;

import hellofx.fheroes2.monster.Monster;

public class Troop {
    public Monster _monster = new Monster(0);
    public int count;

    public Troop() {
    }

    public void SetMonster(Monster monster) {
        _monster.id = monster.id;
    }

    public boolean IsValid() {
        return (_monster.IsValid() && count > 0);
    }

    public void SetCount(int c) {
        count = c;
    }
}
