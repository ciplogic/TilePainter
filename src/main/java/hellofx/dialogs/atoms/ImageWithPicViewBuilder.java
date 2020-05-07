package hellofx.dialogs.atoms;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ImageWithPicViewBuilder {
    public static ImageWithPicViewModel buildViewModel(String resource, String imageResource) {
        ImageWithPicViewModel ViewModel = new ImageWithPicViewModel();
        var root = ViewUtils.gridPaneConstrained(1, 2, 10);
        var imageView = ViewUtils.imageViewFromPath(imageResource);
        GridPane.setColumnIndex(imageView, 0);
        var label = new Label(resource);
        GridPane.setColumnIndex(label, 1);
        root.getChildren().addAll(imageView, label);
        root.setMinHeight(30);
        root.setMaxWidth(230);
        ViewModel.ImageView = imageView;
        ViewModel.Label = label;
        ViewModel.View = root;
        return ViewModel;
    }
}
