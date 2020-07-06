package hellofx.fheroes2.heroes;

public class stats_t {
    public String id = "";
    public primary_t captain_primary;
    public primary_t initial_primary;
    public byte initial_book;
    public byte initial_spell;
    public secondary_t initial_secondary;
    public byte over_level;
    public primary_t mature_primary_under;
    public primary_t mature_primary_over;
    public secondary_t mature_secondary;

    public stats_t(String id, primary_t captain_primary,
                   primary_t initial_primary,
                   int initial_book,
                   int initial_spell,
                   secondary_t initial_secondary,
                   int over_level,
                   primary_t mature_primary_under,
                   primary_t mature_primary_over,
                   secondary_t mature_secondary) {
        this.id = id;
        this.initial_primary = initial_primary;
        this.captain_primary = captain_primary;
        this.initial_book = (byte) initial_book;
        this.initial_spell = (byte) initial_spell;
        this.initial_secondary = initial_secondary;
        this.over_level = (byte) over_level;
        this.mature_primary_under = mature_primary_under;
        this.mature_primary_over = mature_primary_over;
        this.mature_secondary = mature_secondary;
    }
}
