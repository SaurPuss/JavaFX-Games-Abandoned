package settings.user.user;

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
    private int[] hangman, mastermind, minesweeper, snake;

    /* Constructors */
    /**
     * Default Constructor
     */
    UserScore() {
        total               = 0; // cumulative score across all games and difficulties
        streak              = 0; // current streak save, reset to 0 on game switch or lose
        streakGame          = ""; // indicator which game the streak belongs to
        streakDifficulty    = GameDifficulty.NORMAL; // indicator for current game difficulty

        // Individual high scores for each game (difficulty) {EASY, NORMAL, HARD}
        hangman     = new int[] {0, 0, 0};
        mastermind  = new int[] {0, 0, 0};
        minesweeper = new int[] {0, 0, 0};
        snake       = new int[] {0, 0, 0};
    }

    /**
     * Constructor for use by the Database Manager I guess.
     * @param total int
     * @param streak int
     * @param streakGame string
     * @param streakDifficulty game difficulty
     * @param hangman int[3] easy, normal, hard
     * @param mastermind int[3] easy, normal, hard
     * @param minesweeper int[3] easy, normal, hard
     * @param snake int[3] easy, normal, hard
     */
    public UserScore(int total, int streak, String streakGame,
                     GameDifficulty streakDifficulty, int[] hangman,
                     int[] mastermind, int[] minesweeper, int[] snake) {
        this.total              = total;
        this.streak             = streak;
        this.streakGame         = streakGame;
        this.streakDifficulty   = streakDifficulty;
        this.hangman            = hangman;
        this.mastermind         = mastermind;
        this.minesweeper        = minesweeper;
        this.snake              = snake;
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
        // TODO Do I need this?
        System.out.println("USER SCORE: Streak broken, reset to 0");
        this.streak = 0;
    }

    public void updateScore(String game, String difficulty, int score) {
        switch(difficulty.toLowerCase()) {
            case "easy"     :
            case "normal"   :
            case "hard"     : updateScore(game, GameDifficulty.fromString(difficulty),
                    score); break;
            default         : updateScore(game, this.streakDifficulty, score);
        }
    }

    private void updateScore(String game, GameDifficulty gameDifficulty, int score) {
        // TODO check if it's higher than the current saved score?
        int difficulty = 1; // default = GameDifficulty.NORMAL
        switch (gameDifficulty) {
            case EASY   : difficulty = 0; break;
            case HARD   : difficulty = 2;
        }

        switch(game.toLowerCase()) {
            case "hangman"      : hangman[difficulty] = score; break;
            case "mastermind"   : mastermind[difficulty] = score; break;
            case "minesweeper"  : minesweeper[difficulty] = score; break;
            case "snake"        : snake[difficulty] = score;
        }

        // TODO update in database from here?
        // TODO Can I get the user ID in here to make this update work?
    }

    @Override
    public String toString() {
        return    "Total Score: " + this.total
                + "\nCurrent Streak: " + this.streak
                + "\nStreak Game: " + this.streakGame
                + "\nStreak Difficulty: " + this.streakDifficulty.toString()
                + "\nHangman:     " + hangman[0] + " | " + hangman[1] + " | " + hangman[2]
                + "\nMasterMind:  " + mastermind[0] + " | " + mastermind[1] + " | " + mastermind[2]
                + "\nMineSweeper: " + minesweeper[0] + " | " + minesweeper[1] + " | " + minesweeper[2]
                + "\nSnake:       " + snake[0] + " | " + snake[1] + " | " + snake[2];
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
