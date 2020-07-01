package hellofx.fheroes2.spell;

import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.kingdom.RaceKind;

import java.util.ArrayList;
import java.util.List;

import static hellofx.fheroes2.spell.SpellKind.*;
import static hellofx.fheroes2.spell.SpellStats.SP_DISABLE;
import static hellofx.fheroes2.spell.SpellStats.spells;

public class Spell {
    public int id;

    public Spell(int s) {
        id = s > STONE ? NONE : s;
    }

    public static Spell RandCombat(int lvl) {
        return Rand(lvl, false);
    }

    public static Spell Rand(int lvl, boolean adv) {
        List<Spell> v = new ArrayList<>(15);

        for (var sp = NONE; sp < STONE; ++sp) {
            Spell spell = new Spell(sp);
            if (((adv && !spell.isCombat()) || (!adv && spell.isCombat())) &&
                    lvl == spell.Level() &&
                    (spells[sp].bits & SP_DISABLE) == 0)
                v.add(spell);
        }
        return v.size() != 0 ? Rand.Get(v) : new Spell(NONE);
    }

    public static Spell RandAdventure(int lvl) {
        return Rand(lvl, true);
    }

    public boolean isValid() {
        //TODO
        return false;
    }

    public boolean isLevel(int level) {
        return Level() == level;
    }

    private int Level() {
        switch (id) {
            case BLESS:
            case BLOODLUST:
            case CURE:
            case CURSE:
            case DISPEL:
            case HASTE:
            case ARROW:
            case SHIELD:
            case SLOW:
            case STONESKIN:

            case VIEWMINES:
            case VIEWRESOURCES:
                return 1;

            case BLIND:
            case COLDRAY:
            case DEATHRIPPLE:
            case DISRUPTINGRAY:
            case DRAGONSLAYER:
            case LIGHTNINGBOLT:
            case STEELSKIN:

            case HAUNT:
            case SUMMONBOAT:
            case VIEWARTIFACTS:
            case VISIONS:
                return 2;

            case ANIMATEDEAD:
            case ANTIMAGIC:
            case COLDRING:
            case DEATHWAVE:
            case EARTHQUAKE:
            case FIREBALL:
            case HOLYWORD:
            case MASSBLESS:
            case MASSCURSE:
            case MASSDISPEL:
            case MASSHASTE:
            case PARALYZE:
            case TELEPORT:

            case IDENTIFYHERO:
            case VIEWHEROES:
            case VIEWTOWNS:
                return 3;

            case BERSERKER:
            case ELEMENTALSTORM:
            case FIREBLAST:
            case HOLYSHOUT:
            case MASSCURE:
            case MASSSHIELD:
            case MASSSLOW:
            case METEORSHOWER:
            case RESURRECT:
            case HYPNOTIZE:

            case SETEGUARDIAN:
            case SETAGUARDIAN:
            case SETFGUARDIAN:
            case SETWGUARDIAN:
            case TOWNGATE:
            case VIEWALL:
                return 4;

            case CHAINLIGHTNING:
            case ARMAGEDDON:
            case MIRRORIMAGE:
            case RESURRECTTRUE:
            case SUMMONEELEMENT:
            case SUMMONAELEMENT:
            case SUMMONFELEMENT:
            case SUMMONWELEMENT:

            case DIMENSIONDOOR:
            case TOWNPORTAL:
                return 5;

            default:
                break;
        }

        return 0;
    }

    public boolean isCombat() {
        switch (id) {
            case NONE:
            case VIEWMINES:
            case VIEWRESOURCES:
            case VIEWARTIFACTS:
            case VIEWTOWNS:
            case VIEWHEROES:
            case VIEWALL:
            case IDENTIFYHERO:
            case SUMMONBOAT:
            case DIMENSIONDOOR:
            case TOWNGATE:
            case TOWNPORTAL:
            case VISIONS:
            case HAUNT:
            case SETEGUARDIAN:
            case SETAGUARDIAN:
            case SETFGUARDIAN:
            case SETWGUARDIAN:
                return false;
            default:
                break;
        }
        return true;
    }

    public boolean isRaceCompatible(int race) {
        switch (id) {
            case MASSCURE:
            case MASSBLESS:
            case HOLYSHOUT:
            case HOLYWORD:
            case BLESS:
            case CURE:
                if (RaceKind.NECR == race) return false;
                break;

            case DEATHWAVE:
            case DEATHRIPPLE:
            case ANIMATEDEAD:
                if (RaceKind.NECR != race) return false;
                break;
        }
        return true;
    }

    public int GetID() {
        return id;
    }
}
