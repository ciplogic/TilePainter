package hellofx.fheroes2.monster;

import hellofx.fheroes2.kingdom.SpeedKind;
import hellofx.fheroes2.resource.cost_t;

import java.util.ArrayList;
import java.util.List;

import static hellofx.fheroes2.system.Translate.tr;

public class MonsterStats {
    public static List<monstats_t> monsters = new ArrayList<>();

    static {
        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Unknown Monster", "Unknown Monsters", new cost_t(0, 0, 0, 0, 0, 0, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname             cost
        add(1, 1, 1, 1, 1, SpeedKind.SLOW, 26, 0, tr("Peasant"), tr("Peasants"), new cost_t(15, 0, 0, 0, 0, 0, 0));
        add(5, 3, 2, 3, 11, SpeedKind.VERYSLOW, 8, 12, tr("Archer"), tr("Archers"), new cost_t(150, 0, 0, 0, 0, 0, 0));
        add(5, 3, 2, 3, 11, SpeedKind.AVERAGE, 8, 24, tr("Ranger"), tr("Rangers"), new cost_t(200, 0, 0, 0, 0, 0, 0));
        add(5, 9, 3, 5, 16, SpeedKind.AVERAGE, 5, 0, tr("Pikeman"), tr("Pikemen"), new cost_t(210, 0, 0, 0, 0, 0, 0));
        add(5, 9, 3, 5, 21, SpeedKind.FAST, 5, 0, tr("Veteran Pikeman"), tr("Veteran Pikemen"), new cost_t(260, 0, 0, 0, 0, 0, 0));
        add(7, 9, 4, 7, 26, SpeedKind.AVERAGE, 4, 0, tr("Swordsman"), tr("Swordsmen"), new cost_t(260, 0, 0, 0, 0, 0, 0));
        add(7, 9, 4, 7, 31, SpeedKind.FAST, 4, 0, tr("Master Swordsman"), tr("Master Swordsmen"), new cost_t(310, 0, 0, 0, 0, 0, 0));
        add(10, 9, 5, 10, 35, SpeedKind.VERYFAST, 3, 0, tr("Cavalry"), tr("Cavalries"), new cost_t(340, 0, 0, 0, 0, 0, 0));
        add(10, 9, 5, 10, 45, SpeedKind.ULTRAFAST, 3, 0, tr("Champion"), tr("Champions"), new cost_t(415, 0, 0, 0, 0, 0, 0));
        add(11, 12, 10, 20, 60, SpeedKind.FAST, 2, 0, tr("Paladin"), tr("Paladins"), new cost_t(675, 0, 0, 0, 0, 0, 0));
        add(11, 12, 10, 20, 75, SpeedKind.VERYFAST, 2, 0, tr("Crusader"), tr("Crusaders"), new cost_t(1000, 0, 0, 0, 0, 0, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname            cost
        add(3, 1, 1, 2, 3, SpeedKind.AVERAGE, 12, 0, tr("Goblin"), tr("Goblins"), new cost_t(40, 0, 0, 0, 0, 0, 0));
        add(3, 4, 2, 3, 11, SpeedKind.VERYSLOW, 8, 8, tr("Orc"), tr("Orcs"), new cost_t(140, 0, 0, 0, 0, 0, 0));
        add(3, 4, 3, 4, 15, SpeedKind.SLOW, 8, 16, tr("Orc Chief"), tr("Orc Chiefs"), new cost_t(175, 0, 0, 0, 0, 0, 0));
        add(6, 3, 3, 5, 20, SpeedKind.VERYFAST, 5, 0, tr("Wolf"), tr("Wolves"), new cost_t(210, 0, 0, 0, 0, 0, 0));
        add(9, 5, 4, 6, 40, SpeedKind.VERYSLOW, 4, 0, tr("Ogre"), tr("Ogres"), new cost_t(300, 0, 0, 0, 0, 0, 0));
        add(9, 5, 5, 7, 55, SpeedKind.AVERAGE, 4, 0, tr("Ogre Lord"), tr("Ogre Lords"), new cost_t(475, 0, 0, 0, 0, 0, 0));
        add(10, 5, 5, 7, 40, SpeedKind.AVERAGE, 3, 8, tr("Troll"), tr("Trolls"), new cost_t(575, 0, 0, 0, 0, 0, 0));
        add(10, 5, 7, 9, 40, SpeedKind.FAST, 3, 16, tr("War Troll"), tr("War Trolls"), new cost_t(650, 0, 0, 0, 0, 0, 0));
        add(12, 9, 12, 24, 85, SpeedKind.FAST, 2, 0, tr("Cyclops"), tr("Cyclopes"), new cost_t(800, 0, 0, 0, 0, 1, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname            cost
        add(4, 2, 1, 2, 2, SpeedKind.AVERAGE, 10, 0, tr("Sprite"), tr("Sprites"), new cost_t(50, 0, 0, 0, 0, 0, 0));
        add(6, 5, 2, 4, 20, SpeedKind.VERYSLOW, 6, 0, tr("Dwarf"), tr("Dwarves"), new cost_t(190, 0, 0, 0, 0, 0, 0));
        add(6, 6, 2, 4, 20, SpeedKind.AVERAGE, 6, 0, tr("Battle Dwarf"), tr("Battle Dwarves"), new cost_t(235, 0, 0, 0, 0, 0, 0));
        add(5, 3, 2, 3, 16, SpeedKind.AVERAGE, 4, 24, tr("Elf"), tr("Elves"), new cost_t(250, 0, 0, 0, 0, 0, 0));
        add(7, 5, 2, 3, 16, SpeedKind.VERYFAST, 4, 24, tr("Grand Elf"), tr("Grand Elves"), new cost_t(300, 0, 0, 0, 0, 0, 0));
        add(7, 5, 5, 8, 25, SpeedKind.FAST, 3, 8, tr("Druid"), tr("Druids"), new cost_t(350, 0, 0, 0, 0, 0, 0));
        add(7, 7, 5, 8, 26, SpeedKind.VERYFAST, 3, 16, tr("Greater Druid"), tr("Greater Druids"), new cost_t(400, 0, 0, 0, 0, 0, 0));
        add(10, 9, 7, 14, 45, SpeedKind.FAST, 2, 0, tr("Unicorn"), tr("Unicorns"), new cost_t(515, 0, 0, 0, 0, 0, 0));
        add(12, 10, 20, 40, 100, SpeedKind.ULTRAFAST, 1, 0, tr("Phoenix"), tr("Phoenixes"), new cost_t(1500, 0, 1, 0, 0, 0, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname            cost
        add(3, 1, 1, 2, 5, SpeedKind.AVERAGE, 8, 8, tr("Centaur"), tr("Centaurs"), new cost_t(60, 0, 0, 0, 0, 0, 0));
        add(4, 6, 2, 3, 15, SpeedKind.VERYFAST, 6, 0, tr("Gargoyle"), tr("Gargoyles"), new cost_t(200, 0, 0, 0, 0, 0, 0));
        add(6, 6, 3, 5, 25, SpeedKind.AVERAGE, 4, 0, tr("Griffin"), tr("Griffins"), new cost_t(320, 0, 0, 0, 0, 0, 0));
        add(9, 8, 5, 10, 35, SpeedKind.AVERAGE, 3, 0, tr("Minotaur"), tr("Minotaurs"), new cost_t(400, 0, 0, 0, 0, 0, 0));
        add(9, 8, 5, 10, 45, SpeedKind.VERYFAST, 3, 0, tr("Minotaur King"), tr("Minotaur Kings"), new cost_t(500, 0, 0, 0, 0, 0, 0));
        add(8, 9, 6, 12, 75, SpeedKind.VERYSLOW, 2, 0, tr("Hydra"), tr("Hydras"), new cost_t(775, 0, 0, 0, 0, 0, 0));
        add(12, 12, 25, 50, 200, SpeedKind.AVERAGE, 0, 0, tr("Green Dragon"), tr("Green Dragons"), new cost_t(3000, 0, 0, 0, 1, 0, 0));
        add(13, 13, 25, 50, 240, SpeedKind.FAST, 0, 0, tr("Red Dragon"), tr("Red Dragons"), new cost_t(3500, 0, 0, 0, 1, 0, 0));
        add(14, 14, 25, 50, 280, SpeedKind.VERYFAST, 0, 0, tr("Black Dragon"), tr("Black Dragons"), new cost_t(4000, 0, 0, 0, 2, 0, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname            cost
        add(2, 1, 1, 3, 3, SpeedKind.SLOW, 8, 12, tr("Halfling"), tr("Halflings"), new cost_t(55, 0, 0, 0, 0, 0, 0));
        add(5, 4, 2, 4, 15, SpeedKind.VERYFAST, 6, 0, tr("Boar"), tr("Boars"), new cost_t(155, 0, 0, 0, 0, 0, 0));
        add(5, 10, 4, 5, 30, SpeedKind.VERYSLOW, 4, 0, tr("Iron Golem"), tr("Iron Golems"), new cost_t(300, 0, 0, 0, 0, 0, 0));
        add(7, 10, 4, 5, 35, SpeedKind.SLOW, 4, 0, tr("Steel Golem"), tr("Steel Golems"), new cost_t(350, 0, 0, 0, 0, 0, 0));
        add(7, 8, 4, 8, 40, SpeedKind.AVERAGE, 3, 0, tr("Roc"), tr("Rocs"), new cost_t(400, 0, 0, 0, 0, 0, 0));
        add(11, 7, 7, 9, 30, SpeedKind.FAST, 2, 12, tr("Mage"), tr("Magi"), new cost_t(600, 0, 0, 0, 0, 0, 0));
        add(12, 8, 7, 9, 35, SpeedKind.VERYFAST, 2, 24, tr("Archmage"), tr("Archmagi"), new cost_t(700, 0, 0, 0, 0, 0, 0));
        add(13, 10, 20, 30, 155, SpeedKind.AVERAGE, 0, 0, tr("Giant"), tr("Giants"), new cost_t(2000, 0, 0, 0, 0, 0, 1));
        add(15, 15, 20, 30, 280, SpeedKind.VERYFAST, 0, 24, tr("Titan"), tr("Titans"), new cost_t(4500, 0, 0, 0, 0, 0, 2));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname            cost
        add(4, 3, 2, 3, 4, SpeedKind.AVERAGE, 8, 0, tr("Skeleton"), tr("Skeletons"), new cost_t(70, 0, 0, 0, 0, 0, 0));
        add(5, 3, 2, 3, 15, SpeedKind.SLOW, 6, 0, tr("Zombie"), tr("Zombies"), new cost_t(140, 0, 0, 0, 0, 0, 0));
        add(5, 3, 2, 3, 25, SpeedKind.AVERAGE, 6, 0, tr("Mutant Zombie"), tr("Mutant Zombies"), new cost_t(190, 0, 0, 0, 0, 0, 0));
        add(6, 7, 3, 4, 25, SpeedKind.AVERAGE, 4, 0, tr("Mummy"), tr("Mummies"), new cost_t(250, 0, 0, 0, 0, 0, 0));
        add(6, 7, 3, 4, 30, SpeedKind.FAST, 4, 0, tr("Royal Mummy"), tr("Royal Mummies"), new cost_t(300, 0, 0, 0, 0, 0, 0));
        add(8, 6, 5, 7, 30, SpeedKind.AVERAGE, 3, 0, tr("Vampire"), tr("Vampires"), new cost_t(500, 0, 0, 0, 0, 0, 0));
        add(8, 6, 5, 7, 40, SpeedKind.FAST, 3, 0, tr("Vampire Lord"), tr("Vampire Lords"), new cost_t(650, 0, 0, 0, 0, 0, 0));
        add(7, 11, 8, 10, 30, SpeedKind.FAST, 2, 12, tr("Lich"), tr("Liches"), new cost_t(650, 0, 0, 0, 0, 0, 0));
        add(7, 13, 8, 10, 35, SpeedKind.VERYFAST, 2, 24, tr("Power Lich"), tr("Power Liches"), new cost_t(750, 0, 0, 0, 0, 0, 0));
        add(11, 9, 25, 45, 140, SpeedKind.AVERAGE, 1, 0, tr("Bone Dragon"), tr("Bone Dragons"), new cost_t(1600, 0, 0, 0, 0, 0, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname                cost
        add(6, 1, 1, 2, 4, SpeedKind.FAST, 4, 0, tr("Rogue"), tr("Rogues"), new cost_t(50, 0, 0, 0, 0, 0, 0));
        add(7, 6, 2, 5, 20, SpeedKind.VERYFAST, 4, 0, tr("Nomad"), tr("Nomads"), new cost_t(200, 0, 0, 0, 0, 0, 0));
        add(8, 7, 4, 6, 20, SpeedKind.FAST, 4, 0, tr("Ghost"), tr("Ghosts"), new cost_t(1000, 0, 0, 0, 0, 0, 0));
        add(10, 9, 20, 30, 50, SpeedKind.VERYFAST, 4, 0, tr("Genie"), tr("Genies"), new cost_t(650, 0, 0, 0, 0, 0, 1));
        add(8, 9, 6, 10, 35, SpeedKind.AVERAGE, 4, 0, tr("Medusa"), tr("Medusas"), new cost_t(500, 0, 0, 0, 0, 0, 0));
        add(8, 8, 4, 5, 50, SpeedKind.SLOW, 4, 0, tr("Earth Elemental"), tr("Earth Elementals"), new cost_t(500, 0, 0, 0, 0, 0, 0));
        add(7, 7, 2, 8, 35, SpeedKind.VERYFAST, 4, 0, tr("Air Elemental"), tr("Air Elementals"), new cost_t(500, 0, 0, 0, 0, 0, 0));
        add(8, 6, 4, 6, 40, SpeedKind.FAST, 4, 0, tr("Fire Elemental"), tr("Fire Elementals"), new cost_t(500, 0, 0, 0, 0, 0, 0));
        add(6, 8, 3, 7, 45, SpeedKind.AVERAGE, 4, 0, tr("Water Elemental"), tr("Water Elementals"), new cost_t(500, 0, 0, 0, 0, 0, 0));

        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Random Monster", "Random Monsters", new cost_t(0, 0, 0, 0, 0, 0, 0));
        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Random Monster 1", "Random Monsters 3", new cost_t(0, 0, 0, 0, 0, 0, 0));
        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Random Monster 2", "Random Monsters 2", new cost_t(0, 0, 0, 0, 0, 0, 0));
        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Random Monster 3", "Random Monsters 3", new cost_t(0, 0, 0, 0, 0, 0, 0));
        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Random Monster 4", "Random Monsters 4", new cost_t(0, 0, 0, 0, 0, 0, 0));
    }

    static void add(int attack, int defense, int damageMin, int damageMax, int hp, int speed, int grown, int shots, String name, String multiname, cost_t cost) {
        var stat = new monstats_t(attack, defense, damageMin, damageMax, hp, speed, grown, shots, name, multiname, cost);
        monsters.add(stat);
    }

    public static monstats_t get(int index) {
        return monsters.get(index);
    }

}
