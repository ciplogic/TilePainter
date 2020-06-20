package hellofx.dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DownloadDialog {
    public void BuildView(){
        var stage = new Stage(StageStyle.UTILITY);
        var parent = new BorderPane();

        Scene scene = new Scene(parent);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("h2demo.zip", "EssentialMapPack.zip", "AdditionalMapPack.zip", "MegaMapPack.zip");

        var list = new ListView<String>();
        list.setItems(items);



        var buttonOk = new Button("OK");
        var buttonCancel = new Button("Cancel");
        var buttonBox = new HBox(buttonOk, buttonCancel);


        parent.setCenter(list);
        parent.setBottom(buttonBox);

        stage.setScene(scene);
        stage.showAndWait();
    }
}
