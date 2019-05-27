package settings.GUI;

import settings.user.User;
import settings.user.UserManager;

public interface Loginterface {
    /**
     * Default functionality for a login Button
     * @param username User to retrieve
     * @param password Validation for correct user
     */
    default User login(String username, String password) {
        User user = new User();
        // Check if User exists and get user information
        if (UserManager.findUser(username)) {
            // assuming the password is correct
            user = UserManager.retrieveUser(username, password);

        }

        // continue to following screen


        return user;
    }

    /**
     * Default functionality for a sign up button
     * @param username name to check against & create
     * @param password Set password
     */
    default void signUp(String username, String password) {
        if ((username == null) || (username.equals(""))) {
            System.out.println("Please choose a username");
        } else if (UserManager.findUser(username)) {
            System.out.println("Username already exists, pls make a new profile or try login");
        } else if ((password == null) || (password.equals(""))) {
            // do a thing for minimum password length etc
            System.out.println("Please enter a password");
        } else if (password.length() < 6) {
            System.out.println("Please make sure your password is at least 6 characters long");
        } else if (username.contains("Anonymous ")) {
            // default users should basically not exist
            System.out.println("Can't save a default user, please enter a password or new username");
        } else {
            // Invoke
            UserManager.saveNewUser(username,password);
        }
    }
}
