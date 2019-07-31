package settings.user;

import database.DatabaseManager;
import settings.AppSettings;
import settings.user.user.User;

import java.io.*;

// TODO password hashing and encryption
public class UserManager implements Serializable {
    private static final long serialVersionUID = AppSettings.SERIAL_VERSION_UID;

    /**
     * Check if a user already exists in user database
     * @param userName String to search for in the database
     * @return Yay or Nay
     */
    public static boolean findUserName(String userName) {
        return DatabaseManager.findUser(userName);
    }

    /**
     * Match current User to database.
     * @return boolean match
     */
    public static boolean matchCurrentUser() { return findUserName(getCurrentUser().getName()); }

    /**
     * Check if the input password matches the stuff in the database, case sensitive
     * @param name you know what this is
     * @param password you also know what this is
     * @return you also know what this does
     */
    public static boolean matchPassword(String name, String password) {
        return password.equals(DatabaseManager.getUserPassword(name));
    }

    /**
     * Save a user to the database
     * @param name username
     * @param password user password
     * @param rememberUser save this preference to the db too
     * @return User to be set for the session
     */
    public static User saveUser(String name, String password, boolean rememberUser) {
        if (!findUserName(name)) {
            User user = DatabaseManager.saveUser(name, password, rememberUser);
            saveCurrentUser(user);
            return user;
        } else {
            System.out.println("USER MANAGER: Can't save as new user, trying to retrieve user instead");
            return DatabaseManager.retrieveUser(name, password);
        }
    }

    public static User saveUser(String name, String password,
                                boolean rememberUser, boolean rememberPassword) {
        System.out.println("USER MANAGER: PLS MAKE THIS METHOD saveUser(4xStuff)");

        return new User();
    }

    public static boolean updateUser(User user) {
        return DatabaseManager.updateUser(user);
    }

    /**
     * Attempt to retrieve a saved User from database
     * @param userName User input to verify
     * @param userPassword User input to verify
     * @return verified User profile, or bad default
     */
    public static User getUserProfile(String userName, String userPassword) {
        // TODO simplify this, should be in sql/db side
        if (findUserName(userName)) {
            // TODO hash password input
            if (matchPassword(userName, userPassword)) {
                System.out.println("USER MANAGER: Retrieving user from database");
                return DatabaseManager.retrieveUser(userName, userPassword);
            }
        }
        System.out.println("USER MANAGER: Returning null");
        return null;
    }

    /**
     * Try to retrieve a user from currentUser.dat, if failed return default.
     * @return Existing or Default User object
     */
    public static User getCurrentUser() {
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(AppSettings.CURRENT_USER_FILE));
            AppSettings.user = (User) o.readObject();
            o.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppSettings.user;
    }

    /**
     * Save user to currentUser.dat
     * @param user User to be saved
     */
    public static void saveCurrentUser(User user) {
        System.out.println("USER MANAGER: Saving current user");
        try {
            ObjectOutputStream o = new ObjectOutputStream(
                    new FileOutputStream(AppSettings.CURRENT_USER_FILE, false));
            o.writeObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveNewUser(User user) {
        if (!findUserName(user.getName())) {
            System.out.println("USER MANAGER: Invoke saveNewUser(user)");
            DatabaseManager.saveUser(user.getName(), user.getPassword(),
                    user.getUserSettings().isRememberUser());
            AppSettings.user = user;
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("USER MANAGER: no object data");
    }
}
