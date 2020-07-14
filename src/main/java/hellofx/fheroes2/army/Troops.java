package hellofx.fheroes2.army;

import hellofx.fheroes2.castle.Castle;
import hellofx.fheroes2.kingdom.Kingdom;
import hellofx.fheroes2.monster.Monster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static hellofx.common.Utilities.minValueInCollection;

public class Troops {
    public List<Troop> _items = new ArrayList<>();

    public boolean IsValid() {
        for (Troop troop : _items) {
            if (troop.IsValid())
                return true;
        }
        return false;
    }

    public void assign(Troop[] troops) {
        _items.clear();
        _items.addAll(Arrays.asList(troops));
    }

    public void Assign(Troop[] troops) {
        _items.clear();
        _items.addAll(Arrays.asList(troops));
    }

    public void Clean() {
        for (var it : _items) {
            it.Reset();
        }
    }

    public void JoinTroop(Monster mons1, int count) {
        _items.add(new Troop(mons1, count));
    }

    public void UpgradeTroops(Castle castle) {
        for (var _item : _items)
            if (_item.IsValid()) {
                var payment = _item.GetUpgradeCost();
                Kingdom kingdom = castle.GetKingdom();

                if (castle.GetRace() == _item._monster.GetRace() &&
                        castle.isBuild(_item._monster.GetUpgrade().GetDwelling()) &&
                        kingdom.AllowPayment(payment)) {
                    kingdom.OddFundsResource(payment);
                    _item._monster.Upgrade();
                }
            }
    }

    public int GetHitPoints() {
        int res = 0;

        for (var _item : _items)
            if (_item.IsValid()) res += _item.GetHitPointsTroop();

        return res;
    }

    public boolean AllTroopsIsRace(int race) {
        for (var it : _items) {
            if (it.IsValid() && it._monster.GetRace() != race) {
                return false;
            }
        }
        return true;
    }

    public Troop GetSlowestTroop() {
        return minValueInCollection(_items, Troop::GetSpeed);
    }
}
