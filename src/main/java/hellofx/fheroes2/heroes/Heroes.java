package hellofx.fheroes2.heroes;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Bitmap;
import hellofx.fheroes2.ai.AI;
import hellofx.fheroes2.army.Army;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.kingdom.ColorBase;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.maps.H2Obj;
import hellofx.fheroes2.maps.IndexObject;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.spell.Spell;

import java.util.ArrayList;
import java.util.List;

import static hellofx.fheroes2.agg.IcnKind.MINIPORT;
import static hellofx.fheroes2.agg.IcnKind.PORTMEDI;
import static hellofx.fheroes2.heroes.HeroFlags.*;
import static hellofx.fheroes2.heroes.HeroPortraitSize.*;
import static hellofx.fheroes2.heroes.HeroesKind.BAX;
import static hellofx.fheroes2.heroes.HeroesKind.SANDYSANDY;

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
        if (HeroesKind.UNKNOWN == id) {
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

    public void PostLoad() {
        killer_color.SetColor(H2Color.NONE);

        // save general object
        save_maps_object = H2Obj.OBJ_ZERO;

        // fix zero army
        if (!army.m_troops.IsValid())
            army.Reset(true);
        else
            bitModes.SetModes(CUSTOMARMY);

        // level up
        int level = GetLevel();
        while (1 < level--) {
            bitModes.SetModes(NOTDEFAULTS);
            LevelUp(bitModes.Modes(CUSTOMSKILLS), true);
        }

        if (((race & (RaceKind.SORC | RaceKind.WRLK | RaceKind.WZRD | RaceKind.NECR)) != 0) &&
                !HaveSpellBook()) {
            Spell spell = GetInitialSpell(race);
            if (spell.isValid()) {
                SpellBookActivate();
                AppendSpellToBook(spell, true);
            }
        }

        // other param
        SetSpellPoints(GetMaxSpellPoints());
        move_point = GetMaxMovePoints();

        if (isControlAI()) {
            AI.HeroesPostLoad(this);
        }
    }

    private int GetLevel() {
        //TODO
        return 0;
    }

    private void AppendSpellToBook(Spell spell, boolean b) {
        //TODO
    }

    private void SpellBookActivate() {
        //TODO
    }

    private void LevelUp(boolean modes, boolean b) {
        //TODO
    }

    private Spell GetInitialSpell(int race) {
        //TODO
        return null;
    }

    private int GetMaxMovePoints() {
        //TODO
        return 0;
    }

    private boolean isControlAI() {
        //TODO
        return false;
    }

    private void SetSpellPoints(int spellPoints) {
        //TODO
    }

    private int GetMaxSpellPoints() {
        //TODO
        return 0;
    }

    private boolean HaveSpellBook() {
        //TODO
        return false;
    }
}
