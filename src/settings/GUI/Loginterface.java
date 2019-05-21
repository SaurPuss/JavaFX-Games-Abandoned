package settings.GUI;

import settings.User;
import settings.UserManager;

public interface Loginterface {
    /**
     * Default functionality for a login Button
     * @param username User to retrieve
     * @param password Validation for correct user
     */
    default void login(String username, String password) {
        User user = new User();
        // Check if User exists and get user information
        if (UserManager.findUser(username)) {
            user = UserManager.retrieveUser(username, password);
        }

        // Set this user to active and let them play a game?
    }

    /**
     * Default functionality for a sign up button
     * @param username User to check against & create
     * @param password Set password
     */
    default void signUp(String username, String password) {
        if (!UserManager.findUser(username)) {
            // Save new user
            UserManager.saveNewUser(username,password);
        } else {
            // TODO create a new user
            System.out.println("this method needs to try to create a new user with a different name instead");
        }
    }
}
