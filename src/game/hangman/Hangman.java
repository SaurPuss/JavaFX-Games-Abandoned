package game.hangman;

import game.GUI.panes.BottomBarPane;
import game.Game;
import game.GUI.buttons.GameResetButton;
import game.hangman.GUI.GameFields;
import game.hangman.GUI.GameDiagram;
import game.hangman.logic.GameSession;
import game.hangman.logic.GameWord;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import game.GUI.buttons.GameAbandonButton;
import game.GUI.buttons.GameNewButton;
import settings.Session;

public class Hangman extends Game {
    private static GameWord gameWord;
    private GameDiagram hangmanView;
    private GameFields hangmanFields;

    // TODO add difficulty setting (also make that use a different dictionary, maybe as enums?

    public Hangman() {
        // init new game
        gameWord = new GameWord();
        hangmanView = new GameDiagram();
        hangmanFields = new GameFields();

        // Display the parts
        VBox view = new VBox(5);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(0, 50, 0, 50));
        view.getChildren().addAll(hangmanView, hangmanFields);
        getChildren().add(view);

        // Create a bottom Pane for session
        Session.pane.setBottom(new BottomBarPane());

        // Set initial score as a negative of the gameWord length
        GameSession.gameScore -= gameWord.gameWordLength();
        // if letter is guessed correct add it back to the score

        // TODO on win add the full word length to the score before sending it to userScore
    }


    /*@Override
    public Button newGame() {
        Button button = new Button("New Game");

        button.setOnAction(e -> {
            System.out.println("HANGMAN: Do Score stuff here");
            Session.pane.setCenter(new Hangman());
        });

        return button;
    }*/

    /*Hangman(String word) {
        // TODO Make this a thing for multiplayer
        gameWord = new GameWord(word);
        hangmanView = new GameDiagram();
        hangmanFields = new GameFields();
    }*/

    public static GameWord getGameWord() {
        return gameWord;
    }
}
