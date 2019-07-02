package settings;

import game.Game;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
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

    public static final long SERIAL_VERSION_UID = 8L;
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
//            if (allUsersFile.length() == 0) {
//                // add header to the file
//                System.out.println("SESSION: Generating new Header");
//                FileWriter fileWriter = new FileWriter(AppSettings.ALL_USER_FILE);
//                fileWriter.append("\"USERNAME\",\"PASSWORD\",\"REMEMBERPASSWORD\",\"REMEMBERUSER\",\"TOTALSCORE\",\"HIGHESTSTREAK\",\"GAMEDIFFICULTY\"\n");
//                fileWriter.close();
//            }
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
