package settings.GUI.panes;

import game.Game;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.control.ToggleSwitch;
import settings.GUI.buttons.LogOutButton;
import settings.GUI.buttons.UpdateSettingsButton;
import settings.user.settings.GameDifficulty;

import java.util.EnumSet;

import static org.controlsfx.control.textfield.TextFields.*;
import static settings.AppSettings.*;

public class UserSettingsPane extends VBox implements UpdateSettingsButton, LogOutButton {
    public static ToggleSwitch toggleUser = new ToggleSwitch();
    public static ToggleSwitch togglePassword = new ToggleSwitch();
    public static TextField tfName = createClearableTextField();
    public static TextField tfPassword = createClearablePasswordField();

    private static GridPane fields = new GridPane();

    static {
        fields.add(new Text("Remember Username: "), 0, 0, 4, 1);
        fields.add(toggleUser, 8, 0, 2, 1);
        fields.add(new Text("Remember Password: "), 0,1, 4, 1);
        fields.add(togglePassword, 8, 1, 2, 1);
    }

    public UserSettingsPane() {
        // Adjust to user preferences
        toggleUser.setSelected(user.getUserSettings().isRememberUser());
        togglePassword.setSelected(user.getUserSettings().isRememberPassword());



        // ENUM TOGGLE GROUP WOO
        EnumSet<GameDifficulty> enumeration = EnumSet.allOf(GameDifficulty.class);
        SegmentedButton segmentedButton = new SegmentedButton();

        for (Enum<GameDifficulty> enumElement : enumeration) {
            ToggleButton btn = new ToggleButton(enumElement.toString());
            btn.setUserData(enumElement);
            segmentedButton.getButtons().add(btn);
            if (enumElement.equals(user.getUserSettings().getGameDifficulty())) {
                btn.setSelected(true);
            }
        }

        // Game difficulty toggle button
        // TODO You can't change your difficulty while a game is active

        getChildren().addAll(fields, segmentedButton, updateSettings());

        // TODO if default user, Sign up Button
    }
}
