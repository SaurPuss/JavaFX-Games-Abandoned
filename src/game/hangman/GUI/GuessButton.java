package game.hangman.GUI;

import game.hangman.Hangman;
import game.hangman.logic.GameWord;
import javafx.scene.control.Button;

import static game.hangman.GUI.GameFields.tfGuess;

public interface GuessButton {

    default Button guessButton() {
        Button button = new Button("Guess");

        // TODO a lot more functionality
        button.setOnAction(e -> {
            guessLetterInput();
            GameDiagram.addToDiagram(Hangman.getGameWord().getMistakes());
//            endGame();
        });



        return button;
    }

    default void guessLetterInput() {
        if (!tfGuess.getText().equals("")) {
            if (tfGuess.getText().charAt(0) == ' ')
                tfGuess.setText("");
            else {
//                Hangman.getGameWord().guessLetter(tfGuess.getText().charAt(0));
//                fields.setHiddenWord(word.getHiddenWordString());
//                fields.setGuesses(word.getGuessesString());
//                fields.tfGuess.setText("");
            }
        }
    }
}
