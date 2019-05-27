package settings.GUI.panes;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import settings.GUI.buttons.UserScoreButton;
import settings.GUI.buttons.UserSettingsButton;
import settings.user.UserManager;

public class TopBarPane extends HBox implements UserSettingsButton, UserScoreButton {

    public TopBarPane() {
        Text userName = new Text(UserManager.user.getUserName());
    }
}
