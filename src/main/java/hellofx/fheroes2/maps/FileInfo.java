package hellofx.fheroes2.maps;

import hellofx.fheroes2.game.DifficultyEnum;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.kingdom.H2Colors;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.serialize.FileUtils;

import static hellofx.fheroes2.game.GameConsts.KINGDOMMAX;

public class FileInfo {
    public String file;
    public String name;
    public String description;

    public short size_w;
    public short size_h;
    public byte difficulty;
    private static final int LENGTHNAME = 16;
    public byte[] unions = new byte[KINGDOMMAX];

    public byte kingdom_colors;
    public byte allow_human_colors;
    public byte allow_comp_colors;
    public byte rnd_races;

    public byte conditions_wins; // 0: wins def, 1: town, 2: hero, 3: artifact, 4: side, 5: gold
    public boolean comp_also_wins;
    public boolean allow_normal_victory;
    public short wins1;
    public short wins2;
    public byte conditions_loss; // 0: loss def, 1: town, 2: hero, 3: out time
    public short loss1;
    public short loss2;

    public int localtime;

    public boolean with_heroes;
    private static final int LENGTHDESCRIPTION = 143;
    public int[] races = new int[KINGDOMMAX];

    public int KingdomRace(int color) {
        return switch (color) {
            case H2Color.BLUE -> races[0];
            case H2Color.GREEN -> races[1];
            case H2Color.RED -> races[2];
            case H2Color.YELLOW -> races[3];
            case H2Color.ORANGE -> races[4];
            case H2Color.PURPLE -> races[5];
            default -> 0;
        };
    }

    public int HumanOnlyColors() {
        return allow_human_colors & ~allow_comp_colors;
    }

    public int AllowHumanColors() {
        return allow_human_colors;
    }

    int ByteToRace(int byteValue) {
        return switch (byteValue) {
            case 0x00 -> RaceKind.KNGT;
            case 0x01 -> RaceKind.BARB;
            case 0x02 -> RaceKind.SORC;
            case 0x03 -> RaceKind.WRLK;
            case 0x04 -> RaceKind.WZRD;
            case 0x05 -> RaceKind.NECR;
            case 0x06 -> RaceKind.MULT;
            case 0x07 -> RaceKind.RAND;
            default -> RaceKind.NONE;
        };

    }

    int ByteToColor(int byteColor) {
        return switch (byteColor) {
            case 0 -> H2Color.BLUE;
            case 1 -> H2Color.GREEN;
            case 2 -> H2Color.RED;
            case 3 -> H2Color.YELLOW;
            case 4 -> H2Color.ORANGE;
            case 5 -> H2Color.PURPLE;
            default -> H2Color.NONE;
        };

    }

    public boolean ReadMP2(String filename) {
        Reset();
        var fileData = FileUtils.ReadAllBytes(filename);
        ByteVectorReader fs = new ByteVectorReader(fileData);

        file = filename;
        kingdom_colors = 0;
        allow_human_colors = 0;
        allow_comp_colors = 0;
        rnd_races = 0;
        localtime = 0;

        // magic byte
        if (fs.getBE32() != 0x5C000000) {
            return false;
        }

        // level
        switch (fs.getLE16()) {
            case 0x00:
                difficulty = (byte) DifficultyEnum.EASY;
                break;
            case 0x01:
                difficulty = (byte) DifficultyEnum.NORMAL;
                break;
            case 0x02:
                difficulty = (byte) DifficultyEnum.HARD;
                break;
            case 0x03:
                difficulty = (byte) DifficultyEnum.EXPERT;
                break;
            default:
                difficulty = (byte) DifficultyEnum.NORMAL;
                break;
        }

        // width
        size_w = (short) fs.get();

        // height
        size_h = (short) fs.get();

        H2Colors colors = new H2Colors(H2Color.ALL);

        // kingdom color - blue, green, red, yellow, orange, purple
        for (var _item : colors._items)
            if (fs.get() != 0) kingdom_colors |= _item;

        // allow human color - blue, green, red, yellow, orange, purple
        for (var _item : colors._items)
            if (fs.get() != 0) allow_human_colors |= _item;

        // allow comp color - blue, green, red, yellow, orange, purple
        for (var _item : colors._items)
            if (fs.get() != 0) allow_comp_colors |= _item;

        // kingdom count
        // fs.seekg(0x1A, std.ios_base.beg);
        // fs.get();

        // wins
        fs.seek(0x1D);
        conditions_wins = (byte) fs.get();
        // data wins
        comp_also_wins = fs.get() != 0;
        // data wins
        allow_normal_victory = fs.get() != 0;
        // data wins
        wins1 = (short) fs.getLE16();
        // data wins
        fs.seek(0x2c);
        wins2 = (short) fs.getLE16();

        // loss
        fs.seek(0x22);
        conditions_loss = (byte) fs.get();
        // data loss
        loss1 = (short) fs.getLE16();
        // data loss
        fs.seek(0x2e);
        loss2 = (short) fs.getLE16();

        // start with hero
        fs.seek(0x25);
        with_heroes = 0 == fs.get();

        // race color
        for (var _item : colors._items) {
            int race = ByteToRace(fs.get());
            races[H2Color.GetIndex(_item)] = race;
            if (RaceKind.RAND == race) rnd_races |= _item;
        }

        // name
        fs.seek(0x3A);
        name = (fs.toString(LENGTHNAME));

        // description
        fs.seek(0x76);
        description = (fs.toString(LENGTHDESCRIPTION));

        //fill unions
        if (4 == conditions_wins)
            FillUnions();

        return true;
    }

    private void Reset() {
    }

    void FillUnions() {
        int side1 = 0;
        int side2 = 0;

        H2Colors colors = new H2Colors(kingdom_colors);

        for (int color : colors._items) {
            if (H2Color.GetIndex(color) < wins1)
                side1 |= color;
            else
                side2 |= color;
        }

        for (var ii = 0; ii < KINGDOMMAX; ++ii) {
            int cl = ByteToColor(ii);

            if ((side1 & cl) != 0)
                unions[ii] = (byte) side1;
            else if ((side2 & cl) != 0)
                unions[ii] = (byte) side2;
            else
                unions[ii] = (byte) cl;
        }
    }
}
