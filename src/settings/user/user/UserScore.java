package settings.user.user;

import org.apache.commons.lang3.ObjectUtils;
import settings.user.settings.GameDifficulty;

import java.io.*;

import static settings.AppSettings.*;

/**
 * User saves scores in here
 */
public class UserScore implements Serializable {
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    /* Object Data Fields */
    private int total;
    private int streak;
    private String streakGame;
    private GameDifficulty streakDifficulty;
    private int[] hangman , mastermind, minesweeper, snake;

    /* Constructors */
    /**
     * Default Constructor
     */
    public UserScore() {
        total = 0; // cumulative score across all games and difficulties
        streak = 0; // current streak save, reset to 0 on game switch or lose
        streakGame = ""; // indicator which game the streak belongs to
        streakDifficulty = GameDifficulty.NORMAL; // indicator for current game difficulty

        // Individual high scores for each game (difficulty) {EASY, NORMAL, HARD}
        hangman = new int[] {0, 0, 0};
        mastermind = new int[] {0, 0, 0};
        minesweeper = new int[] {0, 0, 0};
        snake = new int[] {0, 0, 0};
    }

    /**
     *
     * @param total
     * @param streak
     * @param streakDifficulty
     * @param hangman
     * @param mastermind
     * @param minesweeper
     * @param snake
     */
    public UserScore(int total, int streak, String streakGame, GameDifficulty streakDifficulty,
                     int[] hangman, int[] mastermind, int[] minesweeper, int[] snake) {
        this.total = total;
        this.streak = streak;
        this.streakGame = streakGame;
        this.streakDifficulty = streakDifficulty;
        this.hangman = hangman;
        this.mastermind = mastermind;
        this.minesweeper = minesweeper;
        this.snake = snake;
    }

    /* Getters and Setters */
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    public int getStreak() { return streak; }
    public void setStreak(int streak) { this.streak = streak; }
    public String getStreakGame() { return streakGame; }
    public void setStreakGame(String streakGame) { this.streakGame = streakGame; }
    public GameDifficulty getStreakDifficulty() { return streakDifficulty; }
    public void setStreakDifficulty(GameDifficulty streakDifficulty) { this.streakDifficulty = streakDifficulty; }
    public void setStreakDifficulty(String streakDifficulty) { this.streakDifficulty = GameDifficulty.fromString(streakDifficulty); }
    public int[] getHangman() { return hangman; }
    public void setHangman(int[] hangman) { this.hangman = hangman; }
    public int[] getMastermind() { return mastermind; }
    public void setMastermind(int[] mastermind) { this.mastermind = mastermind; }
    public int[] getMinesweeper() { return minesweeper; }
    public void setMinesweeper(int[] minesweeper) { this.minesweeper = minesweeper; }
    public int[] getSnake() { return snake; }
    public void setSnake(int[] snake) { this.snake = snake; }

    /**
     * Run through the scores and add or subtract the totals.
     */
    public void breakStreak() {
        // TODO figure this out
    }

    private void updateScore(String game, GameDifficulty gameDifficulty, int score) {
        int difficulty = 0; // default = GameDifficulty.EASY
        switch (gameDifficulty) {
            case NORMAL : difficulty = 1; break;
            case HARD   : difficulty = 2;
        }

        switch(game.toLowerCase()) {
            case "hangman"      : hangman[difficulty] = score; break;
            case "mastermind"   : mastermind[difficulty] = score; break;
            case "minesweeper"  : minesweeper[difficulty] = score; break;
            case "snake"        : snake[difficulty] = score;
        }

        // TODO update in database from here?
    }

    public void updateScore(String game, String difficulty, int score) {
        switch(difficulty.toLowerCase()) {
            case "easy"     :
            case "normal"   :
            case "hard"     : updateScore(game,
                    GameDifficulty.fromString(difficulty), score); break;
            default : updateScore(game, this.streakDifficulty, score);
        }
    }

    /**
     * Implement Serializable Interface methods
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("USER SCORE: no Object data");
    }
}
