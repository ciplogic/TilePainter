package hellofx.fheroes2.system;

public class BitModes {
    public int modes;

    public void SetModes(int f) {
        modes |= f;
    }

    public void ResetModes(int f) {
        modes &= ~f;
    }

    public void ToggleModes(int f) {
        modes ^= f;
    }

    public boolean Modes(int f) {
        return (modes & f) != 0;
    }
}
