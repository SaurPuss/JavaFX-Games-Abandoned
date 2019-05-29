package settings.GUI.buttons;

import javafx.scene.control.Button;

public interface ContinueAnonymousButton {

    /**
     * Button displayed on the Login Pane if currentUser.dat
     * holds one of the default User profiles
     * @return Button to skip the login/signup parts
     */
    default Button continueAnonymous() {
        // Go to game selection screen


        // TODO add real functionality here
        // Add Save User to Settings Pane

        // Ask for save on window.close or whatever that is called


        return new Button();
    }
}
