package settings.GUI.panes;

import game.Game;
import game.hangman.Hangman;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import settings.Session;

import java.util.Set;
import java.util.TreeSet;

public class GameSelectionPane extends VBox {
    private ComboBox<String> dropdown = new ComboBox<>(fillDropdown());

    public GameSelectionPane() {
        // Printing Session user
        Session.printCurrentUser();


        Text intro = new Text("This is where stuff goes to choose a game");

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(intro, dropdown);
    }

    private ObservableList<String> fillDropdown() {
        // Array of Games available to play
        return FXCollections.observableArrayList(
                "Hangman",
                "New Game"
        );
    }


    public static Game startGame(int n) {
        switch(n) {
            default: return new Hangman();

        }
    }
}
