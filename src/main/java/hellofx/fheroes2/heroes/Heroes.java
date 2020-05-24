package hellofx.fheroes2.heroes;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Bitmap;
import hellofx.fheroes2.army.Army;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.kingdom.ColorBase;
import hellofx.fheroes2.maps.IndexObject;
import hellofx.fheroes2.serialize.ByteVectorReader;

import java.util.ArrayList;
import java.util.List;

import static hellofx.fheroes2.agg.IcnKind.MINIPORT;
import static hellofx.fheroes2.agg.IcnKind.PORTMEDI;
import static hellofx.fheroes2.heroes.HeroPortraitSize.*;
import static hellofx.fheroes2.heroes.HeroesKinds.BAX;
import static hellofx.fheroes2.heroes.HeroesKinds.SANDYSANDY;

public class Heroes extends HeroBase {
    public String name;
    public ColorBase killer_color = new ColorBase();
    public int experience;
    public int move_point_scale;

    public SecSkills secondary_skills = new SecSkills();

    public Army army = new Army();

    public int hid; /* hero id */
    public int portrait; /* hero id */
    public int race;
    public int save_maps_object;

    public RoutePath path = new RoutePath();

    public int direction;
    public int sprite_index;

    public H2Point patrol_center = new H2Point();
    public int patrol_square;

    public List<IndexObject> visit_object = new ArrayList<>();

    public Heroes(int heroid, int rc) {
        super(HeroType.HEROES, rc);
        hid = heroid;
        portrait = heroid;
    }

    public int GetMapsObject() {
        //TODO
        return 0;
    }

    public int GetRace() {
        return race;
    }

    public void LoadFromMP2(int map_index, int cl, int race, ByteVectorReader st) {
    }

    public boolean isFreeman() {
        //TODO
        return false;
    }

    public static Bitmap GetPortrait(int id, int type) {
        if (HeroesKinds.UNKNOWN == id) {
            return Bitmap.Empty;
        }
        switch (type) {
            case PORT_BIG:
                return Agg.GetICN(PORTxxxx(id), 0);
            case PORT_MEDIUM:
                return SANDYSANDY > id
                        ? Agg.GetICN(PORTMEDI, id + 1)
                        : Agg.GetICN(PORTMEDI, BAX + 1);
            case PORT_SMALL:
                return SANDYSANDY > id ? Agg.GetICN(MINIPORT, id) : Agg.GetICN(MINIPORT, BAX);
            default:
                break;
        }

        return Bitmap.Empty;
    }
}
