package hellofx.fheroes2.monster;

import hellofx.fheroes2.common.Rand;
import hellofx.fheroes2.game.DifficultyEnum;
import hellofx.fheroes2.game.GameStatic;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.resource.Funds;
import hellofx.fheroes2.spell.Spell;
import hellofx.fheroes2.spell.SpellKind;
import hellofx.fheroes2.system.Settings;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import static hellofx.fheroes2.castle.building_t.*;
import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;
import static hellofx.fheroes2.serialize.ByteVectorReader.toUShort;

public class Monster {
    public int id;

    public Monster(int monsterKind) {
        //TODO
        id = monsterKind;
    }

    public Monster(int race, int dw) {
        id = FromDwelling(race, dw).id;
    }

    public Monster(Spell spell) {
        var sp = spell.id;
        switch (sp) {
            case SpellKind.SETEGUARDIAN:
            case SpellKind.SUMMONEELEMENT:
                id = MonsterKind.EARTH_ELEMENT;
                break;

            case SpellKind.SETAGUARDIAN:
            case SpellKind.SUMMONAELEMENT:
                id = MonsterKind.AIR_ELEMENT;
                break;

            case SpellKind.SETFGUARDIAN:
            case SpellKind.SUMMONFELEMENT:
                id = MonsterKind.FIRE_ELEMENT;
                break;

            case SpellKind.SETWGUARDIAN:
            case SpellKind.SUMMONWELEMENT:
                id = MonsterKind.WATER_ELEMENT;
                break;

            case SpellKind.HAUNT:
                id = MonsterKind.GHOST;
                break;
            default:
                break;
        }
    }

    public static int Rand(int level) {

        switch (level) {
            default:
                return Rand.Get(MonsterKind.PEASANT, MonsterKind.WATER_ELEMENT);

            case MonsterLevel.LEVEL1:
            case MonsterLevel.LEVEL2:
            case MonsterLevel.LEVEL3:
            case MonsterLevel.LEVEL4:
                break;
        }

        var monsters = new IntArrayList(30);

        for (var ii = MonsterKind.PEASANT; ii <= MonsterKind.WATER_ELEMENT; ++ii) {
            Monster mons = new Monster(ii);
            if (mons.GetLevel() == level) {
                monsters.add(ii);
            }
        }

        return monsters.size() != 0 ? Rand.Get(monsters) : MonsterKind.UNKNOWN;
    }

    private int GetLevel() {
        return switch (id) {
            case MonsterKind.PEASANT, MonsterKind.ARCHER, MonsterKind.GOBLIN, MonsterKind.ORC, MonsterKind.SPRITE, MonsterKind.CENTAUR, MonsterKind.HALFLING, MonsterKind.SKELETON, MonsterKind.ZOMBIE, MonsterKind.ROGUE, MonsterKind.MONSTER_RND1 -> MonsterLevel.LEVEL1;
            case MonsterKind.RANGER, MonsterKind.PIKEMAN, MonsterKind.VETERAN_PIKEMAN, MonsterKind.ORC_CHIEF, MonsterKind.WOLF, MonsterKind.DWARF, MonsterKind.BATTLE_DWARF, MonsterKind.ELF, MonsterKind.GRAND_ELF, MonsterKind.GARGOYLE, MonsterKind.BOAR, MonsterKind.IRON_GOLEM, MonsterKind.MUTANT_ZOMBIE, MonsterKind.MUMMY, MonsterKind.NOMAD, MonsterKind.MONSTER_RND2 -> MonsterLevel.LEVEL2;
            case MonsterKind.SWORDSMAN, MonsterKind.MASTER_SWORDSMAN, MonsterKind.CAVALRY, MonsterKind.CHAMPION, MonsterKind.OGRE, MonsterKind.OGRE_LORD, MonsterKind.TROLL, MonsterKind.WAR_TROLL, MonsterKind.DRUID, MonsterKind.GREATER_DRUID, MonsterKind.GRIFFIN, MonsterKind.MINOTAUR, MonsterKind.MINOTAUR_KING, MonsterKind.STEEL_GOLEM, MonsterKind.ROC, MonsterKind.MAGE, MonsterKind.ARCHMAGE, MonsterKind.ROYAL_MUMMY, MonsterKind.VAMPIRE, MonsterKind.VAMPIRE_LORD, MonsterKind.LICH, MonsterKind.GHOST, MonsterKind.MEDUSA, MonsterKind.EARTH_ELEMENT, MonsterKind.AIR_ELEMENT, MonsterKind.FIRE_ELEMENT, MonsterKind.WATER_ELEMENT, MonsterKind.MONSTER_RND3 -> MonsterLevel.LEVEL3;
            case MonsterKind.PALADIN, MonsterKind.CRUSADER, MonsterKind.CYCLOPS, MonsterKind.UNICORN, MonsterKind.PHOENIX, MonsterKind.HYDRA, MonsterKind.GREEN_DRAGON, MonsterKind.RED_DRAGON, MonsterKind.BLACK_DRAGON, MonsterKind.GIANT, MonsterKind.TITAN, MonsterKind.POWER_LICH, MonsterKind.BONE_DRAGON, MonsterKind.GENIE, MonsterKind.MONSTER_RND4 -> MonsterLevel.LEVEL4;
            case MonsterKind.MONSTER_RND -> switch (Rand.Get(0, 3)) {
                default -> MonsterLevel.LEVEL1;
                case 1 -> MonsterLevel.LEVEL2;
                case 2 -> MonsterLevel.LEVEL3;
                case 3 -> MonsterLevel.LEVEL4;
            };
            default -> MonsterLevel.LEVEL0;
        };
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

    public int GetSpriteIndex() {
        return MonsterKind.UNKNOWN < id ? id - 1 : 0;
    }

    public int GetRNDSize(boolean skip_factor) {
        int hps = (GetGrown() != 0 ? GetGrown() : 1) * GetHitPoints();
        var res = Rand.Get(hps, hps + hps / 2);
        if (!skip_factor) {
            var factor = 100;

            switch (Settings.Get().GameDifficulty()) {
                case DifficultyEnum.EASY:
                    factor = 80;
                    break;
                case DifficultyEnum.NORMAL:
                    factor = 100;
                    break;
                case DifficultyEnum.HARD:
                    factor = 130;
                    break;
                case DifficultyEnum.EXPERT:
                    factor = 160;
                    break;
                case DifficultyEnum.IMPOSSIBLE:
                    factor = 190;
                    break;
                default:
                    break;
            }

            res = res * factor / 100;
            // force minimal
            if (res == 0) res = 1;
        }

        return IsValid() ? GetCountFromHitPoints(id, res) : 0;
    }

    int GetCountFromHitPoints(int monsterId, int hp) {
        if (hp == 0)
            return 0;
        var hp1 = GetHitPoints(monsterId);
        var count = hp / hp1;
        return count * hp1 < hp ? count + 1 : count;
    }

    private int GetHitPoints() {

        return GetHitPoints(this.id);
    }

    private int GetHitPoints(int id) {

        return toUShort(MonsterStats.get(id).hp);
    }

    public boolean isElemental() {
        return switch (id) {
            case MonsterKind.EARTH_ELEMENT, MonsterKind.AIR_ELEMENT, MonsterKind.FIRE_ELEMENT, MonsterKind.WATER_ELEMENT -> true;
            default -> false;
        };

    }

    public Funds GetCost() {
        return MonsterStats.get(id).cost;
    }

    public Funds GetUpgradeCost() {

        Monster upgr = GetUpgrade();
        var pay = id != upgr.id ? upgr.GetCost().substract(GetCost()) : GetCost();

        pay.wood = (short) (pay.wood * GetUpgradeRatio());
        pay.mercury = (short) (pay.mercury * GetUpgradeRatio());
        pay.ore = (short) (pay.ore * GetUpgradeRatio());
        pay.sulfur = (short) (pay.sulfur * GetUpgradeRatio());
        pay.crystal = (short) (pay.crystal * GetUpgradeRatio());
        pay.gems = (short) (pay.gems * GetUpgradeRatio());
        pay.gold = (int) (pay.gold * GetUpgradeRatio());

        return pay;
    }

    private float GetUpgradeRatio() {
        return GameStatic.GetMonsterUpgradeRatio();
    }

    public Monster GetUpgrade() {
        return MonsterStats.GetUpgrade(id);
    }

    public int GetRace() {
        if (MonsterKind.UNKNOWN == id) return RaceKind.NONE;
        if (MonsterKind.GOBLIN > id) return RaceKind.KNGT;
        if (MonsterKind.SPRITE > id) return RaceKind.BARB;
        if (MonsterKind.CENTAUR > id) return RaceKind.SORC;
        if (MonsterKind.HALFLING > id) return RaceKind.WRLK;
        if (MonsterKind.SKELETON > id) return RaceKind.WZRD;
        if (MonsterKind.ROGUE > id) return RaceKind.NECR;

        return RaceKind.NONE;
    }

    public int GetDwelling() {
        return MonsterStats.GetDwelling(id);
    }

    public void Upgrade() {
        id = GetUpgrade().id;
    }
}
