package hellofx.framework.controls;

import hellofx.framework.MainContext;
import hellofx.framework.ObjectNames;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainStackPane {
    public MainContext context;
    public StackPane stackPane = new StackPane();
    public Object dialogValue;

    public void setup() {
        context.setObj(ObjectNames.mainStack, this);
    }

    public void push(Node node) {
        stackPane.getChildren().add(node);
    }

    void destroyTop() {
        var children = stackPane.getChildren();
        var node = children.get(children.size() - 1);
        children.remove(node);
    }

    public void push(Node node, Pos align) {
        StackPane.setAlignment(node, align);
        stackPane.getChildren().add(node);
    }
}
