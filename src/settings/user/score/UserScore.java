package settings.user.score;

import com.opencsv.bean.CsvBindByName;
import settings.Session;

import java.io.*;

/**
 * User saves scores in here
 */
public class UserScore /*extends AbstractBeanField*/ implements Serializable {
    private static final long serialVersionUID = Session.SERIAL_VERSION_UID;

    // Data fields
    @CsvBindByName(column = "totalScore")
    private int totalScore;
    @CsvBindByName(column = "currentStreak")
    private int currentStreak;
    @CsvBindByName(column = "highestStreak")
    private int highestStreak;


    /**
     * Default User Scores, everyone starts with this
     */
    public UserScore() {
        totalScore = 0;
        currentStreak = 0;
        highestStreak = 0;
    }

    public UserScore(int totalScore, int currentScore, int highestStreak) {
        this.totalScore = totalScore;
        this.currentStreak = currentScore;
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
        if (currentStreak > highestStreak)
            highestStreak = currentStreak;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }
    private void updateCurrentScore(int gameScore) {
        this.currentStreak += gameScore;
    }
    public void resetCurrentScore() {
        currentStreak = 0;
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
        return String.valueOf(totalScore) + "." + String.valueOf(currentStreak) + "." + String.valueOf(highestStreak);
    }

    /*@Override
    protected Object convert(String s) {
        System.out.println("USER SCORE: " + s);
        UserScore score = new UserScore();
        String[] split = s.split(".", 3);
        score.setTotalScore(Integer.valueOf(split[0]));
        score.setCurrentStreak(Integer.valueOf(split[1]));
        score.setHighestStreak(Integer.valueOf(split[2]));
        return score;
//        return null;
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        UserScore score = (UserScore) value;

        return "\"" + score.getTotalScore() + "." + score.getCurrentStreak() + "." + score.getHighestStreak() + "\"";
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
