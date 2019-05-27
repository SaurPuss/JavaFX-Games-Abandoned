package settings.user;

import settings.score.UserScore;

import java.util.Random;

/**
 * User specific settings and saved info go here
 */
public class User {
    /* Data fields */
    private String userName;
    private String userPassword;
    private UserScore userScore;
    private boolean rememberPassword;
    private UserSettings userSettings;
    private static final String FILE = "src/assets/users.txt";

    /* Constructors */
    /**
     * Create a default User with a random name, no password.
     */
    public User() {
        userName = randomName();
        userPassword = null;
        userScore = new UserScore();
        rememberPassword = false;
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
    }

    /* Getters and Setters */
    public String getUserName() { return userName; }
    void setUserName(String userName) { this.userName = userName; }

    // TODO figure this out - getUserPassword() obfuscate
    String getUserPassword() {
        // Do stuff
        return userPassword;
    }

    public UserScore getUserScore() { return userScore; }
    void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public boolean isRememberPassword() { return rememberPassword; }
    void setRememberPassword(boolean rememberPassword) { this.rememberPassword = rememberPassword; }

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
    public String toString() {
        return "Name: " + this.userName + "\nTotal Score: " + this.userScore.getTotalScore() +
        "\nCurrent Score: " + this.userScore.getCurrentScore() + "\nHighest Score Streak: " +
        this.userScore.getHighestStreak();
    }
}
