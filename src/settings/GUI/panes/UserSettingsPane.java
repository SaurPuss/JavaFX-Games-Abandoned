package settings.GUI.panes;

import javafx.scene.layout.Pane;
import org.controlsfx.control.ToggleSwitch;
import settings.Session;

public class UserSettingsPane extends Pane {


    public UserSettingsPane() {
        ToggleSwitch toggleUser = new ToggleSwitch();
        ToggleSwitch togglePassword = new ToggleSwitch();

        toggleUser.setSelected(Session.user.isRememberUser());
        togglePassword.setSelected(Session.user.isRememberPassword());


        getChildren().addAll(toggleUser, togglePassword);



    }
}
