package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.user.User;
import settings.user.UserManager;

public interface SignUpButton {
    /**
     * Default functionality for a sign up button
     * @param username name to check against & create
     * @param password Set password
     */
    default Button signUp(String username, String password) {
        if ((username == null) || (username.equals(""))) {
            System.out.println("SIGN UP: Please choose a username");
        } else if (User.isRandomName(username)) {
            System.out.println("SIGN UP: You have a default username, please change it");
        } else if (username.length() < 6) {
            System.out.println("SIGN UP: Username is too short, please try again.");
        } else if (UserManager.findUserName(username)) {
            System.out.println("SIGN UP: Username already exists, pls make a new profile or try login");
        } else if ((password == null) || (password.equals(""))) {
            // do a thing for minimum password length etc
            System.out.println("SIGN UP: Please enter a password");
        } else if (password.length() < 6) {
            System.out.println("SIGN UP: Please make sure your password is at least 6 characters long");
        } else {
            // Invoke
            UserManager.saveNewUser(username,password);
            System.out.println("SIGN UP: saving user");
        }

        return new Button();
    }
}
