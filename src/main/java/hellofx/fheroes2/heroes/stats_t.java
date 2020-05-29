package hellofx.fheroes2.heroes;

public class stats_t {
    public String id = "";
    public primary_t captain_primary = new primary_t();
    public primary_t initial_primary = new primary_t();
    public byte initial_book;
    public byte initial_spell;
    public secondary_t initial_secondary = new secondary_t();
    public byte over_level;
    public primary_t mature_primary_under = new primary_t();
    public primary_t mature_primary_over = new primary_t();
    public secondary_t mature_secondary = new secondary_t();

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
