package settings.user.score;

import settings.AppSettings;
import java.io.*;

/**
 * User saves scores in here
 */
public class UserScore implements Serializable {
    private static final long serialVersionUID = AppSettings.SERIAL_VERSION_UID;
    private int totalScore;
    private int highestStreak;
    private int currentStreak;

    /**
     * Default User Scores, everyone starts with this
     */
    public UserScore() {
        totalScore = 0;
        highestStreak = 0;
        currentStreak = 0;
    }

    public UserScore(int totalScore, int highestStreak) {
        this.totalScore = totalScore;
        this.highestStreak = highestStreak;
        currentStreak = 0;
    }

    public UserScore(int totalScore, int highestStreak, int currentStreak) {
        this.totalScore = totalScore;
        this.highestStreak = highestStreak;
        this.currentStreak = currentStreak;
    }

    /* Getters and Setters */
    public int getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    public int getHighestStreak() {
        return highestStreak;
    }
    public void setHighestStreak(int highestStreak) {
        this.highestStreak = highestStreak;
    }
    public int getCurrentStreak() {
        return currentStreak;
    }
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    /**
     * Run through the scores and add or subtract the totals.
     * @param gameScore update all scores with the new numbers gotten from the Game
     */
    public void updateScores(int gameScore) {
        updateTotalScore(gameScore);
        updateCurrentScore(gameScore);
        updateHighestStreak();
    }

    private void updateTotalScore(int gameScore) {
        this.totalScore += gameScore;
    }
    private void updateHighestStreak() { if (currentStreak > highestStreak) highestStreak = currentStreak; }
    private void updateCurrentScore(int gameScore) {
        this.currentStreak += gameScore;
    }
    public void resetCurrentScore() {
        currentStreak = 0;
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
