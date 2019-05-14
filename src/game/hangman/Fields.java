package game.hangman;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

class Fields extends VBox {
    Text tHiddenWord = new Text();
    private Text tGuesses = new Text();
    TextField tfGuess = new TextField();
    Button btnGuess = new Button("Submit");
    Button btnRestart = new Button("New Game");

    Fields() {
        setAlignment(Pos.TOP_CENTER);
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


        getChildren().addAll(lblWord, tHiddenWord, lblGuessed, tGuesses, lblGuess, submitFields, btnRestart);
    }

    void setHiddenWord(String s) {
        tHiddenWord.setText(s);
    }

    void setGuesses(String s) {
        tGuesses.setText(s);
    }


    /**
     * Disable input except for a restart button that will start a new
     * Hangman Game.
     */
    void finishedGame() {
        tfGuess.setEditable(false);
        btnGuess.setDisable(true);
        getChildren().addAll(btnRestart);
    }
}
