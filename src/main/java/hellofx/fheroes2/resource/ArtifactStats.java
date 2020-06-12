package hellofx.fheroes2.resource;

import java.util.ArrayList;
import java.util.List;

import static hellofx.fheroes2.system.Translate.tr;

public class ArtifactStats {
    public static List<artifactstats_t> _items = new ArrayList<>();
    public static artifactstats_t[] artifacts;

    static {
        setup();
        artifacts = new artifactstats_t[_items.size()];
        _items.toArray(artifacts);
        _items.clear();
    }

    public static artifactstats_t Get(int index) {
        return _items.get(index);
    }

    private static void setup() {
        add(0, 12, ArtifactType.TYPE3, tr("Ultimate Book of Knowledge"), tr("The %{name} increases your knowledge by %{count}."));
        add(0, 12, ArtifactType.TYPE3, tr("Ultimate Sword of Dominion"), tr("The %{name} increases your attack skill by %{count}."));
        add(0, 12, ArtifactType.TYPE3, tr("Ultimate Cloak of Protection"), tr("The %{name} increases your defense skill by %{count}."));
        add(0, 12, ArtifactType.TYPE3, tr("Ultimate Wand of Magic"), tr("The %{name} increases your spell power by %{count}."));
        add(0, 6, ArtifactType.TYPE3, tr("Ultimate Shield"), tr("The %{name} increases your attack and defense skills by %{count} each."));
        add(0, 6, ArtifactType.TYPE3, tr("Ultimate Staff"), tr("The %{name} increases your spell power and knowledge by %{count} each."));
        add(0, 4, ArtifactType.TYPE3, tr("Ultimate Crown"), tr("The %{name} increases each of your basic skills by %{count} points."));
        add(0, 10, ArtifactType.TYPE2, tr("Golden Goose"), tr("The %{name} brings in an income of %{count} gold per turn."));
        add(0, 4, ArtifactType.TYPE3, tr("Arcane Necklace of Magic"), tr("The %{name} increases your spell power by %{count}."));
        add(0, 2, ArtifactType.TYPE3, tr("Caster's Bracelet of Magic"), tr("The %{name} increases your spell power by %{count}."));
        add(0, 2, ArtifactType.TYPE3, tr("Mage's Ring of Power"), tr("The %{name} increases your spell power by %{count}."));
        add(0, 3, ArtifactType.TYPE3, tr("Witch's Broach of Magic"), tr("The %{name} increases your spell power by %{count}."));
        add(0, 1, ArtifactType.TYPE1, tr("Medal of Valor"), tr("The %{name} increases your morale."));
        add(0, 1, ArtifactType.TYPE1, tr("Medal of Courage"), tr("The %{name} increases your morale."));
        add(0, 1, ArtifactType.TYPE1, tr("Medal of Honor"), tr("The %{name} increases your morale."));
        add(0, 1, ArtifactType.TYPE1, tr("Medal of Distinction"), tr("The %{name} increases your morale."));
        add(0, 2, ArtifactType.TYPE1, tr("Fizbin of Misfortune"), tr("The %{name} greatly decreases your morale by %{count}."));
        add(0, 1, ArtifactType.TYPE3, tr("Thunder Mace of Dominion"), tr("The %{name} increases your attack skill by %{count}."));
        add(0, 1, ArtifactType.TYPE3, tr("Armored Gauntlets of Protection"), tr("The %{name} increase your defense skill by %{count}."));
        add(0, 1, ArtifactType.TYPE3, tr("Defender Helm of Protection"), tr("The %{name} increases your defense skill by %{count}."));
        add(0, 1, ArtifactType.TYPE3, tr("Giant Flail of Dominion"), tr("The %{name} increases your attack skill by %{count}."));
        add(0, 2, ArtifactType.TYPE0, tr("Ballista of Quickness"), tr("The %{name} lets your catapult fire twice per combat round."));
        add(0, 2, ArtifactType.TYPE3, tr("Stealth Shield of Protection"), tr("The %{name} increases your defense skill by %{count}."));
        add(0, 3, ArtifactType.TYPE3, tr("Dragon Sword of Dominion"), tr("The %{name} increases your attack skill by %{count}."));
        add(0, 2, ArtifactType.TYPE3, tr("Power Axe of Dominion"), tr("The %{name} increases your attack skill by %{count}."));
        add(0, 3, ArtifactType.TYPE3, tr("Divine Breastplate of Protection"), tr("The %{name} increases your defense skill by %{count}."));
        add(0, 2, ArtifactType.TYPE3, tr("Minor Scroll of Knowledge"), tr("The %{name} increases your knowledge by %{count}."));
        add(0, 3, ArtifactType.TYPE3, tr("Major Scroll of Knowledge"), tr("The %{name} increases your knowledge by %{count}."));
        add(0, 4, ArtifactType.TYPE3, tr("Superior Scroll of Knowledge"), tr("The %{name} increases your knowledge by %{count}."));
        add(0, 5, ArtifactType.TYPE3, tr("Foremost Scroll of Knowledge"), tr("The %{name} increases your knowledge by %{count}."));
        add(0, 100, ArtifactType.TYPE2, tr("Endless Sack of Gold"), tr("The %{name} provides you with %{count} gold per day."));
        add(0, 75, ArtifactType.TYPE2, tr("Endless Bag of Gold"), tr("The %{name} provides you with %{count} gold per day."));
        add(0, 50, ArtifactType.TYPE2, tr("Endless Purse of Gold"), tr("The %{name} provides you with %{count} gold per day."));
        add(0, 0, ArtifactType.TYPE3, tr("Nomad Boots of Mobility"), tr("The %{name} increase your movement on land."));
        add(0, 0, ArtifactType.TYPE3, tr("Traveler's Boots of Mobility"), tr("The %{name} increase your movement on land."));
        add(0, 1, ArtifactType.TYPE1, tr("Lucky Rabbit's Foot"), tr("The %{name} increases your luck in combat."));
        add(0, 1, ArtifactType.TYPE1, tr("Golden Horseshoe"), tr("The %{name} increases your luck in combat."));
        add(0, 1, ArtifactType.TYPE1, tr("Gambler's Lucky Coin"), tr("The %{name} increases your luck in combat."));
        add(0, 1, ArtifactType.TYPE1, tr("Four-Leaf Clover"), tr("The %{name} increases your luck in combat."));
        add(0, 0, ArtifactType.TYPE3, tr("True Compass of Mobility"), tr("The %{name} increases your movement on land and sea."));
        add(0, 0, ArtifactType.TYPE3, tr("Sailor's Astrolabe of Mobility"), tr("The %{name} increases your movement on sea."));
        add(0, 0, ArtifactType.TYPE0, tr("Evil Eye"), tr("The %{name} reduces the casting cost of curse spells by half."));
        add(
                0, 2, ArtifactType.TYPE0, tr("Enchanted Hourglass"),
                tr("The %{name} extends the duration of all your spells by %{count} turns.")
        );
        add(0, 0, ArtifactType.TYPE0, tr("Gold Watch"), tr("The %{name} doubles the effectiveness of your hypnotize spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Skullcap"), tr("The %{name} halves the casting cost of all mind influencing spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Ice Cloak"), tr("The %{name} halves all damage your troops take from cold spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Fire Cloak"), tr("The %{name} halves all damage your troops take from fire spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Lightning Helm"), tr("The %{name} halves all damage your troops take from lightning spells."));
        add(
                0, 50, ArtifactType.TYPE0, tr("Evercold Icicle"),
                tr("The %{name} causes your cold spells to do %{count} percent more damage to enemy troops.")
        );
        add(
                0, 50, ArtifactType.TYPE0, tr("Everhot Lava Rock"),
                tr("The %{name} causes your fire spells to do %{count} percent more damage to enemy troops.")
        );
        add(
                0, 50, ArtifactType.TYPE0, tr("Lightning Rod"),
                tr("The %{name} causes your lightning spells to do %{count} percent more damage to enemy troops.")
        );
        add(0, 0, ArtifactType.TYPE0, tr("Snake-Ring"), tr("The %{name} halves the casting cost of all your bless spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Ankh"), tr("The %{name} doubles the effectiveness of all your resurrect and animate spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Book of Elements"), tr("The %{name} doubles the effectiveness of all your summoning spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Elemental Ring"), tr("The %{name} halves the casting cost of all summoning spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Holy Pendant"), tr("The %{name} makes all your troops immune to curse spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Pendant of Free Will"), tr("The %{name} makes all your troops immune to hypnotize spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Pendant of Life"), tr("The %{name} makes all your troops immune to death spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Serenity Pendant"), tr("The %{name} makes all your troops immune to berserk spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Seeing-eye Pendant"), tr("The %{name} makes all your troops immune to blindness spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Kinetic Pendant"), tr("The %{name} makes all your troops immune to paralyze spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Pendant of Death"), tr("The %{name} makes all your troops immune to holy spells."));
        add(0, 0, ArtifactType.TYPE0, tr("Wand of Negation"), tr("The %{name} protects your troops from the Dispel Magic spell."));
        add(
                0, 50, ArtifactType.TYPE0, tr("Golden Bow"),
                tr(
                        "The %{name} eliminates the %{count} percent penalty for your troops shooting past obstacles. (e.g. castle walls)"
                )
        );
        add(
                0, 1, ArtifactType.TYPE4, tr("Telescope"),
                tr("The %{name} increases the amount of terrain your hero reveals when adventuring by %{count} extra square.")
        );
        add(
                0, 10, ArtifactType.TYPE0, tr("Statesman's Quill"),
                tr(
                        "The %{name} reduces the cost of surrender to %{count} percent of the total cost of troops you have in your army."
                )
        );
        add(0, 10, ArtifactType.TYPE0, tr("Wizard's Hat"), tr("The %{name} increases the duration of your spells by %{count} turns."));
        add(0, 2, ArtifactType.TYPE4, tr("Power Ring"), tr("The %{name} returns %{count} extra power points/turn to your hero."));
        add(0, 0, ArtifactType.TYPE0, tr("Ammo Cart"), tr("The %{name} provides endless ammunition for all your troops that shoot."));
        add(0, 25, ArtifactType.TYPE2, tr("Tax Lien"), tr("The %{name} costs you %{count} gold pieces/turn."));
        add(0, 0, ArtifactType.TYPE0, tr("Hideous Mask"), tr("The %{name} prevents all 'wandering' armies from joining your hero."));
        add(0, 1, ArtifactType.TYPE2, tr("Endless Pouch of Sulfur"), tr("The %{name} provides %{count} unit of sulfur per day."));
        add(0, 1, ArtifactType.TYPE2, tr("Endless Vial of Mercury"), tr("The %{name} provides %{count} unit of mercury per day."));
        add(0, 1, ArtifactType.TYPE2, tr("Endless Pouch of Gems"), tr("The %{name} provides %{count} unit of gems per day."));
        add(0, 1, ArtifactType.TYPE2, tr("Endless Cord of Wood"), tr("The %{name} provides %{count} unit of wood per day."));
        add(0, 1, ArtifactType.TYPE2, tr("Endless Cart of Ore"), tr("The %{name} provides %{count} unit of ore per day."));
        add(0, 1, ArtifactType.TYPE2, tr("Endless Pouch of Crystal"), tr("The %{name} provides %{count} unit of crystal/day."));
        add(0, 1, ArtifactType.TYPE3, tr("Spiked Helm"), tr("The %{name} increases your attack and defense skills by %{count} each."));
        add(0, 2, ArtifactType.TYPE3, tr("Spiked Shield"), tr("The %{name} increases your attack and defense skills by %{count} each."));
        add(0, 1, ArtifactType.TYPE3, tr("White Pearl"), tr("The %{name} increases your spell power and knowledge by %{count} each."));
        add(0, 2, ArtifactType.TYPE3, tr("Black Pearl"), tr("The %{name} increases your spell power and knowledge by %{count} each."));

        add(0, 0, ArtifactType.TYPE0, tr("Magic Book"), tr("The %{name} enables you to cast spells."));

        add(0, 0, ArtifactType.TYPE0, "Dummy 1", "The reserved artifact.");
        add(0, 0, ArtifactType.TYPE0, "Dummy 2", "The reserved artifact.");
        add(0, 0, ArtifactType.TYPE0, "Dummy 3", "The reserved artifact.");
        add(0, 0, ArtifactType.TYPE0, "Dummy 4", "The reserved artifact.");

        add(0, 0, ArtifactType.TYPE0, tr("Spell Scroll"), tr("This %{name} gives your hero the ability to cast the %{spell} spell."));
        add(
                0, 3, ArtifactType.TYPE3, tr("Arm of the Martyr"),
                tr("The %{name} increases your spell power by %{count} but adds the undead morale penalty.")
        );
        add(0, 5, ArtifactType.TYPE3, tr("Breastplate of Anduran"), tr("The %{name} increases your defense by %{count}."));
        add(
                0, 50, ArtifactType.TYPE3, tr("Broach of Shielding"),
                tr(
                        "The %{name} provides %{count} percent protection from Armageddon and Elemental Storm, but decreases spell power by 2."
                )
        );
        add(
                0, 5, ArtifactType.TYPE0, tr("Battle Garb of Anduran"),
                tr(
                        "The %{name} combines the powers of the three Anduran artifacts.  It provides maximum luck and morale for your troops and gives you the Town Portal spell."
                )
        );
        add(
                0, 0, ArtifactType.TYPE0, tr("Crystal Ball"),
                tr(
                        "The %{name} lets you get more specific information about monsters, enemy heroes, and castles nearby the hero who holds it."
                )
        );
        add(
                0, 50, ArtifactType.TYPE0, tr("Heart of Fire"),
                tr("The %{name} provides %{count} percent protection from fire, but doubles the damage taken from cold.")
        );
        add(
                0, 50, ArtifactType.TYPE0, tr("Heart of Ice"),
                tr("The %{name} provides %{count} percent protection from cold, but doubles the damage taken from fire.")
        );
        add(0, 5, ArtifactType.TYPE3, tr("Helmet of Anduran"), tr("The %{name} increases your spell power by %{count}."));
        add(0, 5, ArtifactType.TYPE3, tr("Holy Hammer"), tr("The %{name} increases your attack skill by %{count}."));
        add(0, 2, ArtifactType.TYPE3, tr("Legendary Scepter"), tr("The %{name} adds %{count} points to all attributes."));
        add(0, 1, ArtifactType.TYPE1, tr("Masthead"), tr("The %{name} boosts your luck and morale by %{count} each in sea combat."));
        add(0, 0, ArtifactType.TYPE0, tr("Sphere of Negation"), tr("The %{name} disables all spell casting, for both sides, in combat."));
        add(0, 5, ArtifactType.TYPE3, tr("Staff of Wizardry"), tr("The %{name} boosts your spell power by %{count}."));
        add(0, 4, ArtifactType.TYPE3, tr("Sword Breaker"), tr("The %{name} increases your defense by %{count} and attack by 1."));
        add(0, 5, ArtifactType.TYPE3, tr("Sword of Anduran"), tr("The %{name} increases your attack skill by %{count}."));
        add(0, 0, ArtifactType.TYPE4, tr("Spade of Necromancy"), tr("The %{name} gives you increased necromancy skill."));

        add(0, 0, ArtifactType.TYPE0, "Unknown", "Unknown");


    }

    private static void add(int bits, int extra, int artType, String name, String formattedDesc) {
        var art = new artifactstats_t(bits, extra, artType, name, formattedDesc);
        _items.add(art);
    }

}
