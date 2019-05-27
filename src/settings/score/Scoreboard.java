package settings.score;

import javafx.scene.layout.BorderPane;
import settings.user.User;

import java.util.HashMap;

/**
 * Pane with all scores
 */
public class Scoreboard extends BorderPane {
    private HashMap<String, Integer> topTenScores;

    public Scoreboard() {
        topTenScores = retrieveTopScores();
    }




    private HashMap<String, Integer> retrieveTopScores() {
        HashMap<String, Integer> temp = new HashMap<>();
        System.out.println("Get top 10 global scores from a csv");



        return temp;
    }

    private void updateScoreboard() {
        System.out.println("Print the scores to the Scoreboard");
        retrieveTopScores();

    }

    private void saveScores() {
        System.out.println("Save the scores to the csv");
//        saveUserScores();
    }



    public void updateUserScoreboard(User user) {
        System.out.println("Import user specific scores");

        System.out.println("Adjust global scores if necessary?");
    }
}
