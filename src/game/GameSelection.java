package game;

import game.hangman.Hangman;
import javafx.scene.layout.VBox;

public class GameSelection extends VBox {



    public static Game startGame(int n) {
        switch(n) {
            default: return new Hangman();

        }
    }
}
