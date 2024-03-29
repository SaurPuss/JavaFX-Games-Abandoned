package settings.GUI.panes;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import game.GUI.buttons.GamePaneButton;
import settings.GUI.buttons.UserScoreButton;
import settings.GUI.buttons.UserSettingsButton;
import settings.AppSettings;

public class TopBarPane extends BorderPane implements UserSettingsButton, UserScoreButton, GamePaneButton {
    public TopBarPane() {
        Label userName = new Label("Hi, " + AppSettings.user.getName());
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
