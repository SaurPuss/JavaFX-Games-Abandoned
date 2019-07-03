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

import settings.AppSettings;
import settings.GUI.panes.GameSelectionPane;
import javafx.application.Application;
import javafx.stage.Stage;

import settings.GUI.panes.TopBarPane;
import settings.user.UserDatabase;
import settings.user.score.Scoreboard;
import settings.GUI.panes.LoginPane;
import settings.user.user.User;
import settings.user.UserManager;

/**
 * Welcome to a set of games made in javafx to entertain you for a hot minute.
 */
// TODO make real and functional and most of all useful Javadoc
// TODO Make user of OpenCSV I guess
public class Main extends Application {
    // This is the main Pane
    private static Scoreboard scoreboard = new Scoreboard();

    /**
     * Start the application with javafx.
     * @param primaryStage default argument
     */
    @Override
    public void start(Stage primaryStage) {
        // Make sure all things are working on launch
        try {
            AppSettings.initSession();
        } catch (Exception e) {
            System.out.println("MAIN: Exception caught in session initialization.");
        }

        // Check if user is default profile
        if (User.isDefaultUser()) {
            System.out.println("MAIN: Default User detected");

            AppSettings.pane.setCenter(new LoginPane());
            LoginPane.cbRememberUser.setSelected(false);


        } // Check if there is a current user that's also saved to the database
        else if (UserManager.matchCurrentUser()) {
            System.out.println("MAIN: User exists.");
            // TODO if current user is not saved in the db reset to default

            // Password auto login?
            if (!AppSettings.user.userSettings.isRememberPassword()) {
                System.out.println("MAIN: User Password required.");
                AppSettings.pane.setCenter(new LoginPane(AppSettings.user));


            } else { // Auto login, update scoreboard and go to game selection screen

                AppSettings.pane.setTop(new TopBarPane());
                AppSettings.pane.setCenter(new GameSelectionPane());

                // Fill Scores and UserSettings for this user profile
                scoreboard.updateUserScoreboard(AppSettings.user);
            }
        } else {
            // fallback just in case
            System.out.println("MAIN: Starting with a blank slate: new LoginPane()");
            AppSettings.pane.setCenter(new LoginPane());
        }

        // Put it all together in a neat little package
        primaryStage.setTitle("Let's Play a Game");
        primaryStage.setScene(AppSettings.scene);
        primaryStage.show();

        primaryStage.setResizable(false); // No resize for you!
    }

    @Override
    public void stop() {
        System.out.println("MAIN: Stopping application");
        // TODO Save session user updates to db

        if (!AppSettings.user.userSettings.isRememberUser()) {
            System.out.println("MAIN: Saving default user to currentUser.dat");
            UserManager.saveCurrentUser(new User());
        }
        AppSettings.printSessionUser();
        AppSettings.printSavedUser();
    }
    /**
     * Adding a main, because technically you're supposed to do that
     */
    public static void main(String[] args) {
        launch(args);
    }
}
