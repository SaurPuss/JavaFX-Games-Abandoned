package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.user.UserManager;

import static settings.GUI.panes.UserSettingsPane.*;
import static settings.AppSettings.*;

public interface UpdateSettingsButton {

    default Button updateSettings() {
        Button button = new Button("Update");

        button.setOnAction(e -> {
            // Update the settings to the current session user
            user.getUserSettings().setRememberUser(toggleUser.isSelected());
            user.getUserSettings().setRememberPassword(togglePassword.isSelected());

            // Save to current user.dat
            UserManager.saveCurrentUser(user);
            System.out.println("UPDATE SETTINGS BUTTON: This doesn't save to the db, so it only works this session");
            // TODO Save to database too, if not anonymous anyway
            printUser();
        });

        return button;
    }
}
