package game.hangman.GUI;

import game.hangman.logic.GameSession;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class GameFields extends VBox implements GuessButton {
    static Text tHiddenWord = new Text(), tGuesses = new Text();
    static TextField tfGuess = new TextField();

    /**
     * Create Hangman interaction fields.
     */
    public GameFields() {
        // Add layout elements
        HBox    wordBox = new HBox(5),
                tryBox = new HBox(5),
                guessBox = new HBox(0);
        Text    lblHiddenWord = new Text("You're guessing: "),
                lblGuesses = new Text("You've tried: ");

        // Style layout elements
        wordBox.setAlignment(Pos.CENTER);
        tryBox.setAlignment(Pos.CENTER);
        guessBox.setAlignment(Pos.CENTER);
        tfGuess.setPrefColumnCount(1);

        wordBox.getChildren().addAll(lblHiddenWord, tHiddenWord);
        tryBox.getChildren().addAll(lblGuesses, tGuesses);
        guessBox.getChildren().addAll(tfGuess, guessButton());

        // Layout Styling
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(40, 0, 0, 0));
        getChildren().addAll(wordBox, tryBox, guessBox);

        // Add some functionality
        tHiddenWord.setText(GameSession.gameWord.getHiddenWordString());
        Platform.runLater(() -> tfGuess.requestFocus());
    }
}
