package game.hangman;

import game.GUI.panes.BottomBarPane;
import game.Game;
import game.hangman.GUI.*;
import game.hangman.logic.*;
import javafx.geometry.*;
import javafx.scene.layout.VBox;
import settings.user.settings.GameDifficulty;

import static settings.AppSettings.*;

public class Hangman extends Game {
    private static GameDifficulty difficulty;

    public Hangman() {
        // A new game is active
        activeGame = true;
        difficulty = user.getUserSettings().getGameDifficulty();
        System.out.println("HANGMAN: Starting new Game!");

        // init new game
        GameSession.gameWord = new GameWord();
        GameDiagram hangmanView = new GameDiagram();
        GameFields hangmanFields = new GameFields();

        // Display the parts
        VBox view = new VBox(5);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(0, 50, 0, 50));
        view.getChildren().addAll(hangmanView, hangmanFields);
        getChildren().add(view);

        // Create a bottom Pane for session
        pane.setBottom(new BottomBarPane());

        // TODO Implement game score stuff depending on difficulty
        // On win add the full word length to the score before sending it to userScore
        // So basically keep track of the current game score and then add it to the user score
        // once a game is completed or aborted. If the session is closed also save game score,
        // like inside the stop method. Make it gather all current scores and do stuff with it.
        // unless someone manages to save mid game in which case the score should just be saved
        // until completion.
    }

    public Hangman(GameDifficulty gameDifficulty) {
        // A new game is active
        activeGame = true;
        difficulty = gameDifficulty;
        System.out.println("HANGMAN: Starting new Game!");

        // init new game
        GameSession.gameWord = new GameWord();
        GameDiagram hangmanView = new GameDiagram();
        GameFields hangmanFields = new GameFields();

        // Display the parts
        VBox view = new VBox(5);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(0, 50, 0, 50));
        view.getChildren().addAll(hangmanView, hangmanFields);
        getChildren().add(view);

        // Create a bottom Pane for session
        pane.setBottom(new BottomBarPane());

    }

    /*Hangman(String word) {
        // This is a thing for multi-player, at some point
        gameWord = new GameWord(word);
        hangmanView = new GameDiagram();
        hangmanFields = new GameFields();
    }*/

    public static GameDifficulty getGameDifficulty() { return difficulty;}
}
