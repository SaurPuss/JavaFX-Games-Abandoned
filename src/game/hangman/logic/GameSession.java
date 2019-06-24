package game.hangman.logic;

import java.io.File;

public class GameSession {
    static final File HANGMAN_DICTIONARY = new File("src/game/hangman/assets/dictionary.txt");
    public static int gameScore = 0;
    // TODO Normal/Hard mode, in hard mode mistakes are subtracted as points too and longer words
//    public boolean hardMode = false;

}
