package settings.GUI.panes;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import settings.GUI.buttons.GamePaneButton;
import settings.GUI.buttons.UserScoreButton;
import settings.GUI.buttons.UserSettingsButton;
import settings.Session;

public class TopBarPane extends BorderPane implements UserSettingsButton, UserScoreButton, GamePaneButton {
    public TopBarPane() {
        Label userName = new Label("Hi, " + Session.user.getUserName());
        userName.setPadding(new Insets(7));

        // Add the buttons in their own box
        HBox buttons = new HBox(5);
        buttons.getChildren().addAll(gamePaneButton(), userScoreButton(), userSettingsButton());

        // Prettify
        this.setLeft(userName);
        this.setRight(buttons);
        this.setPadding(new Insets(5));
    }
}
