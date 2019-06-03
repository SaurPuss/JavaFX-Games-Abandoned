package game.hangman.GUI.buttons;

import game.hangman.HangmanGame;
import javafx.scene.control.Button;
import settings.Session;

public interface HangmanResetButton {

    default Button resetHangman() {
        Button resetHangman = new Button("Reset Game");

        resetHangman.setOnAction(e -> {
            // this resets the game, so score should be reflected


            // Set up a new HangmanGamePane
            Session.game = new HangmanGame();
        });

        return resetHangman;
    }
}
