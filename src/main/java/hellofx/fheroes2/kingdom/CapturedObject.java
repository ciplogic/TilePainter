package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.army.Troop;
import hellofx.fheroes2.maps.ObjectColor;

public class CapturedObject {
    ObjectColor objcol = new ObjectColor();
    Troop guardians = new Troop();
    int split = 1;

    public Troop GetTroop() {
        return guardians;
    }

    public void SetColor(int col) {
    }
}
