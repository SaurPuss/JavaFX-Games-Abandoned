package settings.user;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import settings.GUI.panes.LoginPane;
import settings.Session;
import settings.user.score.UserScore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        // TODO CSV TO BEAN PLS
        try {
            CSVReader reader = new CSVReader(new FileReader(Session.ALL_USER_FILE));

            CsvToBean csvToBean = new CsvToBeanBuilder(new FileReader(Session.ALL_USER_FILE))
                    .withSeparator(',').withSkipLines(1).withType(User.class).withType(UserScore.class).build();


            List<User> list = csvToBean.parse();

            for (User user : list)
                System.out.println(user.toString());
//            CsvToBeanBuilder<User> beanBuilder = new CsvToBeanBuilder<>(new InputStreamReader(new FileInputStream(Session.ALL_USER_FILE)));
//
//            beanBuilder.withType(User.class);
//            // build methods returns a list of Beans
//            beanBuilder.build().parse().forEach(e -> System.out.println(csvToBean.toString()));

        } catch (IOException e) {

            e.printStackTrace();
        }

        System.out.println("USER MANAGER: retrieve user failed to parse the bean");
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
    public static void saveNewUser(String username, String password, boolean rememberUser) {
        User user = new User(username, password, rememberUser);
        System.out.println("USER MANAGER: Saving new user to users.csv.");

        if (LoginPane.cbRememberUser.isSelected()) {
            UserManager.saveCurrentUser(user);
            System.out.println("USER MANAGER: Saving new user to currentUser.dat");
        } else {
            System.out.println("USER MANAGER: Not saving new user to currentUser.dat");
            UserManager.resetCurrentUser();
        }

        // Save user to database
        saveNewUser(user);

        Session.user = user;
        System.out.println("USER MANAGER: Set Session.user as newly created user");
    }


    public static void saveUser(User user) {
        if (findUserName(user.getUserName())) {
            updateUser(user);
        } else {
            saveNewUser(user);
        }
    }

    private static void updateUser(User user) {

    }

    /**
     * Save NEW user to users.csv
     * @param user User information to append to users.csv
     */
    private static void saveNewUser(User user) {


        try{

            CSVWriter writer = new CSVWriter(new FileWriter(Session.ALL_USER_FILE, true));

//            Writer writer = new FileWriter(Session.ALL_USER_FILE);
            /*HeaderColumnNameMappingStrategy<User> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(User.class);

            StatefulBeanToCsvBuilder<User> builder = new StatefulBeanToCsvBuilder(writer);
            StatefulBeanToCsv beanWriter = builder
                    .withMappingStrategy(strategy)
                    .withSeparator(',').build();

            beanWriter.write(user);*/
            /*StatefulBeanToCsvBuilder<User> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<User> beanWriter = builder.build();
            beanWriter.write(user);
            writer.close();*/


            String[] record = {
                    user.getUserName(),
                    user.getUserPassword(),
                    String.valueOf(user.getUserScore().getTotalScore()),
                    String.valueOf(user.getUserScore().getHighestStreak()),
                    String.valueOf(user.getUserScore().getCurrentScore()),
                    String.valueOf(user.isRememberPassword()),
                    String.valueOf(user.isRememberUser())
            };

            writer.writeNext(record);

            writer.close();
        } catch (Exception e) {
            System.out.println("USER MANAGER: failed saveNewUser(user)!");
            e.printStackTrace();
        }
    }


    // TODO make this a working thing
    public void updateSavedUser(User user) {
        /*try {
            File inputFile = new File(Session.ALL_USER_FILE);

            // Read existing file
            CSVReader reader = new CSVReader(new FileReader(inputFile));
            List<String[]> csvBody = reader.readAll();
            // get CSV row column and replace with by using row and column
            for (int i = 0; i < csvBody.size(); i++) {
                String[] strArray = csvBody.get(i);
                for (int j = 0; j < strArray.length; j++) {
                    if (strArray[j].equalsIgnoreCase("Update_date")) { //String to be replaced
                        csvBody.get(i)[j] = "Updated_date"; //Target replacement
                    }
                }
            }
            reader.close();

            // Write to CSV file which is open
            CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
