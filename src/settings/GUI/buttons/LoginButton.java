package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.GUI.panes.GameSelectionPane;
import settings.Session;
import settings.user.User;
import settings.user.UserManager;

public interface LoginButton {
    // TODO Maybe separate these buttons

    /**
     * Default login button, does a clicky-click to try and log in
     * with given credentials.
     * @param username
     * @param password
     * @return
     */
    default Button login(String username, String password) {
        Button btnLogin = new Button("Log In");

        // Attach event to the button
        btnLogin.setOnAction(e -> {
            // Check if user exists in the database
            if (UserManager.findUserName(username)) {
                // Set user as user
                Session.user = UserManager.getUserProfile(username, password);

                // If password doesn't match?


                // Continue to game selection screen
                Session.pane.setCenter(new GameSelectionPane());


            } else
                System.out.println("LOGIN BUTTON: User does not exist in database");

        });

        return btnLogin;
    }
}
