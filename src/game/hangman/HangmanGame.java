package game.hangman;

import game.Game;
import game.hangman.GUI.buttons.HangmanResetButton;
import game.hangman.GUI.panes.HangmanFields;
import game.hangman.GUI.panes.HangmanDiagram;
import game.hangman.logic.HangmanScore;
import game.hangman.logic.HangmanWord;

public class HangmanGame extends Game implements HangmanResetButton {
    private HangmanWord gameWord;
    private HangmanDiagram hangmanView;
    private HangmanFields hangmanFields;
    private int score = 0;


    public HangmanGame() {
        // init new game
        gameWord = new HangmanWord();
        hangmanView = new HangmanDiagram();
        hangmanFields = new HangmanFields();

        // Set initial score as a negative of the gameWord length
        score -= gameWord.gameWordLength();
        // if letter is guessed correct add it back to the score

        // TODO on win add the full word length to the score before sending it to userScore
    }

    HangmanGame(String word) {
        gameWord = new HangmanWord(word);
        hangmanView = new HangmanDiagram();
        hangmanFields = new HangmanFields();
    }
}
