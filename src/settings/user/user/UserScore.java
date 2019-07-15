package settings.user.user;

import settings.user.settings.GameDifficulty;

import java.io.*;
import java.util.HashMap;

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
        streakDifficulty = GameDifficulty.fromString("Normal"); // indicator for current game difficulty

        // Individual high scores for each game (difficulty) {EASY, NORMAL, HARD}
        hangman = new int[] {0, 0, 0};
        mastermind = new int[] {0, 0, 0};
        minesweeper = new int[] {0, 0, 0};
        snake = new int[] {0, 0, 0};
    }

    /**
     * UserScore preferably restored from Database or currentUserSave
     * @param total
     * @param streak
     */
    public UserScore(int total, int streak) {
        this.total = total;
        this.streak = streak;
    }

    /* Getters and Setters */
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    public int getStreak() { return streak; }
    public void setStreak(int streak) { this.streak = streak; }

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
