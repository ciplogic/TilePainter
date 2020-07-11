package hellofx.fheroes2.resource;

import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.spell.Spell;
import hellofx.fheroes2.spell.SpellKind;
import hellofx.fheroes2.system.Settings;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import static hellofx.fheroes2.resource.ArtifactKind.*;
import static hellofx.fheroes2.resource.ArtifactLevel.*;

public class Artifact {
    public int id;
    public int ext;

    public static final int ART_DISABLED = 0x01;
    public static final int ART_RNDUSED = 0x02;

    public Artifact(int art) {

        id = Math.min(art, ArtifactKind.UNKNOWN);
    }

    public Artifact() {

    }

    public static int Rand(int lvl) {
        var v = new IntArrayList(25);
        var artifacts = ArtifactStats.artifacts;

        // if possibly: make unique on map
        for (var art = ArtifactKind.ULTIMATE_BOOK; art < ArtifactKind.UNKNOWN; ++art) {
            Artifact artifact = new Artifact(art);
            if ((lvl & artifact.Level()) != 0 &&
                    (artifacts[art].bits & ART_DISABLED) == 0 &&
                    (artifacts[art].bits & ART_RNDUSED) == 0
            )
                v.add(art);
        }
        //
        if (v.size() == 0) {
            for (var art = ULTIMATE_BOOK; art < UNKNOWN; ++art) {
                Artifact artifact = new Artifact(art);
                if ((lvl & artifact.Level()) != 0 &&
                        (artifacts[art].bits & ART_DISABLED) == 0) {
                    v.add(art);
                }
            }
        }

        int res = v.size() != 0 ? Rand.Get(v) : UNKNOWN;
        artifacts[res].bits |= ART_RNDUSED;

        return res;
    }

    public static Artifact FromMP2IndexSprite(int index) {
        if (0xA2 > index) return new Artifact((index - 1) / 2);
        if (Settings.Get().PriceLoyaltyVersion() && 0xAB < index && 0xCE > index)
            return new Artifact((index - 1) / 2);
        if (0xA3 == index) return new Artifact(Rand(ART_LEVEL123));
        if (0xA4 == index) return new Artifact(Rand(ART_ULTIMATE));
        if (0xA7 == index) return new Artifact(Rand(ART_LEVEL1));
        if (0xA9 == index) return new Artifact(Rand(ART_LEVEL2));
        if (0xAB == index) return new Artifact(Rand(ART_LEVEL3));

        return new Artifact(ArtifactKind.UNKNOWN);
    }

    private int Level() {
        switch (id) {
            case MEDAL_VALOR:
            case MEDAL_COURAGE:
            case MEDAL_HONOR:
            case MEDAL_DISTINCTION:
            case THUNDER_MACE:
            case ARMORED_GAUNTLETS:
            case DEFENDER_HELM:
            case GIANT_FLAIL:
            case RABBIT_FOOT:
            case GOLDEN_HORSESHOE:
            case GAMBLER_LUCKY_COIN:
            case FOUR_LEAF_CLOVER:
            case ENCHANTED_HOURGLASS:
            case ICE_CLOAK:
            case FIRE_CLOAK:
            case LIGHTNING_HELM:
            case SNAKE_RING:
            case HOLY_PENDANT:
            case PENDANT_FREE_WILL:
            case PENDANT_LIFE:
            case PENDANT_DEATH:
            case GOLDEN_BOW:
            case TELESCOPE:
            case SERENITY_PENDANT:
            case STATESMAN_QUILL:
            case KINETIC_PENDANT:
            case SEEING_EYE_PENDANT:
                return ART_LEVEL1;

            case CASTER_BRACELET:
            case MAGE_RING:
            case STEALTH_SHIELD:
            case POWER_AXE:
            case MINOR_SCROLL:
            case ENDLESS_PURSE_GOLD:
            case SAILORS_ASTROLABE_MOBILITY:
            case ENDLESS_CORD_WOOD:
            case ENDLESS_CART_ORE:
            case SPIKED_HELM:
            case WHITE_PEARL:
            case EVIL_EYE:
            case GOLD_WATCH:
            case ANKH:
            case BOOK_ELEMENTS:
            case ELEMENTAL_RING:
            case SKULLCAP:
            case EVERCOLD_ICICLE:
            case POWER_RING:
            case AMMO_CART:
            case EVERHOT_LAVA_ROCK:
                return ART_LEVEL2;

            case ARCANE_NECKLACE:
            case WITCHES_BROACH:
            case BALLISTA:
            case DRAGON_SWORD:
            case DIVINE_BREASTPLATE:
            case MAJOR_SCROLL:
            case SUPERIOR_SCROLL:
            case FOREMOST_SCROLL:
            case ENDLESS_SACK_GOLD:
            case ENDLESS_BAG_GOLD:
            case NOMAD_BOOTS_MOBILITY:
            case TRAVELER_BOOTS_MOBILITY:
            case TRUE_COMPASS_MOBILITY:
            case ENDLESS_POUCH_SULFUR:
            case ENDLESS_POUCH_GEMS:
            case ENDLESS_POUCH_CRYSTAL:
            case ENDLESS_VIAL_MERCURY:
            case SPIKED_SHIELD:
            case BLACK_PEARL:
            case LIGHTNING_ROD:
            case WAND_NEGATION:
            case WIZARD_HAT:
                return ART_LEVEL3;

            case ULTIMATE_BOOK:
            case ULTIMATE_SWORD:
            case ULTIMATE_CLOAK:
            case ULTIMATE_WAND:
            case ULTIMATE_SHIELD:
            case ULTIMATE_STAFF:
            case ULTIMATE_CROWN:
            case GOLDEN_GOOSE:
                return ART_ULTIMATE;

            // no random
            case MAGIC_BOOK:
            case FIZBIN_MISFORTUNE:
            case TAX_LIEN:
            case HIDEOUS_MASK:
                return ART_NORANDOM;

            // price loyalty
            case SPELL_SCROLL:
            case ARM_MARTYR:
            case BREASTPLATE_ANDURAN:
            case BROACH_SHIELDING:
            case BATTLE_GARB:
            case CRYSTAL_BALL:
            case HELMET_ANDURAN:
            case HOLY_HAMMER:
            case LEGENDARY_SCEPTER:
            case MASTHEAD:
            case SPHERE_NEGATION:
            case STAFF_WIZARDRY:
            case SWORD_BREAKER:
            case SWORD_ANDURAN:
            case SPADE_NECROMANCY:
            case HEART_FIRE:
            case HEART_ICE:
                return Settings.Get().PriceLoyaltyVersion() ? ART_LOYALTY | LoyaltyLevel() : ART_LOYALTY;

            default:
                break;
        }

        return ART_NONE;
    }

    private int LoyaltyLevel() {
        //TODO

        return 0;
    }

    public int GetID() {
        return id;
    }

    public String GetName() {
        return ArtifactStats.artifacts[id].name;
    }

    public void SetId(int artId) {
        id = artId;
    }

    public int IndexSprite() {
        return id < UNKNOWN ? id * 2 + 1 : 0;
    }

    public boolean IsValid() {
        return id != UNKNOWN;
    }

    public void SetSpell(int v) {
        boolean adv = Rand.Get(1) != 0;

        switch (v) {
            case SpellKind.RANDOM -> ext = Spell.Rand(Rand.Get(1, 5), adv).GetID();
            case SpellKind.RANDOM1 -> ext = Spell.Rand(1, adv).GetID();
            case SpellKind.RANDOM2 -> ext = Spell.Rand(2, adv).GetID();
            case SpellKind.RANDOM3 -> ext = Spell.Rand(3, adv).GetID();
            case SpellKind.RANDOM4 -> ext = Spell.Rand(4, adv).GetID();
            case SpellKind.RANDOM5 -> ext = Spell.Rand(5, adv).GetID();
            default -> ext = v;
        }
    }

    public int Type() {
        return ArtifactStats.artifacts[id].type;
    }

    public int GetSpell() {
        return id == SPELL_SCROLL ? ext : SpellKind.NONE;
    }
}
