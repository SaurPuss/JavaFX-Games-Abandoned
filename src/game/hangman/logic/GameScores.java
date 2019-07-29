package game.hangman.logic;

import game.hangman.Hangman;
import settings.AppSettings;
import settings.user.settings.GameDifficulty;

public class GameScores {

    /**
     * This is where I will put all the stuff to calculate scores I guess
     */
    private int calculateScore() {
        int total;
        int score = GameSession.getGameScore();
        int correct = GameSession.getCorrectGuesses();
        int wrong = GameSession.getWrongGuesses();
        GameDifficulty difficulty = Hangman.getGameDifficulty();

        // Calculate points
        switch (difficulty) {
            case EASY   : total = score; break;
            case NORMAL : total = (2 * score) + correct - wrong; break;
            case HARD   : total = (3 * score) + (2 * correct) - (2 * wrong); break;
            default     : total = score;
        }

        return total;
    }

    void saveScore(boolean win) {
        int score = calculateScore();
        boolean higher = isHigher();

        GameDifficulty difficulty = Hangman.getGameDifficulty();

        // TODO check mode, win, and save to db and user object

        // Calculate points
        switch (difficulty) {
            case EASY:
                // if (higher) save to everything
                break;
            case NORMAL: break;
            case HARD: break;
            default:
        }


    }

    boolean isHigher() {
        GameDifficulty difficulty = Hangman.getGameDifficulty();
        int score = calculateScore();
        int[] saved = AppSettings.user.getUserScore().getHangman();
        boolean result = false;

        switch(difficulty) {
            case EASY   : result = score > saved[0]; break;
            case NORMAL : result = score > saved[1]; break;
            case HARD   : result = score > saved[2];
        }

        return result;
    }
}
