package settings.GUI.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import settings.GUI.panes.GameSelectionPane;
import settings.Session;
import settings.user.UserManager;

public interface LoginSignUpButtons {

    /**
     * Default login button, does a clicky-click to try and log in
     * with given credentials.
     * @param username
     * @param password
     * @return
     */
    default Button loginButton(String username, String password) {
        Button btnLogin = new Button("Log In");

        // Attach event to the button
        btnLogin.setOnAction(e -> {
            // Set user as user
            Session.user = UserManager.getUserProfile(username, password);

            // Continue to Game SelectionPane
            Session.pane.setCenter(new GameSelectionPane());

        });

        return btnLogin;
    }






    /**
     * Default functionality for a login Button
     * @param username User to retrieve
     * @param password Validation for correct user
     */
    /*default boolean loginButton(String username, String password) {
        // Check if User exists and get user information
        if (UserManager.getUserProfile(username)) {
            // assuming the password is correct

            return true;

        } else {
            System.out.println("Username does not exist.");
            return false;
        }
    }*/

    /**
     * Default functionality for a sign up button
     * @param username name to check against & create
     * @param password Set password
     */
    default Button signUpButton(String username, String password) {
        if ((username == null) || (username.equals(""))) {
            System.out.println("Please choose a username");
        } else if (username.length() < 6) {
            System.out.println("Username is too short, please try again.");
        } else if (UserManager.findUserName(username)) {
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

        return new Button();
    }
}
