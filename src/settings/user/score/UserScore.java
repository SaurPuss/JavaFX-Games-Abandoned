package settings.user.score;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import settings.Session;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * User saves scores in here
 */
public class UserScore /*extends AbstractBeanField*/ implements Serializable {
    private static final long serialVersionUID = Session.SERIAL_VERSION_UID;

    // Data fields
    @CsvBindByName(column = "totalScore")
    private int totalScore;
    @CsvBindByName(column = "currentScore")
    private int currentScore;
    @CsvBindByName(column = "highestStreak")
    private int highestStreak;


    /**
     * Default User Scores, everyone starts with this
     */
    public UserScore() {
        totalScore = 0;
        currentScore = 0;
        highestStreak = 0;
    }

    public UserScore(int totalScore, int currentScore, int highestStreak) {
        this.totalScore = totalScore;
        this.currentScore = currentScore;
        this.highestStreak = highestStreak;
    }

    /* Getters and Setters */
    public int getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    private void updateTotalScore(int gameScore) {
        this.totalScore += gameScore;
    }

    public int getHighestStreak() {
        return highestStreak;
    }
    public void setHighestStreak(int highestStreak) {
        this.highestStreak = highestStreak;
    }
    private void updateHighestStreak() {
        if (currentScore > highestStreak)
            highestStreak = currentScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }
    private void updateCurrentScore(int gameScore) {
        this.currentScore += gameScore;
    }
    public void resetCurrentScore() {
        currentScore = 0;
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

    @Override
    public String toString() {
        return String.valueOf(totalScore) + "." + String.valueOf(currentScore) + "." + String.valueOf(highestStreak);
    }

    /*@Override
    protected Object convert(String s) {
        System.out.println("USER SCORE: " + s);
        UserScore score = new UserScore();
        String[] split = s.split(".", 3);
        score.setTotalScore(Integer.valueOf(split[0]));
        score.setCurrentScore(Integer.valueOf(split[1]));
        score.setHighestStreak(Integer.valueOf(split[2]));
        return score;
//        return null;
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        UserScore score = (UserScore) value;

        return "\"" + score.getTotalScore() + "." + score.getCurrentScore() + "." + score.getHighestStreak() + "\"";
//        return super.convertToWrite(value);
    }*/
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
