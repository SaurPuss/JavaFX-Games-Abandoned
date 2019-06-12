package game.hangman.GUI;

import game.hangman.Hangman;
import javafx.scene.control.Button;
import settings.Session;

public interface GameResetButton {

    default Button resetHangman() {
        Button resetHangman = new Button("Reset Game");

        resetHangman.setOnAction(e -> {
            // this resets the game, so score should be reflected


            // Set up a new HangmanGamePane
            Session.game = new Hangman();
        });

        return resetHangman;
    }
}
