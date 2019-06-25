package settings.user;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import settings.AppSettings;
import settings.user.settings.GameDifficulty;

import java.io.*;

// TODO password hashing and encryption
// TODO change the CSV database to (embedded) SQL database
public class UserManager implements Serializable {
    private static final long serialVersionUID = AppSettings.SERIAL_VERSION_UID;

    /**
     * Match current User to database.
     * @return Saved user in currentUser.dat matches one of the users in users.csv
     */
    public static boolean matchCurrentUser() { return findUserName(getCurrentUser().getUserName()); }

    public static boolean matchPassword(String username, String password) {
        try {
            return password.equals(getUserPassword(username));
        } catch (NullPointerException e) {
            System.out.println("USER MANAGER: matchPassword caused a NullPointerException");
            return false;
        }
    }

    /**
     * Attempt to retrieve a saved User from users.csv
     * @param userName User input to verify
     * @param userPassword User input to verify
     * @return verified User profile, or bad default
     */
    public static User getUserProfile(String userName, String userPassword) {
        if (findUserName(userName)) { // verify userName
            // TODO hash password input
            if (matchPassword(userName, userPassword)) {
                System.out.println("USER MANAGER: Retrieving user from database");
                return retrieveUser(userName, userPassword);
            } else {
                System.out.println("USER MANAGER: Password mismatch, can't retrieve user from database.");
            }
        }
        System.out.println("USER MANAGER: Returning default user");
        return new User();
    }

    /**
     * Check if a user already exists in the users.csv
     * @param userName String to search for in the database
     * @return Yay or Nay
     */
    public static boolean findUserName(String userName) {
        try {
            CSVReader reader = new CSVReader(new FileReader(AppSettings.ALL_USER_FILE));

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].toLowerCase().equals(userName.toLowerCase())) {
                    System.out.println("USER MANAGER: Found userName in database");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("USER MANAGER: " + userName + " not found in csv.");
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
            CSVReader reader = new CSVReader(new FileReader(AppSettings.ALL_USER_FILE));

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].toLowerCase().equals(userName.toLowerCase())) {
                    return nextLine[1];
                }
            }
        } catch (IOException e) {
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
        try {
            CSVReader reader = new CSVReader(new FileReader(AppSettings.ALL_USER_FILE));

            User user = new User();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if ((nextLine[0].toLowerCase().equals(userName.toLowerCase())) &&
                        (nextLine[1].equals(userPassword))) {
                    // USERNAME
                    user.setUserName(nextLine[0]);
                    // PASSWORD
                    user.setUserPassword(nextLine[1]);
                    // REMEMBER PASSWORD
                    user.setRememberPassword(Boolean.valueOf(nextLine[2]));
                    // REMEMBER USER
                    user.setRememberUser(Boolean.valueOf(nextLine[3]));
//                    temp.setUserScore(new UserScore(Integer.valueOf(nextLine[4]), Integer.valueOf(nextLine[5])));
                    // TOTAL SCORE
                    user.setTotalScore(Integer.valueOf(nextLine[5]));
                    // HIGHEST STREAK
                    user.setHighestStreak(Integer.valueOf(nextLine[4]));
                    // CURRENT STREAK
                    user.setCurrentStreak(0);
                    // GAME DIFFICULTY
                    user.setGameDifficulty(GameDifficulty.fromString(nextLine[6]));

                    System.out.println(user.toString());
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("USER MANAGER: retrieve user failed to get the bean");
        return new User();
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
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(
                    AppSettings.CURRENT_USER_FILE, false));
            o.writeObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void saveUser(User user) {
        if (findUserName(user.getUserName())) {
            updateUser(user);
        } else {
            System.out.println("USER MANAGER: Invoke saveNewUser(user)");
            saveNewUser(user);
            AppSettings.user = user;
        }
    }

    private static void updateUser(User user) {
        // Read whole csv


    }

    /**
     * Save NEW user to users.csv
     * @param user User information to append to users.csv
     */
    private static void saveNewUser(User user) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(AppSettings.ALL_USER_FILE, true));

            String[] record = {
                    user.getUserName(),
                    user.getUserPassword(),
                    String.valueOf(user.isRememberPassword()),
                    String.valueOf(user.isRememberUser()),
                    String.valueOf(user.getTotalScore()),
                    String.valueOf(user.getHighestStreak()),
                    user.getGameDifficulty().toString()
            };
            writer.writeNext(record);
            writer.close();
        } catch (Exception e) {
            System.out.println("USER MANAGER: failed saveNewUser(user)!");
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











    /* Writer writer = new FileWriter(AppSettings.ALL_USER_FILE, true);

//            MappingStrategy<UserScore> scoreStrategy = new HeaderColumnNameMappingStrategy<>();
//            scoreStrategy.setType(UserScore.class);

            MappingStrategy<User> userStrategy = new HeaderColumnNameMappingStrategy<>();
            userStrategy.setType(User.class);
            userStrategy.captureHeader(new CSVReader(new FileReader(AppSettings.ALL_USER_FILE)));

            StatefulBeanToCsvBuilder<User> beanBuilder = new StatefulBeanToCsvBuilder<>(writer);

            StatefulBeanToCsv<User> beanToCsv = beanBuilder.withMappingStrategy(userStrategy).build();
            beanToCsv.write(user);
            writer.close();*/
}
