package game.hangman;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Word {
    private ArrayList<Character> gameWord = new ArrayList<>();
    private ArrayList<Character> hiddenWord = new ArrayList<>();
    private LinkedHashMap<Character, Boolean> guesses = new LinkedHashMap<>();
    private static final File FILE_DICTIONARY = new File("src/assets/dictionary.txt");
    private int mistakes = 0;

    /**
     * Create an object so the game can get started.
     */
    Word() {
        setWord();
        mistakes = 0;
    }

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

    String getGameWordString() {
        if (gameWord.size() == 0)
            setWord();

        StringBuilder s = new StringBuilder();
        for (char c : gameWord)
            s.append(c);

        return s.toString();
    }

    void guessLetter(char c) {
        if (!guesses.containsKey(c)) {
            if (gameWord.contains(c))
                guesses.put(c, true);
            else {
                guesses.put(c, false);
                mistakes++;
            }
        }
        System.out.println(guesses.values().toString());
    }

    int getMistakes() {
        return mistakes;
    }


    LinkedHashMap<Character, Boolean> getGuesses() {
        return guesses;
    }

    String getGuessesString() {
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
//                    System.out.println("added: '" + c + "' (" + c + ")");
                    break;
                } else
                    guessed = false;
            }

            if (!guessed) {
                hiddenWord.add('*');
//                System.out.println("added: '*' (" + g + ")");
            }
        }
    }

    /**
     * Time to get a (partially) obfuscated version of the gameWord
     * @return String hiddenWord
     */
    String getHiddenWordString() {
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
        String s = "";
        try {
            Random random = new Random();
            int n = 0;

            for (Scanner input = new Scanner(FILE_DICTIONARY); input.hasNextLine(); ) {
                ++n;
                String line = input.nextLine();

                // skip short words and words containing a hyphen, a bracket, or a space
                if (line.length() <= 4 || line.contains("-") || line.contains("(") || line.contains(" "))
                    continue;

                if (random.nextInt(n) == 0)
                    s = line;
//                System.out.println(s);
            }


        } catch (FileNotFoundException ex) {
            System.out.println("dictionary.txt not found, using backup words");

            String[] word = {"rerouted", "pistache", "subtransverse",
                    "hesiodus", "unbrushable", "wayne", "jebusite",
                    "dutiful", "nonappendant", "araeostyle"};
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
}

