package game.hangman;

import game.Game;
import game.hangman.GUI.buttons.HangmanResetButton;
import game.hangman.GUI.panes.HangmanFields;
import game.hangman.GUI.panes.HangmanDiagram;
import game.hangman.logic.HangmanWord;

public class HangmanGame extends Game implements HangmanResetButton {

    private HangmanWord gameWord;
    private HangmanDiagram hangmanView;
    private HangmanFields hangmanFields;


    public HangmanGame() {
        // init new game
        gameWord = new HangmanWord();
        hangmanView = new HangmanDiagram();
        hangmanFields = new HangmanFields();
    }

    HangmanGame(String gameWord) {
        this.gameWord = new HangmanWord(gameWord);
        hangmanView = new HangmanDiagram();
        hangmanFields = new HangmanFields();
    }
}
