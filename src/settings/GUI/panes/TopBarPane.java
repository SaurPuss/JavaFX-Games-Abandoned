package settings.GUI.panes;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import settings.GUI.buttons.UserScoreButton;
import settings.GUI.buttons.UserSettingsButton;
import settings.Session;

public class TopBarPane extends BorderPane implements UserSettingsButton, UserScoreButton {
    public TopBarPane() {
        Text userName = new Text(Session.user.getUserName());

        // Add the buttons in their own box
        HBox buttons = new HBox(5);
        buttons.getChildren().addAll(userScoreButton(), userSettingsButton());

        // Prettify
        this.setLeft(userName);
        this.setRight(buttons);
        this.setPadding(new Insets(5));
    }
}
