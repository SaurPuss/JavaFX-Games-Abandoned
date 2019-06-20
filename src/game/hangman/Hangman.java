package game.hangman;

import game.Game;
import game.hangman.GUI.GameResetButton;
import game.hangman.GUI.GameFields;
import game.hangman.GUI.GameDiagram;
import game.hangman.logic.GameSession;
import game.hangman.logic.GameWord;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class Hangman extends Game implements GameResetButton {
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

        // Set initial score as a negative of the gameWord length
        GameSession.gameScore -= gameWord.gameWordLength();
        // if letter is guessed correct add it back to the score

        // TODO on win add the full word length to the score before sending it to userScore
    }

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
