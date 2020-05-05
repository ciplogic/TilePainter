package hellofx.framework.controls;

import hellofx.framework.MainContext;
import hellofx.framework.ObjectNames;
import javafx.scene.layout.BorderPane;

public class MainBorderPane {
    public MainContext context;
    public BorderPane borderPane = new BorderPane();

    public void setup() {
        context.setObj(ObjectNames.mainBorderPane, this);
    }
}
