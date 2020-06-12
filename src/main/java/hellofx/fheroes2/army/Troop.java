package hellofx.fheroes2.army;

import hellofx.fheroes2.monster.Monster;
import hellofx.fheroes2.monster.MonsterKind;

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

    public void Set(int monsterType, int count) {
        _monster = new Monster(monsterType);
        this.count = count;
    }

    public void Reset() {
        _monster.id = MonsterKind.UNKNOWN;
        count = 0;
    }
}
