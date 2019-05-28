package settings.GUI.panes;

import game.Game;
import game.hangman.Hangman;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameSelectionPane extends VBox {

    public GameSelectionPane() {
        Text intro = new Text("This is where stuff goes to choose a game");

        this.getChildren().addAll(intro);
    }


    public static Game startGame(int n) {
        switch(n) {
            default: return new Hangman();

        }
    }
}
