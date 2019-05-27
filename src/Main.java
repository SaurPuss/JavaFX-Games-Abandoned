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

import settings.score.Scoreboard;
import settings.GUI.LoginFields;
import settings.user.Login;
import settings.user.User;
import settings.user.UserManager;

/**
 * Let's get this bitch started! /o/
 */
public class Main extends Application {
    private User user;
    private Scoreboard scoreboard;

    private static BorderPane pane = new BorderPane();
    private Scene scene;
    private Game game;
    private HBox gamePane;


    @Override
    public void start(Stage primaryStage) {
        // Attempt to retrieve a saved or default User
        user = UserManager.getCurrentUser();

        // Check if user is saved in database
        if (UserManager.findUser(user.getUserName())) {
            System.out.println("Someone with this username exists.");

            // Password auto login?
            if (user.isRememberPassword()) {
                // Fill Scores and UserSettings for this user profile
                scoreboard.updateUserScoreboard(user);

                // Continue to Game selection screen
                game = GameSelection.startGame(0);

            } else {
                System.out.println("Please provide the matching password");
                Login.loginScreen();
            }
        } else {
            // prompt for login or sign up
            Login.signUpScreen();

        }






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
        LoginFields fields = new LoginFields();

        startPane.setAlignment(Pos.CENTER);
        startPane.getChildren().add(fields);

        // Create new base user

        // Give the option to load existing User


        // Start a Game


        // Go to Scores and UserSettings for an exisitng user


        // Add stuff to scene
        scene = new Scene(startPane, 500, 800);

    }

    /**
     * Adding a main, because technically you're supposed to do that
     */
    public static void main(String[] args) {
        launch(args);
    }
}
