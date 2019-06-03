package settings.user;

import settings.GUI.panes.LoginPane;
import settings.Session;

import java.io.*;

// TODO password hashing and encryption
public class UserManager implements Serializable {
    private static final long serialVersionUID = Session.SERIAL_VERSION_UID;

    /**
     * Match current User to database.
     * @return Saved user in currentUser.dat matches one of the users in users.csv
     */
    public static boolean matchCurrentUser() { return findUserName(getCurrentUser().getUserName()); }


    private static boolean matchPassword(String username, String password) {
        try {
            System.out.println("USER MANAGER: Password match: " + password.equals(getUserPassword(username)));
            return password.equals(getUserPassword(username));
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("USER MANAGER: matchPassword caused a NullPointerException");
            return false;
        }
    }

    /**
     * // TODO this one assumes you have the password, which is not necessarily useful when trying to log in
     * Attempt to retrieve a saved User from users.csv
     * @param userName User input to verify
     * @param userPassword User input to verify
     * @return verified User profile, or bad default
     */
    public static User getUserProfile(String userName, String userPassword) {
        if (findUserName(userName)) { // verify userName
            // TODO hash password input
            if (matchPassword(userName, userPassword)) {
                System.out.println("USER MANAGER: Successfully retrieved user from database");
                return retrieveUser(userName, userPassword);
            } else {
                System.out.println("USER MANAGER: Password mismatch, retrieval failed, returning default user.");
                return new User();
            }
        }

        System.out.println("USER MANAGER: Username doesn't exist in the database. Returning default user");
        return new User(); // Bad default
    }

    /**
     * Check if a user already exists in the users.csv
     * @param userName String to search for in the database
     * @return Yay or Nay
     */
    public static boolean findUserName(String userName) {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(Session.ALL_USER_FILE));
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length > 0) {
                    if (userName.toLowerCase().equals(fields[0].toLowerCase())) {
                        System.out.println("USER MANAGER: Found user in database");
                        return true;
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("USER MANAGER: (Current) User not found in database.");
        return false;
    }

    /**
     * Get user from database
     * @param userName Check this user
     * @return password
     */
    private static String getUserPassword(String userName) {
        // TODO Make this return the hashed password and make a (de)hashing method
        // TODO make this called from the public boolean matchPassword ?
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(Session.ALL_USER_FILE));
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");


                if (fields.length > 0) {
                    if (userName.equals(fields[0])) {
                        return fields[1];
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if that didn't work return nothing
        return null;
    }

    /**
     * Private method that is called once username and password are verified
     * to match. This pulls the User from the database.
     * @param userName Verified input to function as a db key
     * @param userPassword Verified input to double check before retrieving User
     * @return if match return user, else return default
     */
    private static User retrieveUser(String userName, String userPassword) {
        User user = new User();
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(Session.ALL_USER_FILE));
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length > 0) {
                    // Can't hurt to double check username and password before filling object
                    if ((userName.equals(fields[0])) && (userPassword.equals(fields[1]))) {
                        user.setUserName(fields[0]);
                        user.setUserPassword(fields[1]);
                        user.getUserScore().setSavedTotalScore(Integer.valueOf(fields[2]));
                        user.getUserScore().resetCurrentScore();
                        user.getUserScore().setSavedHighestStreak(Integer.valueOf(fields[4]));
                        user.setRememberPassword(Boolean.valueOf(fields[5]));
                        user.setRememberUser(Boolean.valueOf(fields[6]));
                    } else {
                        System.out.println("USERMANAGER: Can't retrieve user, password did not match.");
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    /**
     * Try to retrieve a user from currentUser.dat, if failed return default.
     * @return Existing or Default User object
     */
    public static User getCurrentUser() {
        try {
            FileInputStream f = new FileInputStream(Session.CURRENT_USER_FILE);
            ObjectInputStream o = new ObjectInputStream(f);

            Session.user = (User) o.readObject();

            o.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Session.user;
    }

    /**
     * Save user to currentUser.dat
     * @param user User to be saved
     */
    public static void saveCurrentUser(User user) {
        try {
            FileOutputStream f = new FileOutputStream(Session.CURRENT_USER_FILE, false);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set currentUser.dat to random default user, wiping any existing record.
     */
    private static void resetCurrentUser() {
        System.out.println("USER MANAGER: Setting random default user to currentUser.dat");
        saveCurrentUser(new User());
    }

    /**
     * Convenience method that invokes the saveNewUser(User) method
     * @param username Making a new user with this username
     * @param password Adding a password to be saved too
     */
    public static void saveNewUser(String username, String password) {
        User user = new User(username, password);
        System.out.println("USER MANAGER: Saving new user to users.csv.");
        saveNewUser(user);

        Session.user = user;
        System.out.println("USER MANAGER: Set Session.user as newly created user");

        if (LoginPane.cbRememberUser.isSelected()) {
            UserManager.saveCurrentUser(user);
            System.out.println("USER MANAGER: Saving new user to currentUser.dat");
        } else {
            System.out.println("USER MANAGER: Not saving new user to currentUser.dat");
            UserManager.resetCurrentUser();
        }
    }

    /**
     * Save User to users.csv, assuming it's a unique username and has a password
     * @param user User input to potentially save
     */
    private static void saveNewUser(User user) {
        try {
            File check = new File(Session.ALL_USER_FILE);
            FileWriter fileWriter = new FileWriter(Session.ALL_USER_FILE, true);

            // Add the user information to the file
            fileWriter.append(user.getUserName());
            fileWriter.append(',');
            fileWriter.append(user.getUserPassword());
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.getUserScore().getTotalScore()));
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.getUserScore().getCurrentScore()));
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.getUserScore().getHighestStreak()));
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.isRememberPassword()));
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.isRememberUser()));
            fileWriter.append("\n");

            System.out.println("USER MANAGER: User has been saved successfully in users.csv");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
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
