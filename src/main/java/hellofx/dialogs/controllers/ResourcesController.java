package hellofx.dialogs.controllers;

import hellofx.graphics.ImageRepo;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ResourcesController {

    public Label goldLbl;
    public Label woodLbl;
    public Label oreLbl;
    public Label crystalLbl;
    public ImageView goldImg;
    public ImageView woodImg;
    public ImageView oreImg;
    public ImageView crystalImg;

    public void setup(ImageRepo imageRepo) {
        goldImg.setImage(imageRepo.get("data/dlg_img/cash.png"));
        woodImg.setImage(imageRepo.get("data/dlg_img/cash.png"));
        oreImg.setImage(imageRepo.get("data/dlg_img/cash.png"));
        crystalImg.setImage(imageRepo.get("data/dlg_img/cash.png"));
    }

    public void setValues(int gold, int wood, int ore, int crystal) {
        goldLbl.setText("" + gold);
        woodLbl.setText("" + wood);
        oreLbl.setText("" + ore);
        crystalLbl.setText("" + crystal);
    }
}
