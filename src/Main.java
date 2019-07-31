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

import settings.GUI.panes.*;
import settings.user.user.User;
import settings.user.UserManager;
import settings.AppSettings;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Welcome to a set of games made in javafx to entertain you for a hot minute.
 */
public class Main extends Application {
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

            // Password auto login?
            if (!AppSettings.user.getUserSettings().isRememberPassword()) {
                System.out.println("MAIN: User Password required.");
                AppSettings.pane.setCenter(new LoginPane(AppSettings.user));
            } else {
                AppSettings.pane.setTop(new TopBarPane());
                AppSettings.pane.setCenter(new GameSelectionPane());
            }
        } else {
            System.out.println("MAIN: Starting with a blank slate: new LoginPane()");
            AppSettings.pane.setCenter(new LoginPane());
        }

        // Put it all together in a neat little package
        primaryStage.setTitle("Let's Play a Game");
        primaryStage.setScene(AppSettings.scene);
        primaryStage.show();

        primaryStage.setResizable(false); // No resize for you!
    }

    /**
     * Adding a main, because technically you're supposed to do that
     */
    public static void main(String[] args) {
        launch(args);
    }
}
