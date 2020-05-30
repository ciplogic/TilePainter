package hellofx.fheroes2.spell;

import java.util.ArrayList;
import java.util.List;

public class SpellStorage {
    public List<Spell> _items = new ArrayList<>();

    public int Size(int lvl) {
        switch (lvl) {
            case 1:
                return getSpellCountOfLevel(1);
            case 2:
                return getSpellCountOfLevel(2);
            case 3:
                return getSpellCountOfLevel(3);
            case 4:
                return getSpellCountOfLevel(4);
            case 5:
                return getSpellCountOfLevel(5);

            default:
                break;
        }

        return _items.size();
    }

    public int getSpellCountOfLevel(int level) {
        return (int) _items.stream()
                .filter(s -> s.isLevel(level))
                .count();
    }

    public SpellStorage GetSpells(int lvl) {
        var result = new SpellStorage();
        _items.stream()
                .filter(s -> s.isLevel(lvl))
                .forEach(s -> {
                    result._items.add(s);
                });
        return result;
    }

    public boolean isSpellPresent(Spell spell) {
        return _items.stream()
                .anyMatch(s -> s.id == spell.id);
    }

    public void Append(Spell spell) {
        if (isSpellPresent(spell))
            return;
        _items.add(spell);
    }

    public void Append(SpellStorage spellStorage) {
        spellStorage._items
                .forEach(this::Append);
    }
}
