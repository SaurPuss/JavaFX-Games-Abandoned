package settings.user;

import com.opencsv.bean.AbstractBeanField;
import settings.user.score.UserScore;

public abstract class UserScoreToBean extends AbstractBeanField<User> {
    @Override
    public Object convert(String value) {
        System.out.println("USER SCORE TO BEAN: " + value);
        UserScore score = new UserScore();
        String[] split = value.split(",", 3);
        score.setTotalScore(Integer.valueOf(split[0]));
        score.setCurrentScore(Integer.valueOf(split[1]));
        score.setHighestStreak(Integer.valueOf(split[2]));
        return score;
    }


    public Object convert(int total, int current, int highest) {
        return new UserScore(total, current, highest);
    }

    /*@Override
    public String convertToWrite(Object value) {
        UserScore userScore = (UserScore) value;
        System.out.printf("USER SCORE TO BEAN: %s.%s.%s", userScore.getTotalScore(), userScore.getCurrentScore(), userScore.getHighestStreak());
        return String.format("%s.%s.%s", userScore.getTotalScore(), userScore.getCurrentScore(), userScore.getHighestStreak());
    }*/



}
