package game.hangman.GUI;

import javafx.scene.control.Button;

public interface GuessButton {

    default Button guessButton() {



        return new Button();
    }
}
