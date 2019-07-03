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

        HBox pRememberUser = new HBox(5);
        Text tRememberUser = new Text("Remember User: ");
        pRememberUser.getChildren().addAll(tRememberUser, toggleUser);

        HBox pRememberPassword = new HBox(5);
        Text tRememberPassword = new Text("Remember Password: ");
        pRememberPassword.getChildren().addAll(tRememberPassword, togglePassword);

        // Adjust to current preferences
        toggleUser.setSelected(user.userSettings.isRememberUser());
        togglePassword.setSelected(user.userSettings.isRememberPassword());


        // Game difficulty toggle button
        // TODO You can't change your difficulty while a game is active

        getChildren().addAll(pRememberUser, pRememberPassword,updateSettings());

        // Give the Option to save
//        getChildren().add();

        // TODO if default user, Sign up Button
    }
}
