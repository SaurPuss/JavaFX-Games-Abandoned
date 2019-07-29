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
