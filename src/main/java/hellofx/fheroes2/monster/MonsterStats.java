package hellofx.fheroes2.monster;

import hellofx.fheroes2.kingdom.SpeedKind;
import hellofx.fheroes2.resource.Funds;

import java.util.ArrayList;
import java.util.List;

import static hellofx.fheroes2.system.Translate.tr;

public class MonsterStats {
    public static List<monstats_t> monsters = new ArrayList<>();

    static {
        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Unknown Monster", "Unknown Monsters", new Funds(0, 0, 0, 0, 0, 0, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname             cost
        add(1, 1, 1, 1, 1, SpeedKind.SLOW, 26, 0, tr("Peasant"), tr("Peasants"), new Funds(15, 0, 0, 0, 0, 0, 0));
        add(5, 3, 2, 3, 11, SpeedKind.VERYSLOW, 8, 12, tr("Archer"), tr("Archers"), new Funds(150, 0, 0, 0, 0, 0, 0));
        add(5, 3, 2, 3, 11, SpeedKind.AVERAGE, 8, 24, tr("Ranger"), tr("Rangers"), new Funds(200, 0, 0, 0, 0, 0, 0));
        add(5, 9, 3, 5, 16, SpeedKind.AVERAGE, 5, 0, tr("Pikeman"), tr("Pikemen"), new Funds(210, 0, 0, 0, 0, 0, 0));
        add(5, 9, 3, 5, 21, SpeedKind.FAST, 5, 0, tr("Veteran Pikeman"), tr("Veteran Pikemen"), new Funds(260, 0, 0, 0, 0, 0, 0));
        add(7, 9, 4, 7, 26, SpeedKind.AVERAGE, 4, 0, tr("Swordsman"), tr("Swordsmen"), new Funds(260, 0, 0, 0, 0, 0, 0));
        add(7, 9, 4, 7, 31, SpeedKind.FAST, 4, 0, tr("Master Swordsman"), tr("Master Swordsmen"), new Funds(310, 0, 0, 0, 0, 0, 0));
        add(10, 9, 5, 10, 35, SpeedKind.VERYFAST, 3, 0, tr("Cavalry"), tr("Cavalries"), new Funds(340, 0, 0, 0, 0, 0, 0));
        add(10, 9, 5, 10, 45, SpeedKind.ULTRAFAST, 3, 0, tr("Champion"), tr("Champions"), new Funds(415, 0, 0, 0, 0, 0, 0));
        add(11, 12, 10, 20, 60, SpeedKind.FAST, 2, 0, tr("Paladin"), tr("Paladins"), new Funds(675, 0, 0, 0, 0, 0, 0));
        add(11, 12, 10, 20, 75, SpeedKind.VERYFAST, 2, 0, tr("Crusader"), tr("Crusaders"), new Funds(1000, 0, 0, 0, 0, 0, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname            cost
        add(3, 1, 1, 2, 3, SpeedKind.AVERAGE, 12, 0, tr("Goblin"), tr("Goblins"), new Funds(40, 0, 0, 0, 0, 0, 0));
        add(3, 4, 2, 3, 11, SpeedKind.VERYSLOW, 8, 8, tr("Orc"), tr("Orcs"), new Funds(140, 0, 0, 0, 0, 0, 0));
        add(3, 4, 3, 4, 15, SpeedKind.SLOW, 8, 16, tr("Orc Chief"), tr("Orc Chiefs"), new Funds(175, 0, 0, 0, 0, 0, 0));
        add(6, 3, 3, 5, 20, SpeedKind.VERYFAST, 5, 0, tr("Wolf"), tr("Wolves"), new Funds(210, 0, 0, 0, 0, 0, 0));
        add(9, 5, 4, 6, 40, SpeedKind.VERYSLOW, 4, 0, tr("Ogre"), tr("Ogres"), new Funds(300, 0, 0, 0, 0, 0, 0));
        add(9, 5, 5, 7, 55, SpeedKind.AVERAGE, 4, 0, tr("Ogre Lord"), tr("Ogre Lords"), new Funds(475, 0, 0, 0, 0, 0, 0));
        add(10, 5, 5, 7, 40, SpeedKind.AVERAGE, 3, 8, tr("Troll"), tr("Trolls"), new Funds(575, 0, 0, 0, 0, 0, 0));
        add(10, 5, 7, 9, 40, SpeedKind.FAST, 3, 16, tr("War Troll"), tr("War Trolls"), new Funds(650, 0, 0, 0, 0, 0, 0));
        add(12, 9, 12, 24, 85, SpeedKind.FAST, 2, 0, tr("Cyclops"), tr("Cyclopes"), new Funds(800, 0, 0, 0, 0, 1, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname            cost
        add(4, 2, 1, 2, 2, SpeedKind.AVERAGE, 10, 0, tr("Sprite"), tr("Sprites"), new Funds(50, 0, 0, 0, 0, 0, 0));
        add(6, 5, 2, 4, 20, SpeedKind.VERYSLOW, 6, 0, tr("Dwarf"), tr("Dwarves"), new Funds(190, 0, 0, 0, 0, 0, 0));
        add(6, 6, 2, 4, 20, SpeedKind.AVERAGE, 6, 0, tr("Battle Dwarf"), tr("Battle Dwarves"), new Funds(235, 0, 0, 0, 0, 0, 0));
        add(5, 3, 2, 3, 16, SpeedKind.AVERAGE, 4, 24, tr("Elf"), tr("Elves"), new Funds(250, 0, 0, 0, 0, 0, 0));
        add(7, 5, 2, 3, 16, SpeedKind.VERYFAST, 4, 24, tr("Grand Elf"), tr("Grand Elves"), new Funds(300, 0, 0, 0, 0, 0, 0));
        add(7, 5, 5, 8, 25, SpeedKind.FAST, 3, 8, tr("Druid"), tr("Druids"), new Funds(350, 0, 0, 0, 0, 0, 0));
        add(7, 7, 5, 8, 26, SpeedKind.VERYFAST, 3, 16, tr("Greater Druid"), tr("Greater Druids"), new Funds(400, 0, 0, 0, 0, 0, 0));
        add(10, 9, 7, 14, 45, SpeedKind.FAST, 2, 0, tr("Unicorn"), tr("Unicorns"), new Funds(515, 0, 0, 0, 0, 0, 0));
        add(12, 10, 20, 40, 100, SpeedKind.ULTRAFAST, 1, 0, tr("Phoenix"), tr("Phoenixes"), new Funds(1500, 0, 1, 0, 0, 0, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname            cost
        add(3, 1, 1, 2, 5, SpeedKind.AVERAGE, 8, 8, tr("Centaur"), tr("Centaurs"), new Funds(60, 0, 0, 0, 0, 0, 0));
        add(4, 6, 2, 3, 15, SpeedKind.VERYFAST, 6, 0, tr("Gargoyle"), tr("Gargoyles"), new Funds(200, 0, 0, 0, 0, 0, 0));
        add(6, 6, 3, 5, 25, SpeedKind.AVERAGE, 4, 0, tr("Griffin"), tr("Griffins"), new Funds(320, 0, 0, 0, 0, 0, 0));
        add(9, 8, 5, 10, 35, SpeedKind.AVERAGE, 3, 0, tr("Minotaur"), tr("Minotaurs"), new Funds(400, 0, 0, 0, 0, 0, 0));
        add(9, 8, 5, 10, 45, SpeedKind.VERYFAST, 3, 0, tr("Minotaur King"), tr("Minotaur Kings"), new Funds(500, 0, 0, 0, 0, 0, 0));
        add(8, 9, 6, 12, 75, SpeedKind.VERYSLOW, 2, 0, tr("Hydra"), tr("Hydras"), new Funds(775, 0, 0, 0, 0, 0, 0));
        add(12, 12, 25, 50, 200, SpeedKind.AVERAGE, 0, 0, tr("Green Dragon"), tr("Green Dragons"), new Funds(3000, 0, 0, 0, 1, 0, 0));
        add(13, 13, 25, 50, 240, SpeedKind.FAST, 0, 0, tr("Red Dragon"), tr("Red Dragons"), new Funds(3500, 0, 0, 0, 1, 0, 0));
        add(14, 14, 25, 50, 280, SpeedKind.VERYFAST, 0, 0, tr("Black Dragon"), tr("Black Dragons"), new Funds(4000, 0, 0, 0, 2, 0, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname            cost
        add(2, 1, 1, 3, 3, SpeedKind.SLOW, 8, 12, tr("Halfling"), tr("Halflings"), new Funds(55, 0, 0, 0, 0, 0, 0));
        add(5, 4, 2, 4, 15, SpeedKind.VERYFAST, 6, 0, tr("Boar"), tr("Boars"), new Funds(155, 0, 0, 0, 0, 0, 0));
        add(5, 10, 4, 5, 30, SpeedKind.VERYSLOW, 4, 0, tr("Iron Golem"), tr("Iron Golems"), new Funds(300, 0, 0, 0, 0, 0, 0));
        add(7, 10, 4, 5, 35, SpeedKind.SLOW, 4, 0, tr("Steel Golem"), tr("Steel Golems"), new Funds(350, 0, 0, 0, 0, 0, 0));
        add(7, 8, 4, 8, 40, SpeedKind.AVERAGE, 3, 0, tr("Roc"), tr("Rocs"), new Funds(400, 0, 0, 0, 0, 0, 0));
        add(11, 7, 7, 9, 30, SpeedKind.FAST, 2, 12, tr("Mage"), tr("Magi"), new Funds(600, 0, 0, 0, 0, 0, 0));
        add(12, 8, 7, 9, 35, SpeedKind.VERYFAST, 2, 24, tr("Archmage"), tr("Archmagi"), new Funds(700, 0, 0, 0, 0, 0, 0));
        add(13, 10, 20, 30, 155, SpeedKind.AVERAGE, 0, 0, tr("Giant"), tr("Giants"), new Funds(2000, 0, 0, 0, 0, 0, 1));
        add(15, 15, 20, 30, 280, SpeedKind.VERYFAST, 0, 24, tr("Titan"), tr("Titans"), new Funds(4500, 0, 0, 0, 0, 0, 2));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname            cost
        add(4, 3, 2, 3, 4, SpeedKind.AVERAGE, 8, 0, tr("Skeleton"), tr("Skeletons"), new Funds(70, 0, 0, 0, 0, 0, 0));
        add(5, 3, 2, 3, 15, SpeedKind.SLOW, 6, 0, tr("Zombie"), tr("Zombies"), new Funds(140, 0, 0, 0, 0, 0, 0));
        add(5, 3, 2, 3, 25, SpeedKind.AVERAGE, 6, 0, tr("Mutant Zombie"), tr("Mutant Zombies"), new Funds(190, 0, 0, 0, 0, 0, 0));
        add(6, 7, 3, 4, 25, SpeedKind.AVERAGE, 4, 0, tr("Mummy"), tr("Mummies"), new Funds(250, 0, 0, 0, 0, 0, 0));
        add(6, 7, 3, 4, 30, SpeedKind.FAST, 4, 0, tr("Royal Mummy"), tr("Royal Mummies"), new Funds(300, 0, 0, 0, 0, 0, 0));
        add(8, 6, 5, 7, 30, SpeedKind.AVERAGE, 3, 0, tr("Vampire"), tr("Vampires"), new Funds(500, 0, 0, 0, 0, 0, 0));
        add(8, 6, 5, 7, 40, SpeedKind.FAST, 3, 0, tr("Vampire Lord"), tr("Vampire Lords"), new Funds(650, 0, 0, 0, 0, 0, 0));
        add(7, 11, 8, 10, 30, SpeedKind.FAST, 2, 12, tr("Lich"), tr("Liches"), new Funds(650, 0, 0, 0, 0, 0, 0));
        add(7, 13, 8, 10, 35, SpeedKind.VERYFAST, 2, 24, tr("Power Lich"), tr("Power Liches"), new Funds(750, 0, 0, 0, 0, 0, 0));
        add(11, 9, 25, 45, 140, SpeedKind.AVERAGE, 1, 0, tr("Bone Dragon"), tr("Bone Dragons"), new Funds(1600, 0, 0, 0, 0, 0, 0));

        // atck dfnc  min  max   hp             speed grwn  shots  name                  multiname                cost
        add(6, 1, 1, 2, 4, SpeedKind.FAST, 4, 0, tr("Rogue"), tr("Rogues"), new Funds(50, 0, 0, 0, 0, 0, 0));
        add(7, 6, 2, 5, 20, SpeedKind.VERYFAST, 4, 0, tr("Nomad"), tr("Nomads"), new Funds(200, 0, 0, 0, 0, 0, 0));
        add(8, 7, 4, 6, 20, SpeedKind.FAST, 4, 0, tr("Ghost"), tr("Ghosts"), new Funds(1000, 0, 0, 0, 0, 0, 0));
        add(10, 9, 20, 30, 50, SpeedKind.VERYFAST, 4, 0, tr("Genie"), tr("Genies"), new Funds(650, 0, 0, 0, 0, 0, 1));
        add(8, 9, 6, 10, 35, SpeedKind.AVERAGE, 4, 0, tr("Medusa"), tr("Medusas"), new Funds(500, 0, 0, 0, 0, 0, 0));
        add(8, 8, 4, 5, 50, SpeedKind.SLOW, 4, 0, tr("Earth Elemental"), tr("Earth Elementals"), new Funds(500, 0, 0, 0, 0, 0, 0));
        add(7, 7, 2, 8, 35, SpeedKind.VERYFAST, 4, 0, tr("Air Elemental"), tr("Air Elementals"), new Funds(500, 0, 0, 0, 0, 0, 0));
        add(8, 6, 4, 6, 40, SpeedKind.FAST, 4, 0, tr("Fire Elemental"), tr("Fire Elementals"), new Funds(500, 0, 0, 0, 0, 0, 0));
        add(6, 8, 3, 7, 45, SpeedKind.AVERAGE, 4, 0, tr("Water Elemental"), tr("Water Elementals"), new Funds(500, 0, 0, 0, 0, 0, 0));

        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Random Monster", "Random Monsters", new Funds(0, 0, 0, 0, 0, 0, 0));
        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Random Monster 1", "Random Monsters 3", new Funds(0, 0, 0, 0, 0, 0, 0));
        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Random Monster 2", "Random Monsters 2", new Funds(0, 0, 0, 0, 0, 0, 0));
        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Random Monster 3", "Random Monsters 3", new Funds(0, 0, 0, 0, 0, 0, 0));
        add(0, 0, 0, 0, 0, SpeedKind.VERYSLOW, 0, 0, "Random Monster 4", "Random Monsters 4", new Funds(0, 0, 0, 0, 0, 0, 0));
    }

    static void add(int attack, int defense, int damageMin, int damageMax, int hp, int speed, int grown, int shots, String name, String multiname, Funds cost) {
        var stat = new monstats_t(attack, defense, damageMin, damageMax, hp, speed, grown, shots, name, multiname, cost);
        monsters.add(stat);
    }

    public static monstats_t get(int index) {
        return monsters.get(index);
    }

}
