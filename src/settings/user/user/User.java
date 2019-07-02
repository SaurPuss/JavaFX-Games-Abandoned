package settings.user.user;

import java.io.*;
import java.util.Random;

import static settings.AppSettings.*;

/**
 * User specific settings and saved info go here
 */
public class User implements Serializable {
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    /* Object Data Fields */
    private long id; // Primary Key, created on sql insert
    private String name;
    private String password;
    public UserScore userScore;
    public UserSettings userSettings;

    /* Constructors */
    /**
     * Create a default User with a random name, no password.
     */
    public User() {
        id = 0;
        name = randomName();
        password = null;
        userScore = new UserScore();
        userSettings = new UserSettings();
    }

    /**
     * Create a User object with a specified name and password.
     * @param name String for username
     * @param password String for password
     * @param rememberUser boolean save current user
     */
    public User(String name, String password, boolean rememberUser) {
        id = 0;
        this.name = name;
        this.password = password;
        userScore = new UserScore();
        userSettings = new UserSettings(rememberUser);
    }

    /**
     * Full User object. Created for retrieval from the database.
     * @param id database Primary Key
     * @param name user name
     * @param password user password
     * @param userScore user scores object
     * @param userSettings user settings object
     */
    public User(long id, String name, String password, UserScore userScore, UserSettings userSettings) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userScore = userScore;
        this.userSettings = userSettings;
    }

    /* Getters and Setters */
    public long getId() { return this.id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    // TODO Password hashing
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    /* Other methods */
    /**
     * Static method to check if AppSettings User is default
     * @return boolean default username
     */
    public static boolean isDefaultUser() {
        try {
            System.out.println("USER: Checking for default user");
            return user.getName().contains("Anonymous ");
        } catch (NullPointerException e) {
            System.out.println("USER: AppSettings User cannot be null!");
        }

        // fallback because the method requires it
        return true;
    }

    /**
     * Choose a random username.
     * @return random word from String[] anonymousName.
     */
    private String randomName() {
        Random r = new Random();
        int random = r.nextInt(anonymousName.length);

        return anonymousName[random];
    }
    private static String[] anonymousName = {
            "Anonymous Badger", "Anonymous Llama", "Anonymous Gopher",
            "Anonymous Giraffe", "Anonymous Dolphin", "Anonymous Horse",
            "Anonymous Zebra", "Anonymous Lion", "Anonymous Bear",
            "Anonymous Koala", "Anonymous Tit", "Anonymous Cat",
            "Anonymous Dog", "Anonymous Ferret", "Anonymous Panther",
            "Anonymous Tiger", "Anonymous Raven", "Anonymous Crow",
            "Anonymous Dinosaur", "Anonymous Potato"};

    /**
     * Check if a name matches any entries in the anonymousName String array.
     * @param userName String to match with the array
     * @return boolean is a match or not
     */
    public static boolean isRandomName(String userName) {
        for (String s : anonymousName) {
            if (s.toLowerCase().equals(userName.toLowerCase()))
                return true;
        }

        return false;
    }

    /**
     * Override toString to give a basic output with User info. Excluding the password.
     * @return String Name, Total Score, Current Score, Highest Streak
     */
    @Override
    public String toString() {
        return  "Name: " + this.name +
                "\nTotal Score: " + this.userScore.getTotal() +
                "\nCurrent Streak: " + this.userScore.getStreak() +
                "\nHangman Score Streak: " + this.userScore.getHangman() +
                "\nHangman Score Total: " + this.userScore.getHangmanTotal() +
                "\nMineSweeper Score Streak: " + this.userScore.getMinesweeper() +
                "\nMineSweeper Score Total: " + this.userScore.getHangmanTotal() +
                "\nRemember User: " + this.userSettings.isRememberUser() +
                "\nRemember Password: " + this.userSettings.isRememberPassword() +
                "\nGame Difficulty: " + this.userSettings.getGameDifficulty();
    }

    /**
     * Implement Serializable Interface methods
     *  - writeObject(ObjectOutputStream)
     *  - readObject(ObjectInputStream)
     *  - readObjectNoData()
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("USER: no Object data");
    }
}
