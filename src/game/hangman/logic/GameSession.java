package game.hangman.logic;

import java.io.File;

public class GameSession {
    static final File HANGMAN_DICTIONARY = new File("src/game/hangman/assets/dictionary.txt");
    public static int mistakes = 0;
    public static GameWord gameWord;


    public static String getGameWord() { return gameWord.toString(); }
}
