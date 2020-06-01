package hellofx.fheroes2.agg;

import hellofx.fheroes2.serialize.FileUtils;

import java.util.HashMap;
import java.util.Map;

public class XmiKind {
    public static final int UNKNOWN = 0;
    public static final int MIDI0002 = 1;
    public static final int MIDI0003 = 2;
    public static final int MIDI0004 = 3;
    public static final int MIDI0005 = 4;
    public static final int MIDI0006 = 5;
    public static final int MIDI0007 = 6;
    public static final int MIDI0008 = 7;
    public static final int MIDI0009 = 8;
    public static final int MIDI0010 = 9;
    public static final int MIDI0011 = 10;
    public static final int MIDI0013 = 11;
    public static final int MIDI0014 = 12;
    public static final int MIDI0015 = 13;
    public static final int MIDI0017 = 14;
    public static final int MIDI0018 = 15;
    public static final int MIDI0042 = 16;
    public static final int MIDI0043 = 17;

    static Map<String, Integer> _names;
    static Map<Integer, String> _toName = new HashMap<>();

    static {
        _names = FileUtils.EnumFieldsOfClass(XmiKind.class);
        for (var name : _names.keySet()) {
            _toName.put(_names.get(name), name);
        }
    }

    public static String GetString(int icn) {
        if (icn == UNKNOWN)
            return "Unknown";
        var actualName = _toName.get(icn);
        return actualName + ".XMI";
    }

    public static int FromMUS(int mus) {
        switch (mus) {
            case MusKind.BATTLE1:
                return MIDI0002;
            case MusKind.BATTLE2:
                return MIDI0003;
            case MusKind.BATTLE3:
                return MIDI0004;
            case MusKind.SORCERESS:
                return MIDI0005;
            case MusKind.WARLOCK:
                return MIDI0006;
            case MusKind.NECROMANCER:
                return MIDI0007;
            case MusKind.KNIGHT:
                return MIDI0008;
            case MusKind.BARBARIAN:
                return MIDI0009;
            case MusKind.WIZARD:
                return MIDI0010;
            case MusKind.LAVA:
                return MIDI0011;
            case MusKind.DESERT:
                return MIDI0013;
            case MusKind.SNOW:
                return MIDI0014;
            case MusKind.SWAMP:
                return MIDI0015;
            case MusKind.DIRT:
                return MIDI0017;
            case MusKind.GRASS:
                return MIDI0018;
            case MusKind.MAINMENU:
                return MIDI0042;
            case MusKind.VICTORY:
                return MIDI0043;
            default:
                break;
        }

        return UNKNOWN;
    }
}
