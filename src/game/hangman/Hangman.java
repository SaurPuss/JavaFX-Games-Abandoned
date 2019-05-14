package game.hangman;

import game.Game;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

/**
 * You chose to play Hangman
 */
public class Hangman extends Game {
    private Word word;
    private Dude dude;
    private Fields fields;

    /**
     * Time to construct a new Game of Hangman /o/
     */
    public Hangman() {

        //TODO make sure the fields etc reset when a new game is initialized

        // Basic set up for containers
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);

        // Give it something to make it functional
        word = new Word();

        // Container contents
        dude = new Dude();
        fields = new Fields();

        fields.setHiddenWord(word.getHiddenWordString());
        fields.setGuesses(word.getGuessesString());
        System.out.println("gameWord: " + word.getGameWordString());

        // Make the buttons do stuff
        fields.tfGuess.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                guessLetterInput();
                dude.addToDude(word.getMistakes());
                endGame();
            }
        });
        fields.btnGuess.setOnAction(e -> {
            guessLetterInput();
            dude.addToDude(word.getMistakes());
            endGame();
        });
        fields.btnRestart.setOnAction(e -> restartGame());

        // Put it all together
        container.getChildren().addAll(dude, fields);
        getChildren().addAll(container);
    }

    private void guessLetterInput() {
        if (!fields.tfGuess.getText().equals("")) {
            if (fields.tfGuess.getText().charAt(0) == ' ')
                fields.tfGuess.setText("");
            else {
                word.guessLetter(fields.tfGuess.getText().charAt(0));
                fields.setHiddenWord(word.getHiddenWordString());
                fields.setGuesses(word.getGuessesString());
                fields.tfGuess.setText("");
            }
        }
    }

    private void endGame() {
        if (word.getMistakes() == 7) {
            // Maxed out mistakes, disable fields and end game
            fields.tfGuess.setDisable(true);
            fields.btnGuess.setDisable(true);

            // display restart button
        } else if (word.getHiddenWord().equals(word.getGameWord())) {
            // We have a winner
            fields.tfGuess.setDisable(true);
            fields.btnGuess.setDisable(true);
            dude.addToDude(0); // remove the dude
            // display restart button
        }
    }

    public void restartGame() {
        dude.newDude();
        word = new Word();

        fields.tfGuess.setDisable(false);
        fields.btnGuess.setDisable(false);
        fields.setHiddenWord(word.getHiddenWordString());
        fields.setGuesses(word.getGuessesString());

        System.out.println("gameWord: " + word.getGameWordString());
    }
}
