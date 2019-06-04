package settings;

import game.Game;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import settings.user.User;
import settings.user.UserManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Session {
    public static BorderPane pane = new BorderPane();
    public static User user = new User();
    public static Scene scene = new Scene(pane, 500, 800);
    public static Game game;

    public static final long SERIAL_VERSION_UID = 4L; // Current version, update when file composition changes
    public static final String CURRENT_USER_FILE = "src/assets/currentUser.dat";
    public static final String ALL_USER_FILE = "src/assets/users.csv";

    /**
     * Making sure all assets are in order and loaded up on start.
     */
    public static void initSession() {
        // Check and if necessary create currentUser.dat
        File currentUserFile = new File(CURRENT_USER_FILE);
        try {
            // try to create new currentUser.dat
            currentUserFile.createNewFile();
            // Save a default user to the new file
            if (currentUserFile.length() == 0) {
                System.out.println("SESSION: Creating new currentUser.dat");
                UserManager.saveCurrentUser(new User());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Get user from currentUser.dat
        user = UserManager.getCurrentUser();

        // Check and if necessary create users.csv
        File allUsersFile = new File(ALL_USER_FILE);
        try {
            // try to create users.csv
            allUsersFile.createNewFile();
            if (allUsersFile.length() == 0) {
                // add header to the file
                System.out.println("SESSION: Creating new users.csv");
                FileWriter fileWriter = new FileWriter(Session.ALL_USER_FILE, true);
                fileWriter.append("Username,Password,TotalScore,CurrentStreak,HighestStreak,RememberPassword,RememberUser\n");
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If last user didn't want to remember, reset the Session user
        if (!user.isRememberUser()) {
            user = new User();
        }
        // Do scoreboard stuff
    }

    public static void printCurrentUser() {
        System.out.println("-------------------------------------");
        System.out.println("--- PRINTING CURRENT SESSION USER ---");
        System.out.println(user.toString());
        System.out.println("-------------------------------------");
    }
}
