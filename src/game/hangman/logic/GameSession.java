package game.hangman.logic;

import java.io.File;
import java.util.*;

public class GameSession {
    static final File DICTIONARY_HARD = new File("src/game/hangman/assets/dictionary.txt");
    static final File DICTIONARY_EASY = new File("src/game/hangman/assets/dictionaryEASY.txt");
    // TODO replace normal dictionary
    static final File DICTIONARY_NORMAL = new File("src/game/hangman/assets/dictionaryEASY.txt");
    
    public static int mistakes = 0;
    public static GameWord gameWord;

    static int getWrongGuesses() {
        LinkedHashMap<Character, Boolean> map = gameWord.getGuesses();
        int wrong = 0;

        for (Map.Entry<Character, Boolean> pair : map.entrySet()) {
            if (!pair.getValue())
                wrong++;
        }

        return wrong;
    }

    static int getCorrectGuesses() {
        LinkedHashMap<Character, Boolean> map = gameWord.getGuesses();
        int correct = 0;

        for (Map.Entry<Character, Boolean> pair : map.entrySet()) {
            if (pair.getValue())
                correct++;
        }

        return correct;
    }

    static int getGameScore() { return gameWord.gameWordLength(); }

    public static String getGameWord() { return gameWord.toString(); }
}
