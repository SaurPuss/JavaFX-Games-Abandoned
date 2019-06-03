package game.hangman.GUI.buttons;

import javafx.scene.control.Button;

public interface HangmanGuessButton {

    default Button guessButton() {



        return new Button();
    }
}
