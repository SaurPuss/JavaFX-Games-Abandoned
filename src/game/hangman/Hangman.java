package game.hangman;

import game.Game;
import game.hangman.GUI.GameResetButton;
import game.hangman.GUI.GameFields;
import game.hangman.GUI.GameDiagram;
import game.hangman.logic.GameSession;
import game.hangman.logic.GameWord;

public class Hangman extends Game implements GameResetButton {
    private static GameWord gameWord;
    private GameDiagram hangmanView;
    private GameFields hangmanFields;

    public Hangman() {
        // init new game
        gameWord = new GameWord();
        hangmanView = new GameDiagram();
        hangmanFields = new GameFields();

        getChildren().addAll(hangmanView, hangmanFields);

        // Set initial score as a negative of the gameWord length
        GameSession.gameScore -= gameWord.gameWordLength();
        // if letter is guessed correct add it back to the score

        // TODO on win add the full word length to the score before sending it to userScore
    }

    Hangman(String word) {
        gameWord = new GameWord(word);
        hangmanView = new GameDiagram();
        hangmanFields = new GameFields();
    }

    public static GameWord getGameWord() {
        return gameWord;
    }
}
