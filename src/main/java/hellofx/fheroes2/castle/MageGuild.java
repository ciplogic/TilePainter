package hellofx.fheroes2.castle;

import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.spell.Spell;
import hellofx.fheroes2.spell.SpellStorage;

public class MageGuild {
    SpellStorage general = new SpellStorage();
    SpellStorage library = new SpellStorage();

    public void Builds(int race, boolean libraryCap) {
        general._items.clear();
        library._items.clear();

        // level 5
        general.Append(7 > Rand.Get(1, 10) ? Spell.RandCombat(5) : Spell.RandAdventure(5));

        // level 4
        general.Append(GetCombatSpellCompatibility(race, 4));
        general.Append(Spell.RandAdventure(4));

        // level 3
        general.Append(GetCombatSpellCompatibility(race, 3));
        general.Append(Spell.RandAdventure(3));

        // level 2
        general.Append(GetCombatSpellCompatibility(race, 2));
        general.Append(GetUniqueCombatSpellCompatibility(general, race, 2));
        general.Append(Spell.RandAdventure(2));

        // level 1
        general.Append(GetCombatSpellCompatibility(race, 1));
        general.Append(GetUniqueCombatSpellCompatibility(general, race, 1));
        general.Append(Spell.RandAdventure(1));

        if (libraryCap) {
            library.Append(GetUniqueCombatSpellCompatibility(general, race, 1));
            library.Append(GetUniqueCombatSpellCompatibility(general, race, 2));
            library.Append(GetUniqueCombatSpellCompatibility(general, race, 3));
            library.Append(GetUniqueCombatSpellCompatibility(general, race, 4));
            library.Append(GetUniqueCombatSpellCompatibility(general, race, 5));
        }
    }

    private Spell GetUniqueCombatSpellCompatibility(SpellStorage spells, int race, int lvl) {
        Spell spell = GetCombatSpellCompatibility(race, lvl);
        while (spells.isPresentSpell(spell)) spell = GetCombatSpellCompatibility(race, lvl);
        return spell;
    }

    private Spell GetCombatSpellCompatibility(int race, int level) {
        Spell spell = Spell.RandCombat(level);
        while (!spell.isRaceCompatible(race)) spell = Spell.RandCombat(level);
        return spell;
    }
}
