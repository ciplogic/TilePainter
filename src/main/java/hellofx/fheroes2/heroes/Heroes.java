package hellofx.fheroes2.heroes;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Bitmap;
import hellofx.fheroes2.ai.AI;
import hellofx.fheroes2.army.Army;
import hellofx.fheroes2.army.Troop;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.game.GameStatic;
import hellofx.fheroes2.kingdom.ColorBase;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.maps.IndexObject;
import hellofx.fheroes2.maps.MapPosition;
import hellofx.fheroes2.maps.Mp2Kind;
import hellofx.fheroes2.monster.Monster;
import hellofx.fheroes2.resource.Artifact;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.spell.Spell;
import hellofx.framework.controls.Painter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static hellofx.fheroes2.agg.icn.IcnKind.MINIPORT;
import static hellofx.fheroes2.agg.icn.IcnKind.PORTMEDI;
import static hellofx.fheroes2.heroes.HeroFlags.*;
import static hellofx.fheroes2.heroes.HeroPortraitSize.*;
import static hellofx.fheroes2.heroes.HeroesKind.BAX;
import static hellofx.fheroes2.heroes.HeroesKind.SANDYSANDY;

public class Heroes extends HeroBase {
    public String name;
    public ColorBase killer_color = new ColorBase();
    public MapPosition mapPosition = new MapPosition();

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
        return save_maps_object;
    }

    public int GetRace() {
        return race;
    }

    public void LoadFromMP2(int map_index, int cl, int rc, ByteVectorReader st) {
        // reset modes
        bitModes.modes = 0;

        mapPosition.SetIndex(map_index);
        color.SetColor(cl);

        // unknown
        st.skip(1);

        // custom troops
        if (st.get() != 0) {
            Troop[] troops = new Troop[5];
            IntStream.range(0, 5)
                    .forEach(i -> troops[i] = new Troop());

            // set monster id
            for (var troop : troops)
                troop.SetMonster(new Monster(st.get() + 1));

            // set count
            for (var troop : troops)
                troop.SetCount(st.getLE16());

            army.m_troops.Assign(troops);
        } else
            st.skip(15);

        // custom portrate
        boolean custom_portrait = st.get() != 0;

        if (custom_portrait) {
            bitModes.SetModes(NOTDEFAULTS);

            // index sprite portrait
            portrait = st.get();

            if (HeroesKind.UNKNOWN <= portrait) {
                portrait = hid;
            }

            // fixed race for custom portrait (after level up)
            if (race != rc)
                race = rc;
        } else
            st.skip(1);

        // 3 artifacts
        PickupArtifact(new Artifact(st.get()));
        PickupArtifact(new Artifact(st.get()));
        PickupArtifact(new Artifact(st.get()));

        // unknown byte
        st.skip(1);

        // experience
        experience = st.getLE32();

        boolean custom_secskill = st.get() != 0;

        // custom skill
        if (custom_secskill) {
            bitModes.SetModes(NOTDEFAULTS);
            bitModes.SetModes(CUSTOMSKILLS);
            Secondary[] secs = new Secondary[8];
            IntStream.range(0, secs.length).forEach(i -> secs[i] = new Secondary());

            for (var sec : secs)
                sec.SetSkill((st.get() + 1));

            for (var sec : secs)
                sec.SetLevel(st.get());

            secondary_skills = new SecSkills();

            for (var it : secs)
                if (it.isValid()) secondary_skills.AddSkill(it);
        } else
            st.skip(16);

        // unknown
        st.skip(1);

        // custom name
        if (st.get() != 0) {
            bitModes.SetModes(NOTDEFAULTS);
            name = (st.toString(13));
        } else
            st.skip(13);

        // patrol
        if (st.get() != 0) {
            bitModes.SetModes(PATROL);
            patrol_center = GetCenter();
        }

        // count square
        patrol_square = st.get();

        PostLoad();
    }

    private H2Point GetCenter() {
        //TODO
        return new H2Point();
    }

    private void PickupArtifact(Artifact artifact) {
        //TODO
    }

    public boolean isFreeman() {

        return isValid() && H2Color.NONE == GetColor() && (!bitModes.Modes(JAIL));
    }

    private int GetColor() {
        return color.GetColor();
    }

    private boolean isValid() {
        return hid != HeroesKind.UNKNOWN;
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
        save_maps_object = Mp2Kind.OBJ_ZERO;

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
        stats_t ptr = GameStatic.GetSkillStats(race);
        var resultId = ptr != null ? ptr.initial_spell : 0;
        return new Spell(resultId);
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

    public void Redraw(Painter dst, int x, int y, boolean b) {
    }
}
