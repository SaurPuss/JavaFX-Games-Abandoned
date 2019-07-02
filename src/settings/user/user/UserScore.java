package settings.user.user;

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
    private int hangman;
    private int hangmanTotal;
    private int minesweeper;
    private int minesweeperTotal;

    /* Constructors */
    /**
     * Default Constructor
     */
    public UserScore() {
        // Cumulative Scores
        total = 0;
        streak = 0;

        // Session Streak Scores
        // TODO add for game difficulties
        hangman = 0;
        minesweeper = 0;

        // Total Scores
        hangmanTotal = 0;
        minesweeperTotal = 0;
    }

    /**
     * UserScore preferably restored from Database or currentUserSave
     * @param total
     * @param streak
     * @param hangman
     * @param minesweeper
     * @param hangmanTotal
     * @param minesweeper
     * @param minesweeperTotal
     */
    public UserScore(int total, int streak, int hangman, int minesweeper, int hangmanTotal, int minesweeperTotal) {
        // Session Streak Scores
        this.streak = streak;
        this.hangman = hangman;
        this.minesweeper = minesweeper;

        // Cumulative Scores
        this.total = total;
        this.hangmanTotal = hangmanTotal;
        this.minesweeperTotal = minesweeperTotal;
    }

    /* Getters and Setters */
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    public int getStreak() { return streak; }
    public void setStreak(int streak) { this.streak = streak; }
    public int getHangman() { return hangman; }
    public void setHangman(int hangman) { this.hangman = hangman; }
    public int getHangmanTotal() { return hangmanTotal; }
    public void setHangmanTotal(int hangmanTotal) { this.hangmanTotal = hangmanTotal; }
    public int getMinesweeper() { return minesweeper; }
    public void setMinesweeper(int minesweeper) { this.minesweeper = minesweeper; }
    public int getMinesweeperTotal() { return minesweeperTotal; }
    public void setMinesweeperTotal(int minesweeperTotal) { this.minesweeperTotal = minesweeperTotal; }

    /**
     * Run through the scores and add or subtract the totals.
     */
    public void breakStreak() {
        // TODO figure this out
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
