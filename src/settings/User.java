package settings;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 * User specific settings and saved info go here
 */
public class User {
    /* Data fields */
    private String userName;
    private String userPassword;
    private int totalScore;
    private int currentScore;
    private int highestStreak;
    private static final String FILE = "src/assets/users.txt";

    /* Constructors */
    /**
     * Default User with a random name, no password.
     */
    public User() {
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

    /* Getters and Setters */
    String getUserName() { return userName; }
    void setUserName(String userName) { this.userName = userName; }

    // TODO figure this out - getUserPassword() obfuscate
    String getUserPassword() {
        // Do stuff
        return userPassword;
    }

    void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    int getTotalScore() { return totalScore; }
    void setTotalScore(int totalScore) { this.totalScore = totalScore; }
    int getCurrentScore() { return currentScore; }
    void setCurrentScore(int currentScore) { this.currentScore = currentScore; }
    int getHighestStreak() { return highestStreak; }
    void setHighestStreak(int highestStreak) { this.highestStreak = highestStreak; }

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
        return "Name: " + this.userName + "\nTotal Score: " + this.totalScore +
        "\nCurrent Score: " + this.currentScore + "\nHighest Score Streak: " +
        this.highestStreak;
    }
}
