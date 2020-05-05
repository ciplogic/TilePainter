package hellofx.dialogs.atoms;

import hellofx.dialogs.atoms.common.ViewModelBase;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ImageWithPicViewModel extends ViewModelBase {
    public String text;
    public ImageView ImageView;
    public Label Label;
    public String imageFile;


    public void setImage(String value) {
        this.imageFile = value;
        notify("image");
    }
}
