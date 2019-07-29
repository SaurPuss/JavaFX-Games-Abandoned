package game.hangman.logic;

import game.hangman.GUI.GameDiagram;
import game.hangman.Hangman;

import java.io.*;
import java.util.*;

import static game.hangman.logic.GameSession.mistakes;

public class GameWord {
    /* Datafields */
    private ArrayList<Character> gameWord, hiddenWord;
    private LinkedHashMap<Character, Boolean> guesses = new LinkedHashMap<>();

    /* Constructors */
    public GameWord() {
        // pick word from dictionary
        gameWord = new ArrayList<>();
        hiddenWord = new ArrayList<>();
        setWord();
        mistakes = 0;
    }

    public GameWord(String customWord) {
        // pick custom word
        gameWord = new ArrayList<>();
        hiddenWord = new ArrayList<>();
        setWord(customWord);
        mistakes = 0;
    }

    /* Methods */
    /**
     * Word, word, baby!
     * @return gameWord This is the word the whole game is based on. Kinda important.
     */
    ArrayList<Character> getGameWord() {
        // Fallback just in case
        if (gameWord.size() == 0)
            setWord();

        return gameWord;
    }

    Integer gameWordLength() {
        return gameWord.size();
    }

    String getGameWordString() {
        if (gameWord.size() == 0)
            setWord();

        StringBuilder s = new StringBuilder();
        for (char c : gameWord)
            s.append(c);

        return s.toString();
    }

    public void guessLetter(char c) {
        if (!guesses.containsKey(c)) {
            if (gameWord.contains(c))
                guesses.put(c, true);
            else {
                guesses.put(c, false);
                mistakes++;
                GameDiagram.addToDiagram();
            }
        }
    }

    public int getMistakes() {
        return mistakes;
    }

    LinkedHashMap<Character, Boolean> getGuesses() {
        return guesses;
    }

    public String getGuessesString() {
        StringBuilder s = new StringBuilder();
        int i = 0;
        for (char c : guesses.keySet()) {
            if (!guesses.get(c)) {
                s.append(c);
            } else {
                s.append(c);
            }
            i++;
            if (i != guesses.size()) {
                s.append(", ");
            }
        }
        return s.toString();
    }

    private void updateHiddenWord() {
        // Poof!
        hiddenWord.clear();

        // Compare gameWord to guesses and add '*' or the corresponding guess
        boolean guessed = false;
        for (char g : gameWord) {
            for (char c : guesses.keySet()) {
                if (c == g) {
                    hiddenWord.add(c);
                    guessed = true;
                    break;
                } else
                    guessed = false;
            }

            if (!guessed)
                hiddenWord.add('*');
        }
    }

    /**
     * Time to get a (partially) obfuscated version of the gameWord
     * @return String hiddenWord
     */
    public String getHiddenWordString() {
        updateHiddenWord();

        StringBuilder s = new StringBuilder();
        for (char c : hiddenWord)
            s.append(c);

        return s.toString();
    }

    ArrayList<Character> getHiddenWord() {
        updateHiddenWord();

        return hiddenWord;
    }

    /**
     * On the 8th day, SaurPuss got a word from a dictionary and saw that it
     * was good.
     * On the 9th day SaurPuss remembered the word should be stored in a
     * Character Array, so mote it be.
     */
    private void setWord() {
        File dictionary;
        switch(Hangman.getGameDifficulty()) {
            case EASY   : dictionary = GameSession.DICTIONARY_EASY; break;
            case HARD   : dictionary = GameSession.DICTIONARY_HARD; break;
            case NORMAL :
            default     : dictionary = GameSession.DICTIONARY_NORMAL;
        }

        System.out.println("GAME WORD: PLS DO DICTIONARY STUFF");
        String s = "";
        try {
            Random random = new Random();
            int n = 0;
            for (Scanner input = new Scanner(dictionary); input.hasNextLine(); ) {
                ++n;
                String line = input.nextLine();

                // skip short words and words containing a hyphen, a bracket, or a space
                if (line.length() <= 4 || line.contains("-") || line.contains("(") || line.contains(" "))
                    continue;

                if (random.nextInt(n) == 0)
                    s = line;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("HANGMAN WORD: dictionary.txt not found, using backup words");
            String[] word = {"pancake", "banana", "strawberry",
                    "blueberry", "spaghetti", "tomato", "orange",
                    "apple", "coconut", "cucumber"};
            int n = (int) (Math.random() * word.length);
            s = word[n];
        } finally {
            ArrayList<Character> chars = new ArrayList<>();
            for (char c : s.toCharArray()) {
                chars.add(c);
            }
            gameWord = chars;
        }
    }


    /**
     * Create a custom gameword for multiplayer hangman
     * @param gameWord String with the word to be guessed
     */
    private void setWord(String gameWord) {
        ArrayList<Character> chars = new ArrayList<>();
        for (char c : gameWord.toCharArray()) {
            chars.add(c);
        }
        this.gameWord = chars;
    }
}
