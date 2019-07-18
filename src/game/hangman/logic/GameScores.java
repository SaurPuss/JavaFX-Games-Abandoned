package game.hangman.logic;

import game.hangman.Hangman;
import settings.user.settings.GameDifficulty;

public class GameScores {

    /**
     * This is where I will put all the stuff to calculate scores I guess
     */
    int calculateScore() {
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

        // TODO check mode, win, and save to db and user object
        GameDifficulty difficulty = Hangman.getGameDifficulty();

        // Calculate points
        switch (difficulty) {
            case EASY: break;
            case NORMAL: break;
            case HARD: break;
            default:
        }


    }
}
