package settings;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import settings.user.User;
import settings.user.UserManager;

import java.io.File;
import java.io.IOException;

public class Session {
    public static BorderPane pane = new BorderPane();
    public static User user = new User();
    public static Scene scene = new Scene(pane, 500, 800);

    public static final long SERIAL_VERSION_UID = 2L; // Current version, update when file composition changes
    public static final String CURRENT_USER_FILE = "src/assets/currentUser.dat";
    public static final String ALL_USER_FILE = "src/assets/users.csv";

    /**
     * Making sure all assets are in order and loaded up on start.
     */
    public static void initSession() {
        // Check and if necessary create currentUser.dat
        File file = new File(CURRENT_USER_FILE);
        try {
            // try to create new currentUser.dat
            file.createNewFile();
            // Save a default user to the new file
            if (file.length() == 0)
                UserManager.saveCurrentUser(new User());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Get user from currentUser.dat
        user = UserManager.getCurrentUser();

        // Do stuff with the local database which holds multiple user objects


        // Do scoreboard stuff
    }

    public static void printCurrentUser() {
        System.out.println("-------------------------------------");
        System.out.println("--- PRINTING CURRENT SESSION USER ---");
        System.out.println(user.toString());
        System.out.println("-------------------------------------");
    }
}
