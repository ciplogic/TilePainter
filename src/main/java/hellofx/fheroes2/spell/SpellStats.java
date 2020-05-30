package hellofx.fheroes2.spell;

import hellofx.fheroes2.resource.cost_t;

import java.util.ArrayList;
import java.util.List;

import static hellofx.fheroes2.resource.cost_t.NONE;
import static hellofx.fheroes2.system.Translate.tr;

public class SpellStats {
    public static final List<spellstats_t> _items = new ArrayList<>();
    public static spellstats_t[] spells;

    static {
        setup();
        spells = new spellstats_t[_items.size()];
        _items.toArray(spells);
        _items.clear();
    }

    public static void setup() {
        //  name                      sp   mp  spr value  bits cost     description
        add("Unknown", 0, 0, 0, 0, 0, NONE, "Unknown spell.");
        add(
                tr("Fireball"), 8, 0, 8, 13, 0, NONE,
                tr("Causes a giant fireball to strike the selected area, damaging all nearby creatures.")
        );
        add(
                tr("Fireblast"), 14, 0, 9, 13, 0, NONE,
                tr(
                        "An improved version of fireball, fireblast affects two hexes around the center point of the spell, rather than one."
                )
        );
        add(
                tr("Lightning Bolt"), 7, 0, 4, 25, 0, NONE,
                tr("Causes a bolt of electrical energy to strike the selected creature.")
        );
        add(
                tr("Chain Lightning"), 20, 0, 5, 40, 0, NONE,
                tr(
                        "Causes a bolt of electrical energy to strike a selected creature, then strike the nearest creature with half damage, then strike the NEXT nearest creature with half again damage, and so on, until it becomes too weak to be harmful.  Warning:  This spell can hit your own creatures!"
                )
        );
        add(
                tr("Teleport"), 9, 0, 10, 0, 0, NONE,
                tr("Teleports the creature you select to any open position on the battlefield.")
        );
        add(
                tr("Cure"), 6, 0, 6, 5, 0, NONE,
                tr(
                        "Removes all negative spells cast upon one of your units, and restores up to %add(count} HP per level of spell power."
                )
        );
        add(
                tr("Mass Cure"), 15, 0, 2, 5, 0, NONE,
                tr(
                        "Removes all negative spells cast upon your forces, and restores up to %add(count} HP per level of spell power, per creature."
                )
        );
        add(
                tr("Resurrect"), 13, 0, 13, 50, 0, NONE,
                tr("Resurrects creatures from a damaged or dead unit until end of combat.")
        );
        add(
                tr("Resurrect True"), 20, 0, 12, 50, 0, NONE,
                tr("Resurrects creatures from a damaged or dead unit permanently.")
        );
        add(tr("Haste"), 3, 0, 14, 0, 0, NONE, tr("Increases the speed of any creature by %add(count}."));
        add(tr("Mass Haste"), 10, 0, 14, 0, 0, NONE, tr("Increases the speed of all of your creatures by %add(count}."));
        add(tr("spell|Slow"), 3, 0, 1, 0, 0, NONE, tr("Slows target to half movement rate."));
        add(tr("Mass Slow"), 15, 0, 1, 0, 0, NONE, tr("Slows all enemies to half movement rate."));
        //
        add(tr("Blind "), 7, 0, 21, 0, 0, NONE, tr("Clouds the affected creatures' eyes, preventing them from moving."));
        add(tr("Bless"), 3, 0, 7, 0, 0, NONE, tr("Causes the selected creatures to inflict maximum damage."));
        add(tr("Mass Bless"), 12, 0, 7, 0, 0, NONE, tr("Causes all of your units to inflict maximum damage."));
        add(tr("Stoneskin"), 3, 0, 31, 3, 0, NONE, tr("Magically increases the defense skill of the selected creatures."));
        add(
                tr("Steelskin"), 6, 0, 30, 5, 0, NONE,
                tr("Increases the defense skill of the targeted creatures.  This is an improved version of Stoneskin.")
        );
        add(tr("Curse"), 3, 0, 3, 0, 0, NONE, tr("Causes the selected creatures to inflict minimum damage."));
        add(tr("Mass Curse"), 12, 0, 3, 0, 0, NONE, tr("Causes all enemy troops to inflict minimum damage."));
        add(tr("Holy Word"), 9, 0, 22, 10, 0, NONE, tr("Damages all undead in the battle."));
        add(
                tr("Holy Shout"), 14, 0, 23, 20, 0, NONE,
                tr("Damages all undead in the battle.  This is an improved version of Holy Word.")
        );
        add(tr("Anti-Magic"), 7, 0, 17, 0, 0, NONE, tr("Prevents harmful magic against the selected creatures."));
        add(tr("Dispel Magic"), 5, 0, 18, 0, 0, NONE, tr("Removes all magic spells from a single target."));
        add(tr("Mass Dispel"), 12, 0, 18, 0, 0, NONE, tr("Removes all magic spells from all creatures."));
        add(tr("Magic Arrow"), 3, 0, 38, 10, 0, NONE, tr("Causes a magic arrow to strike the selected target."));
        add(tr("Berserker"), 12, 0, 19, 0, 0, NONE, tr("Causes a creature to attack its nearest neighbor."));
        add(
                tr("Armageddon"), 30, 0, 16, 50, 0, NONE,
                tr("Holy terror strikes the battlefield, causing severe damage to all creatures.")
        );
        add(
                tr("Elemental Storm"), 20, 0, 11, 25, 0, NONE,
                tr("Magical elements pour down on the battlefield, damaging all creatures.")
        );
        add(
                tr("Meteor Shower"), 18, 0, 24, 25, 0, NONE,
                tr("A rain of rocks strikes an area of the battlefield, damaging all nearby creatures.")
        );
        add(tr("Paralyze"), 9, 0, 20, 0, 0, NONE, tr("The targeted creatures are paralyzed, unable to move or retaliate."));
        add(
                tr("Hypnotize"), 15, 0, 37, 25, 0, NONE,
                tr(
                        "Brings a single enemy unit under your control for one combat round if its hits are less than %add(count} times the caster's spell power."
                )
        );
        add(tr("Cold Ray"), 6, 0, 36, 20, 0, NONE, tr("Drains body heat from a single enemy unit."));
        add(
                tr("Cold Ring"), 8, 0, 35, 13, 0, NONE,
                tr("Drains body heat from all units surrounding the center point, but not including the center point.")
        );
        add(tr("Disrupting Ray"), 7, 0, 34, 3, 0, NONE, tr("Reduces the defense rating of an enemy unit by three."));
        add(tr("Death Ripple"), 6, 0, 28, 5, 0, NONE, tr("Damages all living (non-undead) units in the battle."));
        add(
                tr("Death Wave"), 10, 0, 29, 10, 0, NONE,
                tr("Damages all living (non-undead) units in the battle.  This spell is an improved version of Death Ripple.")
        );
        add(tr("Dragon Slayer"), 6, 0, 32, 5, 0, NONE, tr("Greatly increases a unit's attack skill vs. Dragons."));
        add(tr("Blood Lust"), 3, 0, 27, 3, 0, NONE, tr("Increases a unit's attack skill."));
        add(
                tr("Animate Dead"), 15, 0, 25, 50, 0, NONE,
                tr("Resurrects creatures from a damaged or dead undead unit permanently.")
        );
        add(
                tr("Mirror Image"), 20, 0, 26, 0, 0, NONE,
                tr(
                        "Creates an illusionary unit that duplicates one of your existing units.  This illusionary unit does the same damages as the original, but will vanish if it takes any damage."
                )
        );
        add(tr("Shield"), 3, 0, 15, 2, 0, NONE, tr("Halves damage received from ranged attacks for a single unit."));
        add(
                tr("Mass Shield"), 7, 0, 15, 0, 0, NONE,
                tr("Halves damage received from ranged attacks for all of your units.")
        );
        add(tr("Summon Earth Elemental"), 30, 0, 56, 3, 0, NONE, tr("Summons Earth Elementals to fight for your army."));
        add(tr("Summon Air Elemental"), 30, 0, 57, 3, 0, NONE, tr("Summons Air Elementals to fight for your army."));
        add(tr("Summon Fire Elemental"), 30, 0, 58, 3, 0, NONE, tr("Summons Fire Elementals to fight for your army."));
        add(tr("Summon Water Elemental"), 30, 0, 59, 3, 0, NONE, tr("Summons Water Elementals to fight for your army."));
        add(tr("Earthquake"), 15, 0, 33, 0, 0, NONE, tr("Damages castle walls."));
        add(tr("View Mines"), 1, 0, 39, 0, 0, NONE, tr("Causes all mines across the land to become visible."));
        add(tr("View Resources"), 1, 0, 40, 0, 0, NONE, tr("Causes all resources across the land to become visible."));
        add(tr("View Artifacts"), 2, 0, 41, 0, 0, NONE, tr("Causes all artifacts across the land to become visible."));
        add(tr("View Towns"), 2, 0, 42, 0, 0, NONE, tr("Causes all towns and castles across the land to become visible."));
        add(tr("View Heroes"), 2, 0, 43, 0, 0, NONE, tr("Causes all Heroes across the land to become visible."));
        add(tr("View All"), 3, 0, 44, 0, 0, NONE, tr("Causes the entire land to become visible."));
        add(tr("Identify Hero"), 3, 0, 45, 0, 0, NONE,
                tr("Allows the caster to view detailed information on enemy Heroes.")
        );
        add(
                tr("Summon Boat"), 5, 0, 46, 0, 0, NONE,
                tr(
                        "Summons the nearest unoccupied, friendly boat to an adjacent shore location.  A friendly boat is one which you just built or were the most recent player to occupy."
                )
        );
        add(
                tr("Dimension Door"), 22, 0, 47, 0, 0, NONE,
                tr("Allows the caster to magically transport to a nearby location.")
        );
        add(tr("Town Gate"), 10, 0, 48, 0, 0, NONE, tr("Returns the caster to any town or castle currently owned."));
        add(
                tr("Town Portal"), 20, 0, 49, 0, 0, NONE,
                tr("Returns the hero to the town or castle of choice, provided it is controlled by you.")
        );
        add(
                tr("Visions"), 6, 0, 50, 3, 0, NONE,
                tr("Visions predicts the likely outcome of an encounter with a neutral army camp.")
        );
        add(
                tr("Haunt"), 8, 0, 51, 4, 0, NONE,
                tr(
                        "Haunts a mine you control with Ghosts.  This mine stops producing resources.  (If I can't keep it, nobody will!)"
                )
        );
        add(
                tr("Set Earth Guardian"), 15, 0, 52, 4, 0, NONE,
                tr("Sets Earth Elementals to guard a mine against enemy armies.")
        );
        add(tr("Set Air Guardian"), 15, 0, 53, 4, 0, NONE, tr("Sets Air Elementals to guard a mine against enemy armies."));
        add(
                tr("Set Fire Guardian"), 15, 0, 54, 4, 0, NONE,
                tr("Sets Fire Elementals to guard a mine against enemy armies.")
        );
        add(
                tr("Set Water Guardian"), 15, 0, 55, 4, 0, NONE,
                tr("Sets Water Elementals to guard a mine against enemy armies.")
        );
        add("Random", 0, 0, 0, 0, 0, NONE, "Random");
        add("Random 1", 0, 0, 0, 0, 0, NONE, "Random 1");
        add("Random 2", 0, 0, 0, 0, 0, NONE, "Random 2");
        add("Random 3", 0, 0, 0, 0, 0, NONE, "Random 3");
        add("Random 4", 0, 0, 0, 0, 0, NONE, "Random 4");
        add("Random 5", 0, 0, 0, 0, 0, NONE, "Random 5");
        add("Stone", 0, 0, 0, 0, 0, NONE, "Stone spell from Medusa.");
    }

    public static void add(String name,
                           int sp,
                           int mp,
                           int sprite,
                           int extra,
                           int bits,
                           cost_t cost,
                           String description) {
        var item = new spellstats_t();
        item.sp = (short) sp;
        item.mp = mp;
        item.sprite = (short) sprite;
        item.extra = (short) extra;
        item.bits = (short) bits;
        item.cost = cost;
        item.description = description;
        _items.add(item);
    }
}
