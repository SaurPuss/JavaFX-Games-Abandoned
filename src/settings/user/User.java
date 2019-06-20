package settings.user;

import com.opencsv.bean.*;
import settings.user.score.UserScore;

import java.io.*;
import java.util.Random;

import static settings.Session.*;

/**
 * User specific settings and saved info go here
 */
public class User implements Serializable {
    private static final long serialVersionUID = SERIAL_VERSION_UID;
    /* Data fields */
    @CsvBindByName(column = "username", required = true)
    private String userName;
    @CsvBindByName(column = "password")
    private String userPassword;
    // TODO move to User Settings
    // TODO add toggle button with light/dark mode << style sheets <3
    @CsvBindByName(column = "rememberPassword")
    private boolean rememberPassword;
    @CsvBindByName(column = "rememberUser")
    private boolean rememberUser;
//    @CsvCustomBindByName(column = "scores", converter = UserScoreToBean.class)
//    private UserScore userScore;
    @CsvBindByName(column = "totalScore")
    private int totalScore;
    @CsvBindByName(column = "highestStreak")
    private int highestStreak;
    private int currentStreak;

    private static String[] anonymousName = {
            "Anonymous Badger", "Anonymous Llama", "Anonymous Gopher",
            "Anonymous Giraffe", "Anonymous Dolphin", "Anonymous Horse",
            "Anonymous Zebra", "Anonymous Lion", "Anonymous Bear",
            "Anonymous Koala", "Anonymous Tit", "Anonymous Cat",
            "Anonymous Dog", "Anonymous Ferret", "Anonymous Panther",
            "Anonymous Tiger", "Anonymous Raven", "Anonymous Crow",
            "Anonymous Dinosaur", "Anonymous Potato"};

    /* Constructors */
    /**
     * Create a default User with a random name, no password.
     */
    public User() {
        userName = randomName();
        userPassword = null;
//        userScore = new UserScore();
        rememberPassword = false;
        rememberUser = false;
        totalScore = 0;
        highestStreak = 0;
        currentStreak = 0;
    }

    /**
     * Create a User object with a specified name and password.
     * @param name String for username
     * @param password String for password
     */
    public User(String name, String password, boolean rememberUser) {
        this.userName = name;
        this.userPassword = password;
//        userScore = new UserScore();
        rememberPassword = false;
        this.rememberUser = rememberUser;
        totalScore = 0;
        highestStreak = 0;
        currentStreak = 0;
    }

    /* Getters and Setters */
    public String getUserName() { return this.userName; }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // TODO figure this out - getUserPassword() obfuscate
    public String getUserPassword() {
        // Do stuff
        return userPassword;
    }

//    UserScore getUserScore() { return userScore; }
//    void setUserScore(UserScore userScore) { this.userScore = userScore; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public boolean isRememberPassword() { return rememberPassword; }
    public void setRememberPassword(boolean rememberPassword) { this.rememberPassword = rememberPassword; }
    public boolean isRememberUser() { return rememberUser; }
    public void setRememberUser(boolean rememberUser) { this.rememberUser = rememberUser; }
    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }
    public int getHighestStreak() { return highestStreak; }
    public void setHighestStreak(int highestStreak) { this.highestStreak = highestStreak; }
    public int getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(int currentStreak) { this.currentStreak = currentStreak; }

    /**
     * Static method to check if Session User is default
     * @return boolean default username
     */
    public static boolean isDefaultUser() {
        try {
            System.out.println("USER: Checking for default user");
            return user.getUserName().contains("Anonymous ");
        } catch (NullPointerException e) {
            System.out.println("USER: NullPointerException in isDefaultUser");
        }

        return true;
    }

    /**
     * Choose a random username.
     * @return random word from anonymousName String array.
     */
    private String randomName() {
        Random r = new Random();
        int random = r.nextInt(anonymousName.length);

        return anonymousName[random];
    }

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
        return "Name: " + this.userName + "\nTotal Score: " + this.totalScore +
        "\nCurrent Score: " + this.currentStreak + "\nHighest Score Streak: " +
        this.highestStreak;
    }

    /**
     * Implement Serializable Interface methods
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
