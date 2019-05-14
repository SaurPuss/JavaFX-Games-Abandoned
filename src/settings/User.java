package settings;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * User specific settings and saved info go here
 */
public class User {
    // Data
    private String userName;
    private String userPassword;
    private int totalScore;
    private int currentScore;
    private int highestStreak;
    private static final File USER_FILE = new File("src/assets/users.txt");

    // Constructors
    /**
     * Default User with a random name, no password.
     */
    User() {
        userName = randomName();
        userPassword = null;
        totalScore = 0;
        currentScore = 0;
        highestStreak = 0;
    }

    /**
     * Create a user with a specified name and password.
     * @param name String for username
     * @param password String for password
     */
    public User(String name, String password) {
        this.userName = name;
        this.userPassword = password;
        totalScore = 0;
        currentScore = 0;
        highestStreak = 0;
    }

    // Getters and Setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    // TODO figure this out - getUserPassword()
    private String getUserPassword() {
        // Do stuff
        return userPassword;
    }

    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }
    public int getCurrentScore() { return currentScore; }
    public void setCurrentScore(int currentScore) { this.currentScore = currentScore; }
    public int getHighestStreak() { return highestStreak; }
    public void setHighestStreak(int highestStreak) { this.highestStreak = highestStreak; }

    // Methods
    /**
     * Save User
     */
    private void saveUser(User user) throws IOException {
        // TODO Make this a secure file
        if(USER_FILE.createNewFile())
            System.out.println("New users.txt created");
        else
            System.out.println("users.txt already exists");



    }

    /**
     * Get a saved User from the file.
     * @param userName Input userName
     * @param userPassword Input userPassword
     * @return User Return the last saved copy of the User
     */
    public User getUser(String userName, String userPassword) {
        if (!USER_FILE.exists()) {
            System.out.println("User File does not exist");
            // TODO make sure a new file and user are created
            return null;
        }


        // TODO retrieve user and scores
        User temp = new User(userName, userPassword); // This needs to be actually retrieving



        return temp;
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
                "Tiger", "Raven", "Crow", "Dinosaur", "French Fry"
        };
        int random = r.nextInt(animals.length);

        return s + animals[random];
    }
}
