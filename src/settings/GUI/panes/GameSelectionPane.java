package settings.GUI.panes;

import game.Game;
import game.hangman.Hangman;
import javafx.scene.layout.VBox;

public class GameSelectionPane extends VBox {



    public static Game startGame(int n) {
        switch(n) {
            default: return new Hangman();

        }
    }
}
