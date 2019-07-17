package game.hangman.logic;

import game.hangman.Hangman;

public class GameScores {

    /**
     * This is where I will put all the stuff to calculate scores I guess
     */

    int calculateScore() {
        // TODO actually make this work and make sense
        int score = 0;
        int letterScore = GameSession.getGameWord().length();
        int guesses = GameSession.mistakes;

        switch(Hangman.getGameDifficulty()) {
            case EASY   : guesses = 0; break;
            case HARD   : letterScore *= 3; guesses *= 2; break;
            case NORMAL :
            default     : letterScore *= 2;
        }

        score += letterScore - guesses;

        return score;
    }

}
