/**
 * // TODO: figure out how this is supposed to look, lol
 *
 * @author      SaurPuss
 * @copyright   This all belongs to SaurPuss, except for the assets listed below.
 *              You can probably use it for your own stuff, just shoot her a mail.
 * @assets      Dictionary source: http://www-personal.umich.edu/~jlawler/wordlist
 * @contact     saurlemons@gmail.com
 * @version     1.1.5
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
 */
// TODO make real and functional Javadoc
// TODO Make user of OpenCSV I guess
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

        // Check if user is default profile
        if (User.isDefaultUser()) {
            System.out.println("MAIN: Default User detected");

            Session.pane.setCenter(new LoginPane());
            LoginPane.cbRememberUser.setSelected(false);


        } // Check if there is a current user that's also saved to the database
        else if (UserManager.matchCurrentUser()) {
            System.out.println("MAIN: User exists.");

            // Password auto login?
            if (!Session.user.isRememberPassword()) {
                System.out.println("MAIN: User Password required.");
                Session.pane.setCenter(new LoginPane(Session.user));


            } else { // Auto login, update scoreboard and go to game selection screen

                Session.pane.setTop(new TopBarPane());
                Session.pane.setCenter(new GameSelectionPane());

                // Fill Scores and UserSettings for this user profile
                scoreboard.updateUserScoreboard(Session.user);
            }
        } else {
            // fallback just in case
            System.out.println("MAIN: Starting with a blank slate: new LoginPane()");
            Session.pane.setCenter(new LoginPane());
        }



        // TODO At the top of the pane display current username, a button for scores and a button for settings





        // Set up basic panes
        gamePane = new HBox();
        gamePane.setAlignment(Pos.CENTER);


        // Put it all together in a neat little package
        primaryStage.setTitle("Let's Play a Game");
        primaryStage.setScene(Session.scene);
        primaryStage.show();

        primaryStage.setResizable(false); // No resize for you!
    }

    @Override
    public void stop() {
        System.out.println("MAIN: Stopping application");
        // TODO Save session user updates to db

        if (!Session.user.isRememberUser()) {
            System.out.println("MAIN: Saving default user to currentUser.dat");
            UserManager.saveCurrentUser(new User());
        }
        Session.printSessionUser();
        Session.printSavedUser();
    }
    /**
     * Adding a main, because technically you're supposed to do that
     */
    public static void main(String[] args) {
        launch(args);
    }
}
