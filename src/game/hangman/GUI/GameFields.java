package game.hangman.GUI;

import game.hangman.Hangman;
import game.hangman.logic.GameWord;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class GameFields extends HBox implements GuessButton {
    static Text tHiddenWord = new Text();
    static Text tGuesses = new Text();
    static TextField tfGuess = new TextField();

    public GameFields() {
        setAlignment(Pos.TOP_CENTER);

        GridPane fields = new GridPane();
        Text lblHiddenWord = new Text("You're guessing: ");
        Text lblGuesses = new Text("You've tried: ");

        fields.add(lblHiddenWord, 0, 0);
        fields.add(tHiddenWord, 1, 0);
        fields.add(lblGuesses, 0, 1);
        fields.add(tGuesses,1, 1);
        fields.add(tfGuess, 0, 2);
        fields.add(guessButton(), 1, 2);

        getChildren().addAll(fields);
    }

    void setHiddenWord(String s) {
        tHiddenWord.setText(s);
    }

    void setGuesses(String s) {
        tGuesses.setText(s);
    }




}
