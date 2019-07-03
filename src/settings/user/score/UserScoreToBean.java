package settings.user.score;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import settings.user.user.UserScore;

public abstract class UserScoreToBean extends AbstractBeanField<UserScore> {
    public UserScoreToBean() {}

    @Override
    protected Object convert(String s) {
        System.out.println("USER SCORE: " + s);
        UserScore score = new UserScore();
        String[] split = s.split(".", 3);
//        score.setTotalScore(Integer.valueOf(split[0]));
//        score.setCurrentStreak(Integer.valueOf(split[1]));
//        score.setHighestStreakScore(Integer.valueOf(split[2]));
//        return score;
        return null;
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        UserScore score = (UserScore) value;

//        return "\"" + score.getTotalScore() + "\",\"" + score.getCurrentStreak() + "\",\"" + score.getHighestStreakScore() + "\"";
        return super.convertToWrite(value);
    }
}
