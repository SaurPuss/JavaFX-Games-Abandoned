package settings.user.score;

import javafx.scene.layout.BorderPane;
import settings.user.User;

import java.util.HashMap;

/**
 * Pane with all scores
 */
public class Scoreboard extends BorderPane {
    private HashMap<String, Integer> globalScores;
    private UserScore userScore;
    private HashMap<String, Integer> hangmanScores;

    public Scoreboard() {
        globalScores = retrieveTopScores();
        userScore = new UserScore();

        // Top 10 for each game
        hangmanScores = retrieveHangmanScores();
    }

    public Scoreboard(User user) {
        globalScores = retrieveTopScores();
        userScore = user.getUserScore();

        // Top 10 for each game
        hangmanScores = retrieveHangmanScores();
    }




    private HashMap<String, Integer> retrieveTopScores() {
        HashMap<String, Integer> temp = new HashMap<>();
        System.out.println("Get top 10 global scores from a csv");
        System.out.println("First line in csv with string \"GLOBAL SCORES\"");



        return temp;
    }

    private void updateScoreboard() {
        System.out.println("Print the scores to the Scoreboard");
        retrieveTopScores();

    }

    private void saveScores() {
        System.out.println("Save the scores to the global scores csv");
//        saveUserScores();
    }



    public void updateUserScoreboard(User user) {
        System.out.println("Import user specific scores, saved in the user scores csv");
        System.out.println("retrieve them from the line with the username as a key");

        System.out.println("Adjust global scores if necessary?");
    }


    private void printGameScores(int n) {

        switch(n) {
            default:
                System.out.println("Print the scores for the corresponding Game");
        }
    }

    private void printGlobalScores() {
        globalScores = retrieveTopScores();

        System.out.println("Print the stuff into the pane from the top 10 global scores");
    }



    private HashMap<String, Integer> retrieveHangmanScores() {
        System.out.println("Get the top 10 from hangman");
        System.out.println("From the csv with global scores, use \"GAME HANGMAN\" as a key");

        return new HashMap<>();
    }
}
