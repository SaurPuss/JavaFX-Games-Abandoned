package game.hangman.GUI;

import game.hangman.Hangman;
import game.hangman.logic.GameSession;
import javafx.scene.control.Button;

import static game.hangman.GUI.GameFields.*;

public interface GuessButton {

    default Button guessButton() {
        Button button = new Button("Guess");
        button.setDefaultButton(true);

        button.setOnAction(e -> {
            guessLetterInput();
            GameDiagram.addToDiagram();
            if (GameSession.mistakes >= 7) {
                tfGuess.setText("");
                button.setDisable(true);
                tfGuess.setEditable(false);
            }
        });

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
