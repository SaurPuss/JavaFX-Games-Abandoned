package game.hangman.GUI.panes;

import game.hangman.GUI.buttons.HangmanGuessButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class HangmanFields extends GridPane implements HangmanGuessButton {
    private Text tHiddenWord = new Text();
    private Text tGuesses = new Text();
    private TextField tfGuess = new TextField();

    public HangmanFields() {



        /*setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(40, 10, 20, 10));
        setSpacing(10);

        HBox submitFields = new HBox();
        submitFields.getChildren().addAll(tfGuess, btnGuess);

        Label lblWord = new Label("You're guessing: ", tHiddenWord);
        lblWord.setContentDisplay(ContentDisplay.RIGHT);

        Label lblGuessed = new Label("You've tried: ", tGuesses);
        lblGuessed.setContentDisplay(ContentDisplay.RIGHT);

        Label lblGuess = new Label("Guess: ", submitFields);
        lblGuess.setContentDisplay(ContentDisplay.RIGHT);

        tfGuess.setPrefColumnCount(2);


        getChildren().addAll(lblWord, tHiddenWord, lblGuessed, tGuesses, lblGuess, submitFields, btnRestart);*/
    }

    void setHiddenWord(String s) {
        tHiddenWord.setText(s);
    }

    void setGuesses(String s) {
        tGuesses.setText(s);
    }



}
