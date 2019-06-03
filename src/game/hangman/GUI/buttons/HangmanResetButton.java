package game.hangman.GUI.buttons;

import javafx.scene.control.Button;

public interface HangmanResetButton {

    default Button resetHangman() {
        // this resets the game, so score should be reflected


        return new Button();
    }
}
