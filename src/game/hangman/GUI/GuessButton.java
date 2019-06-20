package game.hangman.GUI;

import game.hangman.Hangman;
import javafx.scene.control.Button;

import static game.hangman.GUI.GameFields.*;

public interface GuessButton {

    default Button guessButton() {
        Button button = new Button("Guess");
        button.setDefaultButton(true);
        // TODO a lot more functionality
        button.setOnAction(e -> {
            guessLetterInput();
            GameDiagram.addToDiagram(Hangman.getGameWord().getMistakes());
            if (Hangman.getGameWord().getMistakes() >= 7) {
                tfGuess.setText("");
                button.setDisable(true);
                tfGuess.setEditable(false);
            }
        });

        button.setDisable(false);
        return button;
    }

    default void guessLetterInput() {
        if (!tfGuess.getText().equals("")) {
            if (tfGuess.getText().charAt(0) == ' ')
                tfGuess.setText("");
            else {
                Hangman.getGameWord().guessLetter(tfGuess.getText().charAt(0));
                tHiddenWord.setText(Hangman.getGameWord().getHiddenWordString());
                tGuesses.setText(Hangman.getGameWord().getGuessesString());
                tfGuess.setText("");
            }
        }
    }
}
