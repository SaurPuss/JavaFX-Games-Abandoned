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
    private UserSettings userSettings;
    private UserScore userScore;

    /* Constructors */
    /**
     * Create a default User with a random name, no password.
     */
    public User() {
        id = 0;
        name = randomName();
        password = null;
        userSettings = new UserSettings();
        userScore = new UserScore();
    }

    /**
     * Create a User object based on a known id, generally one that will
     * match a username and password combination stored in the database.
     * @param id retrieved from database
     * @param name user input for the purpose of retrieval
     * @param password user input for the purpose of retrieval
     * @param rememberUser this user will be called on next program start
     */
    public User(long id, String name, String password, boolean rememberUser) {
        this.id = id;
        this.name = name;
        this.password = password;
        userSettings = new UserSettings(rememberUser);
        userScore = new UserScore();
    }

    /**
     * Create a User object from database retrieval.
     * @param id retrieved from database
     * @param name retrieved from database
     * @param password retrieved from database
     * @param userSettings compiled from database
     * @param userScore compiled from database
     */
    public User(long id, String name, String password, UserSettings userSettings, UserScore userScore) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userSettings = userSettings;
        this.userScore = userScore;
    }

    /* Getters and Setters */
    public long getId() { return this.id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; } // TODO Password hashing
    public String getPassword() { return password; }
    public UserSettings getUserSettings() { return userSettings; }
    public void setUserSettings(UserSettings userSettings) { this.userSettings = userSettings; }
    public UserScore getUserScore() { return userScore; }
    public void setUserScore(UserScore userScore) { this.userScore = userScore; }

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
     * Choose a random prefab username.
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
     * @param name String to match with the array
     * @return boolean is a match or not
     */
    public static boolean isRandomName(String name) {
        for (String s : anonymousName) {
            if (s.toLowerCase().equals(name.toLowerCase()))
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
        return    "ID: " + this.id
                + "Name: " + this.name
                + this.userSettings.toString()
                + this.userScore.toString();
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
