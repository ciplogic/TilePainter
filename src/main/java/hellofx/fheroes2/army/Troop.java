package hellofx.fheroes2.army;

import hellofx.fheroes2.monster.Monster;
import hellofx.fheroes2.monster.MonsterKind;
import hellofx.fheroes2.resource.Funds;

public class Troop {
    public Monster _monster = new Monster(0);
    public int count;

    public Troop() {
    }

    public Troop(Monster monster, int count) {
        SetMonster(monster);
        this.count = count;
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

    public Funds GetUpgradeCost() {
        return _monster.GetUpgradeCost().multiply(count);
    }

    public int GetHitPointsTroop() {
        return _monster.GetHitPoints() * count;
    }

    public int GetDamageMin() {
        return _monster.GetDamageMin() * count;
    }

    public int GetDamageMax() {
        return _monster.GetDamageMax() * count;
    }

    public int GetCount() {
        return count;
    }
}
