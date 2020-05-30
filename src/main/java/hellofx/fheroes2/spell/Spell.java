package hellofx.fheroes2.spell;

import static hellofx.fheroes2.spell.SpellType.*;

public class Spell {
    public int id;

    public Spell(int s) {
        id = s > STONE ? NONE : s;
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

}
