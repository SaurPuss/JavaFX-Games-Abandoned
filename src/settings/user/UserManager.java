package settings.user;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import settings.Session;
import settings.user.score.UserScore;

import java.io.*;

// TODO password hashing and encryption
public class UserManager implements Serializable {
    private static final long serialVersionUID = Session.SERIAL_VERSION_UID;

    /**
     * Match current User to database.
     * @return Saved user in currentUser.dat matches one of the users in users.csv
     */
    public static boolean matchCurrentUser() { return findUserName(getCurrentUser().getUserName()); }


    public static boolean matchPassword(String username, String password) {
        try {
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
                System.out.println("USER MANAGER: Retrieving user from database");
                return retrieveUser(userName, userPassword);
            } else {
                System.out.println("USER MANAGER: Password mismatch, can't retrieve user from csv.");
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
            CSVReader reader = new CSVReader(new FileReader(Session.ALL_USER_FILE));

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].toLowerCase().equals(userName.toLowerCase())) {
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
            CSVReader reader = new CSVReader(new FileReader(Session.ALL_USER_FILE));

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
        // TODO OPENCSV TO (NESTED)BEAN PLS
        try {
            CSVReader reader = new CSVReader(new FileReader(Session.ALL_USER_FILE));

            User temp = new User();
            UserScore score = new UserScore();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if ((nextLine[0].toLowerCase().equals(userName.toLowerCase())) &&
                        (nextLine[1].equals(userPassword))) {
                    // USERNAME
                    temp.setUserName(nextLine[0]);
                    // PASSWORD
                    temp.setUserPassword(nextLine[1]);
                    // REMEMBERPASSWORD
                    temp.setRememberPassword(Boolean.valueOf(nextLine[2]));
                    // REMEMBERUSER
                    temp.setRememberUser(Boolean.valueOf(nextLine[3]));

                    // CURRENTSCORE
                    score.setCurrentScore(Integer.valueOf(nextLine[4]));
                    // HIGHESTSTREAK
                    score.setHighestStreak(Integer.valueOf(nextLine[5]));
                    // TOTALSCORE
                    score.setTotalScore(Integer.valueOf(nextLine[6]));

                    temp.setUserScore(score);

                    return temp;
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
//        System.out.println("USER MANAGER: Saving current user");
        try {
            FileOutputStream f = new FileOutputStream(Session.CURRENT_USER_FILE, false);
            ObjectOutputStream o = new ObjectOutputStream(f);

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
            Session.user = user;
            Session.printSessionUser();
            if (user.isRememberUser()) {
                saveCurrentUser(user);
                Session.printSavedUser();
            }
        }
    }

    private static void updateUser(User user) {


    }

    /**
     * Save NEW user to users.csv
     * @param user User information to append to users.csv
     */
    private static void saveNewUser(User user) {
        try {
            Writer writer = new FileWriter(Session.ALL_USER_FILE);

            MappingStrategy<UserScore> scoreStrategy = new HeaderColumnNameMappingStrategy<>();
            scoreStrategy.setType(UserScore.class);

            MappingStrategy<User> userStrategy = new HeaderColumnNameMappingStrategy<>();
            userStrategy.setType(User.class);

            StatefulBeanToCsvBuilder<User> beanBuilder = new StatefulBeanToCsvBuilder<>(writer);

            StatefulBeanToCsv<User> beanToCsv = beanBuilder.build();
            beanToCsv.write(user);
            writer.close();


/*            CSVWriter writer = new CSVWriter(new FileWriter(Session.ALL_USER_FILE, true));

            String[] record = {
                    user.getUserName(),
                    user.getUserPassword(),
                    String.valueOf(user.isRememberPassword()),
                    String.valueOf(user.isRememberUser()),
                    String.valueOf(user.getUserScore().getTotalScore()),
                    String.valueOf(user.getUserScore().getHighestStreak()),
                    String.valueOf(user.getUserScore().getCurrentScore()),

            };

            writer.writeNext(record);

            writer.close();*/
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
}
