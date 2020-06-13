package hellofx.fheroes2.maps;

import static hellofx.fheroes2.game.GameConsts.KINGDOMMAX;

public class FileInfo {
    public String file;
    public String name;
    public String description;

    public short size_w;
    public short size_h;
    public byte difficulty;
    public byte[] races = new byte[KINGDOMMAX];
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
}
