package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.army.Troop;
import hellofx.fheroes2.maps.ObjectColor;

public class CapturedObject {
    ObjectColor objcol = new ObjectColor();
    Troop guardians = new Troop();

    public Troop GetTroop() {
        return guardians;
    }

    public void SetColor(int col) {
    }

    public int GetColor() {
        return objcol.color;
    }

    public void Set(int obj, int col) {
        objcol = new ObjectColor(obj, col);
    }
}
