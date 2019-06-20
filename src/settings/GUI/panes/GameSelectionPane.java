package settings.GUI.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import settings.GUI.buttons.GameSelectionButton;

import java.util.ArrayList;

public class GameSelectionPane extends VBox implements GameSelectionButton {

    public GameSelectionPane() {
        HBox box = new HBox(0);
        box.setAlignment(Pos.CENTER);

        ComboBox<String> dropdown = new ComboBox<>();
        dropdown.getItems().addAll(gameSelectionList());
        dropdown.getSelectionModel().selectFirst();

        Button button = gameSelectionButton(dropdown.getSelectionModel().getSelectedItem());
        Text intro = new Text("This is where stuff goes to choose a game");

        box.getChildren().addAll(dropdown, button);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(intro, box);
    }


    /**
     * Add all the games in the program to this list.
     * @return ArrayList of hard coded game names
     */
    private ArrayList<String> gameSelectionList() {
        ArrayList<String> gameList = new ArrayList<>();

        gameList.add("Hangman");
        gameList.add("Minesweeper");

        return gameList;
    }
}
