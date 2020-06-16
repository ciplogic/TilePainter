package hellofx.player;

import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.system.BitModes;
import hellofx.fheroes2.system.Focus;

import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class Player {
    public static final int ST_INGAME = 0x2000;
    public String name;
    public int color;
    int control;
    int race;
    int friends;
    int id;
    Focus focus = new Focus();
    BitModes _bitModes = new BitModes();

    public Player() {

    }

    public Player(int col) {
        this.color = col;
        this.name = H2Color.String(color);
    }

    public void SetRace(byte rc) {
        SetRace(toByte(rc));
    }

    public void SetRace(int rc) {
        race = rc;
    }

    public void SetControl(int ctrl) {
        control = ctrl;
    }

    public void SetFriends(int f) {
        friends = f;
    }

    public int GetControl() {
        return control;
    }

    public void SetPlay(boolean f) {
        if (f) _bitModes.SetModes(ST_INGAME);
        else _bitModes.ResetModes(ST_INGAME);
    }

    public Focus GetFocus() {
        return focus;
    }

    public int GetRace() {
        return race;
    }

    public int GetColor() {
        return color;
    }
}
