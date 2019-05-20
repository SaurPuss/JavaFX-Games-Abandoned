/**
 * // TODO: figure out how this is supposed to look, lol
 *
 * @author      SaurPuss
 * @copyright   This all belongs to SaurPuss, except for the assets listed below.
 *              You can probably use it for your own stuff, just shoot her a mail.
 * @assets      Dictionary source: http://www-personal.umich.edu/~jlawler/wordlist
 * @contact     saurlemons@gmail.com
 */

// You know what this is, why are you reading this comment?
import game.Game;
import game.hangman.Hangman;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import settings.User;

import java.io.IOException;

/**
 * Let's get this bitch started! /o/
 */
public class Main extends Application {
    private static BorderPane pane = new BorderPane();
    private Scene scene;
    private Game game;
    private HBox gamePane;


    @Override
    public void start(Stage primaryStage) throws IOException {
        gamePane = new HBox();
        gamePane.setAlignment(Pos.CENTER);

        startGame(0);

        gamePane.getChildren().addAll(game);
        pane.setCenter(gamePane);

        /*// test reset button
        Button btnReset = new Button("reset");
        btnReset.setOnAction(e -> restartGame(0));
        pane.setBottom(btnReset);*/

        scene = new Scene(pane, 500, 800);

//        User.saveUser(new User("Test User", "password"));

        primaryStage.setTitle("Let's Play a Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setResizable(false); // No resize for you!
    }


    /**
     * Populate the initial pane with starter text and buttons. Only used to get the
     * game started, never to be seen again (in this session).
     */
    private void splashStart() {
        // Create new base user
        User newUser = new User();

        // Give the option to load existing User


        // Start a Game


        // Go to Scores and Settings for an exisitng user

    }

    /**
     * Start the game of choice.
     * Yes I'm aware that currently there is only one game to choose from and this
     * is technically redundant. Suck it up.
     * @param x Select a game to start
     */
    private void startGame(int x) {
        switch (x) {
            default:
                this.game = new Hangman();
        }
    }

    // TODO maybe move it to the game class itself
    private void restartGame(int x) {
        switch(x) {
            default:
                gamePane.getChildren().remove(game); // remove old game
                this.game = new Hangman();
                gamePane.getChildren().addAll(game); // add replacement
        }
    }
}
