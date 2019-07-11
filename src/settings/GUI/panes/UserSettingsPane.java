package settings.GUI.panes;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.ToggleSwitch;
import settings.GUI.buttons.UpdateSettingsButton;
import static settings.AppSettings.*;

public class UserSettingsPane extends VBox implements UpdateSettingsButton {
    public static ToggleSwitch toggleUser;
    public static ToggleSwitch togglePassword;

    public UserSettingsPane() {
        // Set up GUI
        toggleUser = new ToggleSwitch();
        togglePassword = new ToggleSwitch();

        // Adjust to user preferences
        toggleUser.setSelected(user.getUserSettings().isRememberUser());
        togglePassword.setSelected(user.getUserSettings().isRememberPassword());


        // Game difficulty toggle button
        // TODO You can't change your difficulty while a game is active

        getChildren().addAll(
                rememberUserBox(),
                rememberPasswordBox(),
                updateSettings());

        // TODO if default user, Sign up Button
    }

    private HBox rememberUserBox() {
        HBox box = new HBox(5);
        Text tRememberUser = new Text("Remember User: ");
        box.getChildren().addAll(tRememberUser, toggleUser);

        return box;
    }

    private HBox rememberPasswordBox() {
        HBox box = new HBox(5);
        Text tRememberPassword = new Text("Remember Password: ");
        box.getChildren().addAll(tRememberPassword, togglePassword);

        return box;
    }
}
