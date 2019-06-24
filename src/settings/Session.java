package settings;

import game.Game;
import game.hangman.Hangman;
import game.minesweeper.Minesweeper;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import settings.user.User;
import settings.user.UserManager;

import java.io.*;

public class Session {
    public static BorderPane pane = new BorderPane();
    public static User user;
    public static Scene scene = new Scene(pane, 500, 800);
    public static Game game;
    public static boolean activeGame;

    public static final long SERIAL_VERSION_UID = 7L;
    public static final String CURRENT_USER_FILE = "src/assets/userdata/currentUser.dat";
    public static final String ALL_USER_FILE = "src/assets/userdata/users.csv";

    /**
     * Making sure all assets are in order and loaded up on start.
     */
    public static void initSession() {
        // If files don't exist, generate them
        try {
            File currentUserFile = new File(CURRENT_USER_FILE);
            // try to create new currentUser.dat
            currentUserFile.createNewFile();
            // Save a default user to the new file
            if (currentUserFile.length() == 0) {
                System.out.println("SESSION: Creating new currentUser.dat");
                UserManager.saveCurrentUser(new User());
            }

            // TODO Make this all a local sql database to start
            File allUsersFile = new File(ALL_USER_FILE);
            // try to create users.csv
            allUsersFile.createNewFile();
            if (allUsersFile.length() == 0) {
                // add header to the file
                System.out.println("SESSION: Generating new Header");
                FileWriter fileWriter = new FileWriter(Session.ALL_USER_FILE);
                fileWriter.append("\"USERNAME\",\"PASSWORD\",\"REMEMBERPASSWORD\",\"REMEMBERUSER\",\"TOTALSCORE\",\"HIGHESTSTREAK\"\n");
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // pull up a user to start with
        System.out.println("SESSION: Getting current user");
        user = UserManager.getCurrentUser();

        // init game activity
        // TODO allow a user to save a game in mid session upon exit?
        activeGame = false;
    }

    /**
     * Create a new Game based on user interaction.
     * @param selection String retrieved from either the Game Selection Pane or straight up
     *                  from the Session game with a getClass() instance.
     */
    public static void gameSelection(String selection) {
        // TODO Can I auto populate this to reduce human error?
        switch (selection) {
            case "Hangman":
                Session.game = new Hangman(); break;
            case "Minesweeper":
                Session.game = new Minesweeper(); break;
            default:
                System.out.println("GAME SELECTION BUTTON: Default case. Nothing happens.");
                Session.game = new Hangman(); // default hangman to make sure that there is *something*
        }

        Session.pane.setCenter(Session.game);
    }

    public static void printSessionUser() {
        System.out.println("-------------------------------------");
        System.out.println("--- PRINTING CURRENT SESSION USER ---");
        System.out.println(user.toString());
        System.out.println("-------------------------------------");
    }

    public static void printSavedUser() {
        System.out.println("-------------------------------------");
        System.out.println("---- PRINTING SAVED SESSION USER ----");
        System.out.println(UserManager.getCurrentUser().toString());
        System.out.println("-------------------------------------");
    }
}
