package settings.user.score;

import com.opencsv.bean.AbstractCsvConverter;
import settings.user.user.UserScore;

public class TextToUserScore extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) {
        UserScore score = new UserScore();
        String[] split = value.split("\\.", 3);
//        score.setTotalScore(Integer.valueOf(split[0]));
//        score.setCurrentStreak(Integer.valueOf(split[1]));
//        score.setHighestStreakScore(Integer.valueOf(split[2]));
        return score;
    }

    /*@Override
    public String convertToWrite(Object value) {
        UserScore userScore = (UserScore) value;
        return String.format("%s.%s.%s", userScore.getTotalScore(), userScore.getCurrentStreak(), userScore.getHighestStreakScore());
    }*/
}
