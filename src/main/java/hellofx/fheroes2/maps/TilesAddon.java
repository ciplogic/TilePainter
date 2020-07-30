package hellofx.fheroes2.maps;

import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.common.H2IntPair;
import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.objects.*;

import static hellofx.fheroes2.kingdom.WorldDump.writeField;
import static hellofx.fheroes2.kingdom.WorldDump.writeFieldBare;

public class TilesAddon {

    public int uniq;
    public short level;
    public short object;
    public short index;
    public short tmp;

    public TilesAddon(int lv, int gid, int obj, int ii) {
        uniq = gid;
        level = (short) lv;
        object = (short) obj;
        index = (short) ii;
        if (index < 0) {
            System.out.println("Here2");
        }
        tmp = 0;
    }

    public static H2IntPair ColorRaceFromHeroSprite(TilesAddon ta) {
        H2IntPair res = new H2IntPair();

        if (7 > ta.getIndex())
            res.first = H2Color.BLUE;
        else if (14 > ta.getIndex())
            res.first = H2Color.GREEN;
        else if (21 > ta.getIndex())
            res.first = H2Color.RED;
        else if (28 > ta.getIndex())
            res.first = H2Color.YELLOW;
        else if (35 > ta.getIndex())
            res.first = H2Color.ORANGE;
        else
            res.first = H2Color.PURPLE;

        res.second = RaceKind.NONE;
        switch (ta.index % 7) {
            case 0 -> res.second = RaceKind.KNGT;
            case 1 -> res.second = RaceKind.BARB;
            case 2 -> res.second = RaceKind.SORC;
            case 3 -> res.second = RaceKind.WRLK;
            case 4 -> res.second = RaceKind.WZRD;
            case 5 -> res.second = RaceKind.NECR;
            case 6 -> res.second = RaceKind.RAND;
        }
        return res;
    }

    public static int ColorFromBarrierSprite(TilesAddon ta) {
        // 60, 66, 72, 78, 84, 90, 96, 102
        return IcnKind.X_LOC3 == Mp2Kind.GetICNObject(ta.object) &&
                60 <= ta.getIndex() && 102 >= ta.getIndex()
                ? (ta.getIndex() - 60) / 6 + 1
                : 0;
    }

    public static boolean isMounts(TilesAddon ta) {
        switch (Mp2.GetICNObject(ta.object)) {
            case IcnKind.MTNSNOW:
            case IcnKind.MTNSWMP:
            case IcnKind.MTNLAVA:
            case IcnKind.MTNDSRT:
            case IcnKind.MTNMULT:
            case IcnKind.MTNGRAS:
                return !ObjMnts1.isShadow(ta.getIndex());

            case IcnKind.MTNCRCK:
            case IcnKind.MTNDIRT:
                return !ObjMnts2.isShadow(ta.getIndex());

            default:
                break;
        }

        return false;
    }

    static boolean isWaterResource(TilesAddon ta) {
        return IcnKind.OBJNWATR == Mp2Kind.GetICNObject(ta.object) &&
                (0 == ta.index || // buttle
                        19 == ta.index || // chest
                        45 == ta.index || // flotsam
                        111 == ta.getIndex()) // surviror
                ;
    }

    public TilesAddon() {
    }

    public static int ColorFromTravellerTentSprite(TilesAddon ta) {
        // 110, 114, 118, 122, 126, 130, 134, 138
        return IcnKind.X_LOC3 == Mp2.GetICNObject(ta.object) &&
                110 <= ta.index && 138 >= ta.index
                ? (ta.index - 110) / 4 + 1
                : 0;
    }

    static boolean isWhirlPool(TilesAddon ta) {
        return IcnKind.OBJNWATR == Mp2Kind.GetICNObject(ta.object) &&
                ta.getIndex() >= 202 && ta.getIndex() <= 225;
    }

    public static boolean ForceLevel1(TilesAddon ta) {
        // broken ship: left roc
        return IcnKind.OBJNWAT2 == Mp2.GetICNObject(ta.object) && ta.index == 11;
    }


    public static boolean isX_LOC123(TilesAddon ta) {
        return IcnKind.X_LOC1 == Mp2Kind.GetICNObject(ta.object) ||
                IcnKind.X_LOC2 == Mp2Kind.GetICNObject(ta.object) ||
                IcnKind.X_LOC3 == Mp2Kind.GetICNObject(ta.object);
    }

    public static boolean ForceLevel2(TilesAddon ta) {
        return false;
    }

    public static int GetLoyaltyObject(TilesAddon addon) {
        switch (Mp2.GetIcnKindObject(addon.object)) {
            case IcnKind.X_LOC1:
                if (addon.index == 3) return Mp2Kind.OBJ_ALCHEMYTOWER;
                if (addon.index < 3) return Mp2Kind.OBJN_ALCHEMYTOWER;
                if (70 == addon.index) return Mp2Kind.OBJ_ARENA;
                if (3 < addon.index && addon.index < 72) return Mp2Kind.OBJN_ARENA;
                if (77 == addon.index) return Mp2Kind.OBJ_BARROWMOUNDS;
                if (71 < addon.index && addon.index < 78) return Mp2Kind.OBJN_BARROWMOUNDS;
                if (94 == addon.index) return Mp2Kind.OBJ_EARTHALTAR;
                if (77 < addon.index && addon.index < 112) return Mp2Kind.OBJN_EARTHALTAR;
                if (118 == addon.index) return Mp2Kind.OBJ_AIRALTAR;
                if (111 < addon.index && addon.index < 120) return Mp2Kind.OBJN_AIRALTAR;
                if (127 == addon.index) return Mp2Kind.OBJ_FIREALTAR;
                if (119 < addon.index && addon.index < 129) return Mp2Kind.OBJN_FIREALTAR;
                if (135 == addon.index) return Mp2Kind.OBJ_WATERALTAR;
                if (128 < addon.index && addon.index < 137) return Mp2Kind.OBJN_WATERALTAR;
                break;

            case IcnKind.X_LOC2:
                if (addon.index == 4) return Mp2Kind.OBJ_STABLES;
                if (addon.index < 4) return Mp2Kind.OBJN_STABLES;
                if (addon.index == 9) return Mp2Kind.OBJ_JAIL;
                if (4 < addon.index && addon.index < 10) return Mp2Kind.OBJN_JAIL;
                if (addon.index == 37) return Mp2Kind.OBJ_MERMAID;
                if (9 < addon.index && addon.index < 47) return Mp2Kind.OBJN_MERMAID;
                if (addon.index == 101) return Mp2Kind.OBJ_SIRENS;
                if (46 < addon.index && addon.index < 111) return Mp2Kind.OBJN_SIRENS;
                if (110 < addon.index && addon.index < 136) return Mp2Kind.OBJ_REEFS;
                break;

            case IcnKind.X_LOC3:
                if (addon.index == 30) return Mp2Kind.OBJ_HUTMAGI;
                if (addon.index < 32) return Mp2Kind.OBJN_HUTMAGI;
                if (addon.index == 50) return Mp2Kind.OBJ_EYEMAGI;
                if (31 < addon.index && addon.index < 59) return Mp2Kind.OBJN_EYEMAGI;
                // fix
                break;

            default:
                break;
        }

        return Mp2Kind.OBJ_ZERO;
    }

    static boolean isResource(TilesAddon ta) {
        // OBJNRSRC
        return IcnKind.OBJNRSRC == Mp2Kind.GetICNObject(ta.object) && ta.getIndex() % 2 != 0 ||
                // TREASURE
                IcnKind.TREASURE == Mp2Kind.GetICNObject(ta.object);
    }

    public static boolean isRocs(TilesAddon ta) {
        switch (Mp2.GetICNObject(ta.object)) {
            // roc objects
            case IcnKind.OBJNSNOW:
                if ((ta.index > 21 && ta.index < 25) || (ta.index > 25 && ta.index < 29) ||
                        ta.index == 30 || ta.index == 32 || ta.index == 34 || ta.index == 35 ||
                        (ta.index > 36 && ta.index < 40))
                    return true;
                break;

            case IcnKind.OBJNSWMP:
                if (ta.index == 201 || ta.index == 205 ||
                        (ta.index > 207 && ta.index < 211))
                    return true;
                break;

            case IcnKind.OBJNGRAS:
                if ((ta.index > 32 && ta.index < 36) || ta.index == 37 || ta.index == 38 ||
                        ta.index == 40 || ta.index == 41 || ta.index == 43 || ta.index == 45)
                    return true;
                break;

            case IcnKind.OBJNDIRT:
                if (ta.index == 92 || ta.index == 93 || ta.index == 95 || ta.index == 98 ||
                        ta.index == 99 || ta.index == 101 || ta.index == 102 || ta.index == 104 || ta.index == 105)
                    return true;
                break;

            case IcnKind.OBJNCRCK:
                if (ta.index == 10 || ta.index == 11 ||
                        ta.index == 18 || ta.index == 19 || ta.index == 21 || ta.index == 22 ||
                        (ta.index > 23 && ta.index < 28) || (ta.index > 28 && ta.index < 33) ||
                        ta.index == 34 || ta.index == 35 || ta.index == 37 || ta.index == 38 ||
                        (ta.index > 39 && ta.index < 45) || ta.index == 46 || ta.index == 47 ||
                        ta.index == 49 || ta.index == 50 || ta.index == 52 || ta.index == 53 || ta.index == 55)
                    return true;
                break;

            case IcnKind.OBJNWAT2:
                if (ta.index == 0 || ta.index == 2)
                    return true;
                break;

            case IcnKind.OBJNWATR:
                if (ta.index == 182 || ta.index == 183 ||
                        (ta.index > 184 && ta.index < 188))
                    return true;
                break;

            default:
                break;
        }

        return false;
    }

    static boolean isRandomArtifact(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2Kind.GetICNObject(ta.object) && 0xA3 == ta.getIndex();
    }

    public static boolean isForests(TilesAddon ta) {
        switch (Mp2.GetICNObject(ta.object)) {
            case IcnKind.TREJNGL:
            case IcnKind.TREEVIL:
            case IcnKind.TRESNOW:
            case IcnKind.TREFIR:
            case IcnKind.TREFALL:
            case IcnKind.TREDECI:
                return !ObjTree.isShadow(ta.index);

            default:
                break;
        }

        return false;
    }

    static boolean isCactus(TilesAddon ta) {
        switch (Mp2.GetICNObject(ta.object)) {
            case IcnKind.OBJNDSRT:
                if (ta.index == 24 || ta.index == 26 || ta.index == 28 ||
                        (ta.index > 29 && ta.index < 33) ||
                        ta.index == 34 || ta.index == 36 || ta.index == 37 || ta.index == 39 ||
                        ta.index == 40 || ta.index == 42 || ta.index == 43 ||
                        ta.index == 45 || ta.index == 48 || ta.index == 49 ||
                        ta.index == 51 || ta.index == 53)
                    return true;
                break;

            case IcnKind.OBJNCRCK:
                if (ta.index == 14 || ta.index == 16)
                    return true;
                break;

            default:
                break;
        }

        return false;
    }

    static boolean isTrees(TilesAddon ta) {
        switch (Mp2.GetICNObject(ta.object)) {
            // tree objects
            case IcnKind.OBJNDSRT:
                if (ta.index == 3 || ta.index == 4 || ta.index == 6 ||
                        ta.index == 7 || ta.index == 9 || ta.index == 10 ||
                        ta.index == 12 || ta.index == 74 || ta.index == 76)
                    return true;
                break;

            case IcnKind.OBJNDIRT:
                if (ta.index == 115 || ta.index == 118 || ta.index == 120 ||
                        ta.index == 123 || ta.index == 125 || ta.index == 127)
                    return true;
                break;

            case IcnKind.OBJNGRAS:
                if (ta.index == 80 || (ta.index > 82 && ta.index < 86) ||
                        ta.index == 87 || (ta.index > 88 && ta.index < 92))
                    return true;
                break;

            default:
                break;
        }

        return false;
    }

    public static int GetPassable(TilesAddon ta) {
        var icn = Mp2.GetICNObject(ta.object);

        switch (icn) {
            case IcnKind.MTNSNOW:
            case IcnKind.MTNSWMP:
            case IcnKind.MTNLAVA:
            case IcnKind.MTNDSRT:
            case IcnKind.MTNMULT:
            case IcnKind.MTNGRAS:
                return ObjMnts1.GetPassable(icn, ta.getIndex());

            case IcnKind.MTNCRCK:
            case IcnKind.MTNDIRT:
                return ObjMnts2.GetPassable(icn, ta.getIndex());

            case IcnKind.TREJNGL:
            case IcnKind.TREEVIL:
            case IcnKind.TRESNOW:
            case IcnKind.TREFIR:
            case IcnKind.TREFALL:
            case IcnKind.TREDECI:
                return ObjTree.GetPassable(ta.getIndex());
            case IcnKind.OBJNSNOW:
                return ObjSnow.GetPassable(ta.getIndex());
            case IcnKind.OBJNSWMP:
                return ObjSwmp.GetPassable(ta.getIndex());
            case IcnKind.OBJNGRAS:
                return ObjGras.GetPassable(ta.getIndex());
            case IcnKind.OBJNGRA2:
                return ObjGra2.GetPassable(ta.getIndex());
            case IcnKind.OBJNCRCK:
                return ObjCrck.GetPassable(ta.getIndex());
            case IcnKind.OBJNDIRT:
                return ObjDirt.GetPassable(ta.getIndex());
            case IcnKind.OBJNDSRT:
                return ObjDsrt.GetPassable(ta.getIndex());
            case IcnKind.OBJNMUL2:
                return ObjMul2.GetPassable(ta.getIndex());
            case IcnKind.OBJNMULT:
                return ObjMult.GetPassable(ta.getIndex());
            case IcnKind.OBJNLAVA:
                return ObjLava.GetPassable(ta.getIndex());
            case IcnKind.OBJNLAV3:
                return ObjLav3.GetPassable(ta.getIndex());
            case IcnKind.OBJNLAV2:
                return ObjLav2.GetPassable(ta.getIndex());
            case IcnKind.OBJNWAT2:
                return ObjWat2.GetPassable(ta.getIndex());
            case IcnKind.OBJNWATR:
                return ObjWatr.GetPassable(ta.getIndex());

            case IcnKind.OBJNTWBA:
                return ObjTwba.GetPassable(ta.getIndex());
            case IcnKind.OBJNTOWN:
                return ObjTown.GetPassable(ta.getIndex());

            case IcnKind.X_LOC1:
                return ObjXlc1.GetPassable(ta.getIndex());
            case IcnKind.X_LOC2:
                return ObjXlc2.GetPassable(ta.getIndex());
            case IcnKind.X_LOC3:
                return ObjXlc3.GetPassable(ta.getIndex());

            // MANUAL.ICN
            case IcnKind.TELEPORT1:
            case IcnKind.TELEPORT2:
            case IcnKind.TELEPORT3:
            case IcnKind.FOUNTAIN:
                return 0;

            default:
                break;
        }

        return Direction.DIRECTION_ALL;

    }

    static int UpdateStoneLightsSprite(TilesAddon ta) {
        if (IcnKind.OBJNMUL2 == Mp2.GetICNObject(ta.object))
            switch (ta.index) {
                case 116:
                    ta.object = 0x11;
                    ta.index = 0;
                    return 1;
                case 119:
                    ta.object = 0x12;
                    ta.index = 0;
                    return 2;
                case 122:
                    ta.object = 0x13;
                    ta.index = 0;
                    return 3;
                default:
                    break;
            }
        return 0;
    }

    public TilesAddon clone() {
        TilesAddon res = new TilesAddon();
        res.uniq = uniq;
        res.level = level;
        res.object = object;
        if (index < 0) {
            System.out.println("Care!");
        }
        res.index = index;
        res.tmp = tmp;
        return res;
    }

    public static boolean isShadow(TilesAddon ta) {
        //TODO
        return false;
    }

    static boolean isArtifact(TilesAddon ta) {
        // OBJNARTI (skip ultimate)
        return IcnKind.OBJNARTI == Mp2Kind.GetICNObject(ta.object) && ta.index > 0x10 && ta.index % 2 != 0;
    }

    static boolean isRandomArtifact1(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2Kind.GetICNObject(ta.object) && 0xA7 == ta.getIndex();
    }

    static boolean isRandomArtifact2(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2Kind.GetICNObject(ta.object) && 0xA9 == ta.getIndex();
    }

    static boolean isStandingStone(TilesAddon ta) {
        return IcnKind.OBJNMULT == Mp2Kind.GetICNObject(ta.object) &&
                (ta.index == 84 || ta.index == 85);
    }

    static boolean isRandomArtifact3(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2Kind.GetICNObject(ta.object) && 0xAB == ta.getIndex();
    }

    static boolean isRandomResource(TilesAddon ta) {
        // OBJNRSRC
        return IcnKind.OBJNRSRC == Mp2Kind.GetICNObject(ta.object) && 17 == ta.index;
    }

    static boolean isCampFire(TilesAddon ta) {
        // MTNDSRT
        return IcnKind.OBJNDSRT == Mp2Kind.GetICNObject(ta.object) && 61 == ta.getIndex() ||
                // OBJNMULT
                IcnKind.OBJNMULT == Mp2Kind.GetICNObject(ta.object) && 131 == ta.getIndex() ||
                // OBJNSNOW
                IcnKind.OBJNSNOW == Mp2Kind.GetICNObject(ta.object) && 4 == ta.getIndex();
    }

    static boolean isUltimateArtifact(TilesAddon ta) {
        // OBJNARTI
        return IcnKind.OBJNARTI == Mp2Kind.GetICNObject(ta.object) && 0xA4 == ta.getIndex();
    }

    static boolean isWateringHole(TilesAddon ta) {
        return IcnKind.OBJNCRCK == Mp2Kind.GetICNObject(ta.object) &&
                ta.getIndex() >= 217 && ta.getIndex() <= 220;
    }

    static boolean isRandomMonster(TilesAddon ta) {
        // MONS32
        return IcnKind.MONS32 == Mp2Kind.GetICNObject(ta.object) &&
                0x41 < ta.getIndex() && 0x47 > ta.getIndex();
    }

    static boolean isEvent(TilesAddon ta) {
        // OBJNMUL2
        return IcnKind.OBJNMUL2 == Mp2Kind.GetICNObject(ta.object) && 0xA3 == ta.getIndex();
    }

    static boolean isRandomCastle(TilesAddon ta) {
        // OBJNTWRD
        return IcnKind.OBJNTWRD == Mp2Kind.GetICNObject(ta.object) && 32 > ta.getIndex();
    }

    static boolean isStream(TilesAddon ta) {
        return IcnKind.STREAM == Mp2Kind.GetICNObject(ta.object) ||
                IcnKind.OBJNMUL2 == Mp2Kind.GetICNObject(ta.object) && ta.getIndex() < 14;
    }

    static boolean isMonster(TilesAddon ta) {
        // MONS32
        return IcnKind.MONS32 == Mp2Kind.GetICNObject(ta.object);
    }

    static boolean isArtesianSpring(TilesAddon ta) {
        return IcnKind.OBJNCRCK == Mp2Kind.GetICNObject(ta.object) &&
                (ta.index == 3 || ta.index == 4);
    }

    static boolean isSkeleton(TilesAddon ta) {
        return IcnKind.OBJNDSRT == Mp2Kind.GetICNObject(ta.object) && ta.index == 84;
    }

    static boolean isOasis(TilesAddon ta) {
        return IcnKind.OBJNDSRT == Mp2Kind.GetICNObject(ta.object) &&
                (ta.index == 108 || ta.index == 109);
    }

    static boolean isBarrier(TilesAddon ta) {
        return IcnKind.X_LOC3 == Mp2Kind.GetICNObject(ta.object) &&
                60 <= ta.getIndex() && 102 >= ta.getIndex() && 0 == ta.getIndex() % 6;
    }

    static boolean isJail(TilesAddon ta) {
        return IcnKind.X_LOC2 == Mp2Kind.GetICNObject(ta.object) && 0x09 == ta.index;
    }

    static boolean isRoad(TilesAddon ta) {

        return IcnKind.ROAD == Mp2Kind.GetICNObject(ta.object);
    }

    static boolean isMine(TilesAddon ta) {
        // EXTRAOVR
        return IcnKind.EXTRAOVR == Mp2Kind.GetICNObject(ta.object);
    }

    static boolean isBoat(TilesAddon ta) {
        // OBJNWAT2
        return IcnKind.OBJNWAT2 == Mp2Kind.GetICNObject(ta.object) && 0x17 == ta.index;
    }

    static boolean isMiniHero(TilesAddon ta) {
        // MINIHERO
        return IcnKind.MINIHERO == Mp2Kind.GetICNObject(ta.object);
    }

    static boolean isCastle(TilesAddon ta) {
        // OBJNTOWN
        return IcnKind.OBJNTOWN == Mp2Kind.GetICNObject(ta.object);
    }

    boolean isStump(TilesAddon ta) {
        switch (Mp2.GetICNObject(ta.object)) {
            case IcnKind.OBJNSNOW:
                if (ta.index == 41 || ta.index == 42)
                    return true;
                break;

            default:
                break;
        }

        return false;
    }

    boolean isDeadTrees(TilesAddon ta) {
        switch (Mp2.GetICNObject(ta.object)) {
            case IcnKind.OBJNMUL2:
                if (ta.index == 16 || ta.index == 18 || ta.index == 19)
                    return true;
                break;

            case IcnKind.OBJNMULT:
                if (ta.index == 0 || ta.index == 2 || ta.index == 4)
                    return true;
                break;

            case IcnKind.OBJNSNOW:
                if ((ta.index > 50 && ta.index < 53) || (ta.index > 54 && ta.index < 59) ||
                        (ta.index > 59 && ta.index < 63) || (ta.index > 63 && ta.index < 67) ||
                        ta.index == 68 || ta.index == 69 || ta.index == 71 || ta.index == 72 ||
                        ta.index == 74 || ta.index == 75 || ta.index == 77)
                    return true;
                break;

            case IcnKind.OBJNSWMP:
                if (ta.index == 161 || ta.index == 162 ||
                        (ta.index > 163 && ta.index < 170) ||
                        (ta.index > 170 && ta.index < 175) ||
                        ta.index == 176 || ta.index == 177)
                    return true;
                break;

            default:
                break;
        }

        return false;
    }

    void UpdateAbandoneMineRightSprite(TilesAddon ta) {
        if (IcnKind.OBJNDIRT == Mp2.GetICNObject(ta.object) && ta.index == 9) {
            ta.object = 104;
            ta.index = 113;
        } else if (IcnKind.OBJNGRAS == Mp2.GetICNObject(ta.object) && ta.index == 7) {
            ta.object = 128;
            ta.index = 83;
        }
    }

    void UpdateFountainSprite(TilesAddon ta) {
        if (IcnKind.OBJNMUL2 == Mp2.GetICNObject(ta.object) && 15 == ta.index) {
            ta.object = 0x14;
            ta.index = 0;
        }
    }

    void UpdateTreasureChestSprite(TilesAddon ta) {
        if (IcnKind.OBJNRSRC == Mp2.GetICNObject(ta.object) && 19 == ta.index) {
            ta.object = 0x15;
            ta.index = 0;
        }
    }


    @Override
    public String toString() {
        return "{" +
                "level:" + level +
                ",object:" + object +
                ",index:" + index +
                '}';
    }

    public void setObject(int object) {
        this.object = (byte) object;
    }

    public int getObject() {
        return object;
    }


    boolean isUniq(int id) {
        return uniq == id;
    }

    boolean isICN(int icn) {
        return icn == Mp2.GetICNObject(object);
    }

    public void setIndex(int index) {
        this.index = (short) index;
    }

    public int getIndex() {
        if (index == -87) {
            System.out.println("Here");
        }
        return index;
    }

    public String toJsonRow() {
        var sb = new StringBuilder();
        sb.append("{");
        writeField(sb, "uniq", uniq);
        writeField(sb, "level", level);
        writeField(sb, "object", object);
        writeFieldBare(sb, "index", index);

        sb.append("}");
        return sb.toString();
    }

    public boolean isRoad(int direct) {
        switch (Mp2.GetICNObject(object)) {
            // from sprite road
            case IcnKind.ROAD:
                if (0 == index ||
                        4 == index ||
                        5 == index ||
                        13 == index ||
                        26 == index)
                    return 0 != (direct & (Direction.TOP | Direction.BOTTOM));
                if (2 == index ||
                        21 == index ||
                        28 == index)
                    return 0 != (direct & (Direction.LEFT | Direction.RIGHT));
                if (17 == index ||
                        29 == index)
                    return 0 != (direct & (Direction.TOP_LEFT | Direction.BOTTOM_RIGHT));
                if (18 == index ||
                        30 == index)
                    return 0 != (direct & (Direction.TOP_RIGHT | Direction.BOTTOM_LEFT));
                if (3 == index)
                    return 0 != (direct & (Direction.TOP | Direction.BOTTOM | Direction.LEFT | Direction.RIGHT));
                if (6 == index) return 0 != (direct & (Direction.TOP | Direction.BOTTOM | Direction.RIGHT));
                if (7 == index) return 0 != (direct & (Direction.TOP | Direction.RIGHT));
                if (9 == index) return 0 != (direct & (Direction.BOTTOM | Direction.RIGHT));
                if (12 == index) return 0 != (direct & (Direction.BOTTOM | Direction.LEFT));
                if (14 == index) return 0 != (direct & (Direction.TOP | Direction.BOTTOM | Direction.LEFT));
                if (16 == index) return 0 != (direct & (Direction.TOP | Direction.LEFT));
                if (19 == index) return 0 != (direct & (Direction.TOP_LEFT | Direction.BOTTOM_RIGHT));
                if (20 == index) return 0 != (direct & (Direction.TOP_RIGHT | Direction.BOTTOM_LEFT));
                break;

            // castle and tower (gate)
            case IcnKind.OBJNTOWN:
                if (13 == index ||
                        29 == index ||
                        45 == index ||
                        61 == index ||
                        77 == index ||
                        93 == index ||
                        109 == index ||
                        125 == index ||
                        141 == index ||
                        157 == index ||
                        173 == index ||
                        189 == index)
                    return 0 != (direct & (Direction.TOP | Direction.BOTTOM));
                break;
            // castle lands (gate)
            case IcnKind.OBJNTWBA:
                if (7 == index ||
                        17 == index ||
                        27 == index ||
                        37 == index ||
                        47 == index ||
                        57 == index ||
                        67 == index ||
                        77 == index)
                    return 0 != (direct & (Direction.TOP | Direction.BOTTOM));
                break;

            default:
                break;
        }

        return false;
    }

}
