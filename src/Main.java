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
import settings.GUI.panes.GameSelectionPane;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import settings.GUI.panes.TopBarPane;
import settings.Session;
import settings.user.score.Scoreboard;
import settings.GUI.panes.LoginPane;
import settings.user.User;
import settings.user.UserManager;

/**
 * Welcome to a set of games made in javafx to entertain you for a hot minute.
 * @author SaurPuss
 * @version 0.1
 */
public class Main extends Application {
    // This is the main Pane
    private static Scoreboard scoreboard = new Scoreboard();

    private Game game;
    private HBox gamePane;


    /**
     * Start the application with javafx.
     * @param primaryStage default argument
     */
    @Override
    public void start(Stage primaryStage) {
        // Make sure all things are working on launch
        Session.initSession();
        Session.printCurrentUser();

        // Check if user is default profile
        if (User.isDefaultUser()) {
            System.out.println("MAIN: Default User detected");

            Session.pane.setCenter(new LoginPane());


        } // Check if there is a current user that's also saved to the database
        else if (UserManager.matchCurrentUser()) {
            System.out.println("MAIN: Someone with this username is saved in the global database, woo!");

            // Password auto login?
            if (!Session.user.isRememberPassword()) {
                System.out.println("MAIN: User Password required.");
                Session.pane.setCenter(new LoginPane(Session.user));
            } else { // Auto login, update scoreboard and go to game selection screen

                Session.pane.setTop(new TopBarPane());
                Session.pane.setCenter(new GameSelectionPane());

                // Fill Scores and UserSettings for this user profile
                scoreboard.updateUserScoreboard(Session.user);

                // Continue to Game selection screen
                game = GameSelectionPane.startGame(0);
            }
        }
        // If all else fails
        else {
            Session.user = new User();
            System.out.println("MAIN: No current user found, something went super bad. Creating new user.");
//                LoginPane.signUpScreen();
        }



        // TODO At the top of the pane display current username, a button for scores and a button for settings





        // Set up basic panes
        gamePane = new HBox();
        gamePane.setAlignment(Pos.CENTER);

        splashStart();
        // This is temporary
//        startGame(0);

//        gamePane.getChildren().addAll(game);
//        Session.pane.setCenter(gamePane);


        // Put it all together in a neat little package
        primaryStage.setTitle("Let's Play a Game");
        primaryStage.setScene(Session.scene);
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
