package hellofx.dialogs.controllers;

import javafx.scene.control.Label;

public class ResourcesController {

    public Label goldLbl;
    public Label woodLbl;
    public Label oreLbl;
    public Label crystalLbl;

    public void setValues(int gold, int wood, int ore, int crystal) {
        goldLbl.setText("Gold: " + gold);
        woodLbl.setText("Wood:" + wood);
        oreLbl.setText("Ore: " + ore);
        crystalLbl.setText("Crystal:" + crystal);
    }
}
