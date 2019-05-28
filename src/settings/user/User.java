package settings.user;

import settings.Session;
import settings.user.score.UserScore;

import java.io.*;
import java.util.Random;

/**
 * User specific settings and saved info go here
 */
public class User implements Serializable {
    private static final long serialVersionUID = 365729905314659904L;

    /* Data fields */
    private String userName;
    private String userPassword;
    private UserScore userScore;
    private boolean rememberPassword;
    private boolean rememberUser;
//    private UserSettings userSettings;

    /* Constructors */
    /**
     * Create a default User with a random name, no password.
     */
    public User() {
        userName = randomName();
        userPassword = null;
        userScore = new UserScore();
        rememberPassword = false;
        rememberUser = false;
//        userSettings = new UserSettings();
    }

    /**
     * Create a User object with a specified name and password.
     * @param name String for username
     * @param password String for password
     */
    public User(String name, String password) {
        this.userName = name;
        this.userPassword = password;
        userScore = new UserScore();
        rememberPassword = false;
        rememberUser = false;
    }

    /**
     * TODO is this real?
     * Create a fake user for errors?????
     */
    User(boolean bool) {
        if (!bool) {
            this.userName = null;
            this.userPassword = "PASSWORD DOES NOT MATCH";
            userScore = null;
            rememberPassword = false;
            rememberUser = false;
        } else {
            System.out.println("This should not be possible, terminating program.");
            System.exit(100);
        }
    }

    /* Getters and Setters */
    public String getUserName() { return this.userName; }
    void setUserName(String userName) {
        if ((userName.length() >= 6) && (!UserManager.findUserName(userName)))
            this.userName = userName;
    }

    // TODO figure this out - getUserPassword() obfuscate
    String getUserPassword() {
        // Do stuff
        return userPassword;
    }

    public UserScore getUserScore() { return userScore; }
    void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public boolean isRememberPassword() { return rememberPassword; }
    void setRememberPassword(boolean rememberPassword) { this.rememberPassword = rememberPassword; }
    public boolean isRememberUser() { return rememberUser; }
    public void setRememberUser(boolean rememberUser) { this.rememberUser = rememberUser; }

    /**
     * Static method to check if Session User is default
     * @return boolean default username
     */
    public static boolean isDefaultUser() throws NullPointerException {
        return Session.user.getUserName().contains("Anonymous ");
    }



    /**
     * Choose a random username.
     * @return String Combination of "Anonymous" and a random animal.
     */
    private String randomName() {
        String s = "Anonymous ";
        Random r = new Random();

        String[] animals = {
                "Badger", "Llama", "Gopher", "Giraffe", "Dolphin",
                "Horse", "Zebra", "Lion", "Bear", "Koala",
                "Tit", "Cat", "Dog", "Ferret", "Panther",
                "Tiger", "Raven", "Crow", "Dinosaur", "Potato"
        };
        int random = r.nextInt(animals.length);

        return s + animals[random];
    }

    /**
     * Override toString to give a basic output with User info. Excluding the password.
     * @return String Name, Total Score, Current Score, Highest Streak
     */
    public String toString() throws NullPointerException {
        return "Name: " + this.userName + "\nTotal Score: " + this.userScore.getTotalScore() +
        "\nCurrent Score: " + this.userScore.getCurrentScore() + "\nHighest Score Streak: " +
        this.userScore.getHighestStreak();
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
        System.out.println("no Object data");
    }
}