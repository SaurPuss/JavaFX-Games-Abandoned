package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.AppSettings;
import settings.GUI.panes.GameSelectionPane;

public interface GameSelectionButton {

    default Button gameSelectionButton() {
        Button btnSelect = new Button("Play!");
        btnSelect.setDefaultButton(true);

        btnSelect.setOnAction(e -> {
            String selection = GameSelectionPane.dropdown.getSelectionModel().getSelectedItem();
            AppSettings.gameSelection(selection);
        });

        return btnSelect;
    }
}
