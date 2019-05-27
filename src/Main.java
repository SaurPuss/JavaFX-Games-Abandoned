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
import game.GameSelection;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import settings.GUI.panes.TopBarPane;
import settings.user.score.Scoreboard;
import settings.GUI.panes.LoginPane;
import settings.user.User;
import settings.user.UserManager;

/**
 * Let's get this bitch started! /o/
 */
public class Main extends Application {
    // This is the main Pane
    private static BorderPane pane = new BorderPane();
    private static Scoreboard scoreboard = new Scoreboard();

    private Scene scene;
    private Game game;
    private HBox gamePane;


    /**
     * Start the application with javafx.
     * @param primaryStage default argument
     */
    @Override
    public void start(Stage primaryStage) {
        scene = new Scene(pane, 500, 800);


        // Check if there is a current user that's also saved to the database
        if (UserManager.matchCurrentUser()) {
            UserManager.user = UserManager.getCurrentUser();
            System.out.println("Someone with this username is saved in the global database, woo!");

            // Password auto login?
            if (!UserManager.user.isRememberPassword()) {
                System.out.println("User Password required.");
                pane.setCenter(new LoginPane(UserManager.user));



            } else { // Auto login, update scoreboard and go to game selection screen

                pane.setTop(new TopBarPane());
                pane.setCenter(new GameSelection());

                // Fill Scores and UserSettings for this user profile
                scoreboard.updateUserScoreboard(UserManager.user);

                // Continue to Game selection screen
                game = GameSelection.startGame(0);

            }

        }
        // Sometimes the current user is a default profile
        else if (User.isDefaultUser(UserManager.getCurrentUser())) {
            System.out.println("Default User, choose login/signup or continue as is");
            UserManager.user = UserManager.getCurrentUser();



        }
        // Otherwise there is nothing and you should start fresh
        else {
            UserManager.user = new User();
            System.out.println("No current user found, starting with a default profile.");
//                LoginPane.signUpScreen();
        }


        // TODO At the top of the pane display current username, a button for scores and a button for settings






        // Set up basic panes
        gamePane = new HBox();
        gamePane.setAlignment(Pos.CENTER);

        splashStart();
        // This is temporary
//        startGame(0);

        gamePane.getChildren().addAll(game);
        pane.setCenter(gamePane);





//        scene = new Scene(pane, 500, 800);


        // Put it all together in a neat little package
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
        HBox startPane = new HBox();
//        LoginPane fields = new LoginPane();

        startPane.setAlignment(Pos.CENTER);
//        startPane.getChildren().add(fields);

        // Create new base user

        // Give the option to load existing User


        // Start a Game


        // Go to Scores and UserSettings for an exisitng user


        // Add stuff to scene


    }

    /**
     * Adding a main, because technically you're supposed to do that
     */
    public static void main(String[] args) {
        launch(args);
    }
}
