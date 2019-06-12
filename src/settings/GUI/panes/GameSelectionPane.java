package settings.GUI.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import settings.GUI.buttons.GameSelectionButton;
import settings.GUI.other.GameSelectionList;

public class GameSelectionPane extends VBox implements GameSelectionList, GameSelectionButton {
    private ComboBox<String> dropdown = new ComboBox<>();

    public GameSelectionPane() {
        dropdown.getItems().addAll(gameSelectionList());
        dropdown.getSelectionModel().selectFirst();


        Button button = gameSelectionButton(dropdown.getSelectionModel().getSelectedItem());


        Text intro = new Text("This is where stuff goes to choose a game");

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(intro, dropdown, button);
    }
}
