package settings;

import game.Game;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import settings.user.DatabaseManager;
import settings.user.user.User;
import settings.user.UserManager;

import java.io.*;
import java.util.Set;

public class AppSettings {
    public static BorderPane pane = new BorderPane();
    public static User user;
    public static Scene scene = new Scene(pane, 500, 800);
    public static Game game;
    public static boolean activeGame;

    public static final long SERIAL_VERSION_UID = 9L;
    public static final String CURRENT_USER_FILE = "src/assets/userdata/currentUser.dat";

    /**
     * Making sure all assets are in order and loaded up on start.
     */
    public static void initSession() {
        try {
            // If files don't exist, generate them
            File currentUserFile = new File(CURRENT_USER_FILE);
            File allUsersFile = new File("src/assets/userdata/userDatabase.mv.db");

            currentUserFile.createNewFile();
            if (currentUserFile.length() == 0) {
                System.out.println("SESSION: Creating new currentUser.dat");
                UserManager.saveCurrentUser(new User());
            }
            if (!allUsersFile.exists() || allUsersFile.length() == 0)
                DatabaseManager.makeDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if current user wants to be remembered and set a Session User
        if (UserManager.getCurrentUser().getUserSettings().isRememberUser()) {
            System.out.println("SESSION: Getting current user");
            user = UserManager.getCurrentUser();
        } else {
            System.out.println("SESSION: Setting new current user");
            user = new User();
        }

        // init game activity
        // TODO allow a user to save a game in mid session upon exit?
        activeGame = false;
    }

    /**
     * Create new Game instance and place it in the center pane.
     * @param selection String matching one of the available Classes
     *                  extending game.Game
     */
    public static void gameSelection(String selection) {
        try {
            Set<Class<? extends Game>> classes = new Reflections(ClasspathHelper
                    .forPackage("game")).getSubTypesOf(Game.class);

            for (Class<? extends Game> c : classes) {
                if (selection.equalsIgnoreCase(StringUtils.substringAfterLast(c.getName(), ".")))
                    game = c.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pane.setCenter(game);
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
