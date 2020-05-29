package hellofx.fheroes2.monster;

import hellofx.fheroes2.kingdom.RaceKind;

import static hellofx.fheroes2.castle.BuildingKind.*;
import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class Monster {
    public int id;

    public Monster(int monsterKind) {
        //TODO
        id = monsterKind;
    }

    public Monster(int race, int dw) {
        id = FromDwelling(race, dw).id;
    }

    private Monster FromDwelling(int race, int dwelling) {
        switch (dwelling) {
            case DWELLING_MONSTER1:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.PEASANT);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.GOBLIN);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.SPRITE);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.CENTAUR);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.HALFLING);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.SKELETON);
                    default:
                        break;
                }
                break;

            case DWELLING_MONSTER2:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.ARCHER);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.ORC);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.DWARF);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.GARGOYLE);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.BOAR);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.ZOMBIE);
                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE2:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.RANGER);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.ORC_CHIEF);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.BATTLE_DWARF);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.GARGOYLE);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.BOAR);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.MUTANT_ZOMBIE);
                    default:
                        break;
                }
                break;

            case DWELLING_MONSTER3:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.PIKEMAN);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.WOLF);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.ELF);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.GRIFFIN);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.IRON_GOLEM);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.MUMMY);
                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE3:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.VETERAN_PIKEMAN);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.WOLF);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.GRAND_ELF);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.GRIFFIN);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.STEEL_GOLEM);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.ROYAL_MUMMY);
                    default:
                        break;
                }
                break;

            case DWELLING_MONSTER4:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.SWORDSMAN);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.OGRE);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.DRUID);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.MINOTAUR);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.ROC);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.VAMPIRE);
                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE4:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.MASTER_SWORDSMAN);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.OGRE_LORD);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.GREATER_DRUID);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.MINOTAUR_KING);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.ROC);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.VAMPIRE_LORD);
                    default:
                        break;
                }
                break;

            case DWELLING_MONSTER5:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.CAVALRY);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.TROLL);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.UNICORN);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.HYDRA);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.MAGE);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.LICH);
                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE5:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.CHAMPION);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.WAR_TROLL);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.UNICORN);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.HYDRA);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.ARCHMAGE);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.POWER_LICH);
                    default:
                        break;
                }
                break;

            case DWELLING_MONSTER6:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.PALADIN);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.CYCLOPS);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.PHOENIX);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.GREEN_DRAGON);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.GIANT);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.BONE_DRAGON);
                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE6:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.CRUSADER);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.CYCLOPS);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.PHOENIX);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.RED_DRAGON);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.TITAN);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.BONE_DRAGON);
                    default:
                        break;
                }
                break;

            case DWELLING_UPGRADE7:
                switch (race) {
                    case RaceKind.KNGT:
                        return new Monster(MonsterKind.CRUSADER);
                    case RaceKind.BARB:
                        return new Monster(MonsterKind.CYCLOPS);
                    case RaceKind.SORC:
                        return new Monster(MonsterKind.PHOENIX);
                    case RaceKind.WRLK:
                        return new Monster(MonsterKind.BLACK_DRAGON);
                    case RaceKind.WZRD:
                        return new Monster(MonsterKind.TITAN);
                    case RaceKind.NECR:
                        return new Monster(MonsterKind.BONE_DRAGON);
                    default:
                        break;
                }
                break;

            default:
                break;
        }

        return new Monster(MonsterKind.UNKNOWN);
    }


    public int GetDamageMin() {
        return toByte(MonsterStats.get(id).damageMin);
    }

    public int GetDamageMax() {
        return toByte(MonsterStats.get(id).damageMax);
    }

    public int GetShots() {
        return toByte(MonsterStats.get(id).shots);
    }

    public int GetSpeed() {
        return toByte(MonsterStats.get(id).speed);
    }

    public int GetGrown() {
        return toByte(MonsterStats.get(id).grown);
    }

    public boolean IsValid() {
        return id != MonsterKind.UNKNOWN;
    }
}
