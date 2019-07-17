package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.GUI.panes.*;

import static settings.AppSettings.pane;

public interface ContinueAnonymousButton {

    /**
     * Button displayed on the Login Pane if currentUser.dat
     * holds one of the default User profiles
     * @return Button to skip the login/signup parts
     */
    default Button continueAnonymous() {
        Button btnContinue = new Button("Remain Anonymous");

        btnContinue.setOnAction(e -> {
            pane.setTop(new TopBarPane());
            pane.setCenter(new GameSelectionPane());
        });

        return btnContinue;
    }
}
