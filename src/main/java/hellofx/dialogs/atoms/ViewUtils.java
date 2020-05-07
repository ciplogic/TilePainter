package hellofx.dialogs.atoms;

import hellofx.common.Utilities;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.stream.IntStream;

public class ViewUtils {
    public static ImageView imageViewFromPath(String imageFile) {
        return new ImageView(getImage(imageFile));
    }

    public static Image getImage(String imageFile) {
        return new Image(Utilities.getResource(imageFile));
    }

    public static GridPane gridPaneConstrained(int rows, int columns, int insetSize) {
        var root = gridPaneConstrained(rows, columns);
        root.setPadding(new Insets(insetSize));
        return root;
    }

    public static GridPane gridPaneConstrained(int rows, int columns) {
        var root = new GridPane();
        IntStream.range(0, rows).forEach(row -> {
            var constraint = new RowConstraints();
            constraint.setValignment(VPos.CENTER);
            root.getRowConstraints().add(constraint);
        });

        IntStream.range(0, columns).forEach(column -> {
            var constraint = new ColumnConstraints();
            constraint.setHalignment(HPos.CENTER);
            root.getColumnConstraints().add(constraint);
        });
        return root;
    }
}
