package hellofx.dialogs.atoms;

import hellofx.common.Utilities;
import hellofx.dialogs.atoms.common.ViewBuilderBase;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ImageWithPicViewBuilder
        extends ViewBuilderBase<ImageWithPicViewModel, Parent> {
    @Override
    public void build() {
        var root = new GridPane();
        var constraint = new RowConstraints();
        constraint.setValignment(VPos.CENTER);
        root.getRowConstraints().add(constraint);
        var constraint2 = new RowConstraints();
        constraint2.setValignment(VPos.CENTER);
        root.getRowConstraints().add(constraint2);
        var imageView = new ImageView();
        GridPane.setRowIndex(imageView, 0);
        ViewModel.ImageView = imageView;
        Label label = new Label();
        GridPane.setRowIndex(label, 1);
        ViewModel.Label = label;
        root.setMinHeight(50);
        View = root;
    }

    @Override
    public void onVmChanges(String propertyName) {
        ViewModel.ImageView.setImage(new Image(Utilities.getResource(ViewModel.imageFile)));
        ViewModel.Label.setText(ViewModel.text);
    }
}
