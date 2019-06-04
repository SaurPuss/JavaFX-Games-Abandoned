package settings.user;

import com.opencsv.bean.AbstractBeanField;
import settings.user.score.UserScore;

public abstract class UserScoreToBean extends AbstractBeanField<UserScore> {
    /*@Override
    public Object convertToRead(String value) {
        UserScore score = new UserScore();
        String[] split = value.split("\\.", 3);
        score.setSavedTotalScore(Integer.valueOf(split[0]));
        score.setCurrentScore(0);
        score.setSavedHighestStreak(Integer.valueOf(split[2]));
        return score;
    }*/

    @Override
    public String convertToWrite(Object value) {
        UserScore userScore = (UserScore) value;
        return String.format("%s.%s.%s", userScore.getTotalScore(), userScore.getCurrentScore(), userScore.getHighestStreak());
    }



}
