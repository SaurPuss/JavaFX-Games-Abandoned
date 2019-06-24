package game.GUI.buttons;

import game.hangman.Hangman;
import javafx.scene.control.Button;
import settings.Session;

public interface GameResetButton {

    default Button resetGame() {
        Button button = new Button("Reset Game");

        button.setOnAction(e -> {
            // TODO make this so it can find the current game and get a new instance of it
            // this resets the game, so score should be reflected


            // Set up a new HangmanGamePane
            Session.game = new Hangman();
            Session.pane.setCenter(Session.game);
        });

        return button;
    }
}
