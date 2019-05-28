package settings.user.score;

import java.io.*;

/**
 * User saves scores in here
 */
public class UserScore implements Serializable {
    private static final long serialVersionUID = 365729905314659904L;
    // Data fields
    private int totalScore;
    private int currentScore;
    private int highestStreak;

    /**
     * Default User Scores, everyone starts with this
     */
    public UserScore() {
        totalScore = 0;
        currentScore = 0;
        highestStreak = 0;
    }

    /* Getters and Setters
    * Setters are private, with the exception of User retrieval
    * from the database
    */
    public int getTotalScore() { return totalScore; }
    private void setTotalScore(int gameScore) {
        this.totalScore += gameScore;
    }
    public void setSavedTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    public int getHighestStreak() { return highestStreak; }
    private void setHighestStreak() {
        if (currentScore > highestStreak)
            highestStreak = currentScore;
    }
    public void setSavedHighestStreak(int highestStreak) {
        this.highestStreak = highestStreak;
    }
    public int getCurrentScore() { return currentScore; }
    private void setCurrentScore(int gameScore) { this.currentScore += gameScore; }
    public void resetCurrentScore() { currentScore = 0; }


    /**
     * Run through the scores and add or subtract the totals.
     * @param gameScore update all scores with the new numbers gotten from the Game
     */
    public void updateScores(int gameScore) {
        setTotalScore(gameScore);
        setCurrentScore(gameScore);
        setHighestStreak();
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
        System.out.println("no Object data");
    }
}
