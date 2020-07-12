package hellofx.fheroes2.heroes;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Bitmap;
import hellofx.fheroes2.ai.AI;
import hellofx.fheroes2.army.Army;
import hellofx.fheroes2.army.Troop;
import hellofx.fheroes2.battle.BattleResult;
import hellofx.fheroes2.castle.Castle;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.dialog.Dialogs;
import hellofx.fheroes2.game.GameStatic;
import hellofx.fheroes2.gui.H2Font;
import hellofx.fheroes2.heroes.route.Path;
import hellofx.fheroes2.kingdom.*;
import hellofx.fheroes2.maps.IndexObject;
import hellofx.fheroes2.maps.MapPosition;
import hellofx.fheroes2.maps.Mp2;
import hellofx.fheroes2.maps.Mp2Kind;
import hellofx.fheroes2.maps.objects.Maps;
import hellofx.fheroes2.monster.Monster;
import hellofx.fheroes2.resource.Artifact;
import hellofx.fheroes2.resource.ArtifactKind;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.spell.Spell;
import hellofx.fheroes2.spell.SpellStorage;
import hellofx.fheroes2.system.Settings;
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
import static hellofx.fheroes2.system.Translate.tr;

public class Heroes extends HeroBase {
    public String name;
    public ColorBase killer_color = new ColorBase();
    public MapPosition mapPosition = new MapPosition();

    public int experience;
    public int move_point_scale;

    public SecSkills secondary_skills = new SecSkills();

    public Army army;

    public int hid; /* hero id */
    public int portrait; /* hero id */
    public int race;
    public int save_maps_object;

    public Path path = new Path();

    public int direction;
    public int sprite_index;

    public H2Point patrol_center = new H2Point();
    public int patrol_square;

    public List<IndexObject> visit_object = new ArrayList<>();

    private static final String[] heroNames = {
            // knight
            tr("Lord Kilburn"), tr("Sir Gallanth"), tr("Ector"), tr("Gwenneth"), tr("Tyro"), tr("Ambrose"), tr("Ruby"),
            tr("Maximus"), tr("Dimitry"),
            // barbarian
            tr("Thundax"), tr("Fineous"), tr("Jojosh"), tr("Crag Hack"), tr("Jezebel"), tr("Jaclyn"), tr("Ergon"), tr("Tsabu"),
            tr("Atlas"),
            // sorceress
            tr("Astra"), tr("Natasha"), tr("Troyan"), tr("Vatawna"), tr("Rebecca"), tr("Gem"), tr("Ariel"), tr("Carlawn"),
            tr("Luna"),
            // warlock
            tr("Arie"), tr("Alamar"), tr("Vesper"), tr("Crodo"), tr("Barok"), tr("Kastore"), tr("Agar"), tr("Falagar"),
            tr("Wrathmont"),
            // wizard
            tr("Myra"), tr("Flint"), tr("Dawn"), tr("Halon"), tr("Myrini"), tr("Wilfrey"), tr("Sarakin"), tr("Kalindra"),
            tr("Mandigal"),
            // necromant
            tr("Zom"), tr("Darlana"), tr("Zam"), tr("Ranloo"), tr("Charity"), tr("Rialdo"), tr("Roxana"), tr("Sandro"),
            tr("Celia"),
            // campains
            tr("Roland"), tr("Lord Corlagon"), tr("Sister Eliza"), tr("Archibald"), tr("Lord Halton"), tr("Brother Bax"),
            // loyalty version
            tr("Solmyr"), tr("Dainwin"), tr("Mog"), tr("Uncle Ivan"), tr("Joseph"), tr("Gallavant"), tr("Elderian"),
            tr("Ceallach"), tr("Drakonia"), tr("Martine"), tr("Jarkonas"),
            // debug
            "SandySandy", "Unknown"
    };

    public Heroes(int heroid, int rc) {
        super(HeroType.HEROES, rc);
        army = new Army(this);
        army.Reset(true);
        hid = heroid;
        portrait = heroid;

        color.color = H2Color.NONE;
        experience = 0;
        move_point_scale = -1;
        secondary_skills = new SecSkills(rc);
        army = new Army(this);
        race = rc;
        save_maps_object = Mp2Kind.OBJ_ZERO;

        path = new Path(this);
        direction = Direction.RIGHT;
        sprite_index = 18;
        patrol_square = 0;
        name = Heroes.GetName(heroid);
    }

    @Override
    protected Castle inCastle() {
        var world = World.Instance;
        Castle castle = H2Color.NONE != GetColor() ? world.GetCastle(GetCenter()) : null;
        return castle != null && castle.GetHeroes().isEqual(this) ? castle : null;
    }

    private static String GetName(int heroid) {
        return heroNames[heroid];
    }

    public int GetMapsObject() {
        return save_maps_object;
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
                sec.SetSkill(st.get() + 1);

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
            name = st.toString(13);
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

    public H2Point GetCenter() {
        return mapPosition.GetCenter();
    }

    private boolean PickupArtifact(Artifact art) {
        if (!art.IsValid()) return false;

        //const Settings & conf = Settings::Get();

        if (!bag_artifacts.PushArtifact(art)) {
            if (isControlHuman()) {
                if (art.GetID() == ArtifactKind.MAGIC_BOOK) {
                    Dialogs.Message("",
                            tr(
                                    "You must purchase a spell book to use the mage guild, but you currently have no room for a spell book. Try giving one of your artifacts to another hero."
                            ),
                            H2Font.BIG, Dialogs.OK);
                } else {
                    Dialogs.Message(art.GetName(), tr("You have no room to carry another artifact!"), H2Font.BIG, Dialogs.OK);
                }

            }
            return false;
        }

        // check: anduran garb
        if (bag_artifacts.MakeBattleGarb()) {
            if (isControlHuman()) {
                Dialogs.ArtifactInfo("", tr("The three Anduran artifacts magically combine into one."), ArtifactKind.BATTLE_GARB);
            }
        }

        return true;
    }

    private boolean isControlHuman() {
        //TODO
        return false;
    }

    public boolean isFreeman() {

        return isValid() && H2Color.NONE == GetColor() && !bitModes.Modes(JAIL);
    }

    private int GetColor() {
        return color.GetColor();
    }

    public boolean isValid() {
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

        if ((race & (RaceKind.SORC | RaceKind.WRLK | RaceKind.WZRD | RaceKind.NECR)) != 0 &&
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
        return HeroesUtils.GetLevelFromExperience(experience);
    }


    private void AppendSpellToBook(Spell spell, boolean without_wisdom) {
        if (without_wisdom || CanLearnSpell(spell))
            spell_book.Append(spell);
    }

    public void AppendSpellsToBook(SpellStorage spells, boolean without_wisdom) {
        for (var spell : spells._items)
            AppendSpellToBook(spell, without_wisdom);
    }

    private void LevelUp(boolean modes, boolean b) {
        //TODO
    }

    private Spell GetInitialSpell(int race) {
        stats_t ptr = GameStatic.GetSkillStats(race);
        var resultId = ptr != null ? ptr.initial_spell : 0;
        return new Spell(resultId);
    }

    /* set enable move */
    void SetMove(boolean f) {
        if (f) {
            SetModes(ENABLEMOVE);
        } else {
            ResetModes(ENABLEMOVE);

            // reset sprite position
            switch (direction) {
                case Direction.TOP:
                    sprite_index = 0;
                    break;
                case Direction.BOTTOM:
                    sprite_index = 36;
                    break;
                case Direction.TOP_RIGHT:
                case Direction.TOP_LEFT:
                    sprite_index = 9;
                    break;
                case Direction.BOTTOM_RIGHT:
                case Direction.BOTTOM_LEFT:
                    sprite_index = 27;
                    break;
                case Direction.RIGHT:
                case Direction.LEFT:
                    sprite_index = 18;
                    break;
                default:
                    break;
            }
        }
    }

    private void ResetModes(int flags) {
        bitModes.ResetModes(flags);
    }

    private void SetModes(int flags) {
        bitModes.SetModes(flags);
    }

    private int GetMaxMovePoints() {
        //TODO
        return 0;
    }

    public boolean isControlAI() {
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

    public boolean HaveSpellBook() {
        //TODO
        return false;
    }

    public void Redraw(Painter dst, int x, int y, boolean b) {
    }

    public int GetID() {
        return hid;
    }

    public void Recruit(int cl, H2Point pt) {
        //TODO
    }


    @Override
    public int GetType() {
        return HeroType.HEROES;
    }

    @Override
    public int GetRace() {
        return race;
    }

    public void SetFreeman(int reason) {
        //TODO
        if (isFreeman()) return;
        var savepoints = false;
        var world = World.Instance;
        Kingdom kingdom = GetKingdom();

        if (0 != ((BattleResult.RESULT_RETREAT | BattleResult.RESULT_SURRENDER) & reason)) {
            if (Settings.Get().ExtHeroRememberPointsForRetreating()) savepoints = true;
            kingdom.SetLastLostHero(this);
        }

        if (!army.m_troops.IsValid() || (0 != (BattleResult.RESULT_RETREAT & reason))) army.Reset(false);
        else if ((BattleResult.RESULT_LOSS & reason) != 0 && ((BattleResult.RESULT_SURRENDER & reason) == 0))
            army.Reset(true);

        if (GetColor() != H2Color.NONE) kingdom.RemoveHeroes(this);

        color.SetColor(H2Color.NONE);
        world.GetTiles(GetIndex()).SetHeroes(null);
        bitModes.modes = 0;
        SetIndex(-1);
        move_point_scale = -1;
        path.Reset();
        SetMove(false);
        SetModes(ACTION);
        if (savepoints) SetModes(SAVEPOINTS);
    }

    private void SetIndex(int i) {
        mapPosition.SetIndex(i);
    }

    private Kingdom GetKingdom() {
        return color.GetKingdom();
    }

    public boolean Recruit(Castle castle) {
        //TODO
        return false;
    }

    public void SetCenterPatrol(H2Point pt) {
        patrol_center = pt;
    }

    public Army GetArmy() {
        return army;
    }

    public int GetMorale() {
        return GetMoraleWithModificators(null);
    }

    private int GetMoraleWithModificators(String strs) {
        //TODO

        int result = MoraleKind.NORMAL;

        // bonus artifact
        result += GetMoraleKindModificator(strs);

        if (army.m_troops.AllTroopsIsRace(RaceKind.NECR)) return MoraleKind.NORMAL;

        // bonus leadership
        result += Skill.GetLeadershipModifiers(GetLevelSkill(SkillT.LEADERSHIP), strs);

        // object visited
        int[] objs = {
                Mp2Kind.OBJ_BUOY, Mp2Kind.OBJ_OASIS, Mp2Kind.OBJ_WATERINGHOLE, Mp2Kind.OBJ_TEMPLE, Mp2Kind.OBJ_GRAVEYARD,
                Mp2Kind.OBJ_DERELICTSHIP, Mp2Kind.OBJ_SHIPWRECK
        };
        result += ObjectVisitedModifiersResult(ModifierKind.MDF_MORALE, objs, objs.length, this, strs);

        // result
        if (result < MoraleKind.AWFUL) return MoraleKind.TREASON;
        if (result < MoraleKind.POOR) return MoraleKind.AWFUL;
        if (result < MoraleKind.NORMAL) return MoraleKind.POOR;
        if (result < MoraleKind.GOOD) return MoraleKind.NORMAL;
        if (result < MoraleKind.GREAT) return MoraleKind.GOOD;
        if (result < MoraleKind.BLOOD) return MoraleKind.GREAT;

        return MoraleKind.BLOOD;
    }

    private int ObjectVisitedModifiersResult(int mdfMorale, int[] objs, int size, Heroes hero, String strs) {
        int result = 0;

        for (var ii = 0; ii < size; ++ii) {
            if (hero.isVisited(objs[ii])) {
                result += GameStatic.ObjectVisitedModifiers(objs[ii]);

                if (strs != null) {
                    strs += Mp2.StringObject(objs[ii]);
                    strs = Skill.StringAppendModifiers(strs, GameStatic.ObjectVisitedModifiers(objs[ii]));
                    strs += "\n";
                }
            }
        }

        return result;
    }

    private boolean isVisited(int obj) {
        //TODO
        return false;
    }


    /* return route range in days */
    int GetRangeRouteDays(int dst) {
        var max = GetMaxMovePoints();
        var limit = max * 5 / 100; // limit ~5 day

        // approximate distance, this restriction calculation
        if (4 * max / 100 < Maps.GetApproximateDistance(GetIndex(), dst)) {
            return 0;
        }

        Path test = new Path(this);
        // approximate limit, this restriction path finding algorithm
        if (test.Calculate(dst, limit)) {
            var total = test.GetTotalPenalty();
            if (move_point >= total) return 1;

            total -= move_point;
            if (max >= total) return 2;

            total -= move_point;
            if (max >= total) return 3;

            return 4;
        }

        return 0;
    }

    public int GetIndex() {
        return mapPosition.GetIndex();
    }

    public boolean HasSecondarySkill(int skill) {
        //TODO
        return false;
    }

    public int GetSecondaryValues(int skill) {
        return secondary_skills.GetValues(skill);
    }

    public int GetLevelSkill(int skill) {
        return secondary_skills.GetLevel(skill);
    }

    public Path GetPath() {
        return path;
    }

    public int GetMovePoints() {
        return move_point;
    }

    public boolean isEnableMove() {
        return bitModes.Modes(ENABLEMOVE) && path.isValid() && path.GetFrontPenalty() <= move_point;
    }

    public int GetSpriteIndex() {
        return sprite_index;
    }

    public void SetMapsObject(int obj) {
        save_maps_object = obj != Mp2Kind.OBJ_HEROES ? obj : Mp2Kind.OBJ_ZERO;
    }
}
