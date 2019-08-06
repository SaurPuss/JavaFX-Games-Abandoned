package settings;

import database.DatabaseManager;
import game.Game;
import javafx.scene.layout.BorderPane;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import settings.user.*;
import settings.user.user.User;

import java.io.*;
import java.util.Set;

public class AppSettings {
    public static final long SERIAL_VERSION_UID = 9L;
    public static final String CURRENT_USER_FILE = "src/assets/userdata/currentUser.dat";

    public static BorderPane pane = new BorderPane();
    public static User user;
    public static Game game;
    public static boolean activeGame = false;

    /**
     * Do a pass over on application start to make sure that
     * all files are available for saving and we have a User
     * object to play with.
     */
    public static void initSession() throws IOException {
        // Make sure we have user files to save to
        File currentUserFile = new File(CURRENT_USER_FILE);
        currentUserFile.createNewFile();
        if (currentUserFile.length() == 0)
            UserManager.saveCurrentUser(new User());

        // TODO allow for connection to remote host test here
        File allUsersFile = new File("src/assets/userdata/userDatabase.mv.db");
        if (!allUsersFile.exists() || allUsersFile.length() == 0)
            DatabaseManager.makeDatabase();

        // Check for saved User object
        if (UserManager.getCurrentUser().getUserSettings().isRememberUser()) {
            System.out.println("SESSION: Getting current user");
            user = UserManager.getCurrentUser();
        } else {
            System.out.println("SESSION: Setting new current user");
            user = new User();
        }
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

    /**
     * Print current User object info to the command line.
     */
    public static void printUser() {
        System.out.println("-------------------------------------");
        System.out.println("--- PRINTING CURRENT SESSION USER ---");
        System.out.println(user.toString());
        System.out.println("-------------------------------------");
    }
}
