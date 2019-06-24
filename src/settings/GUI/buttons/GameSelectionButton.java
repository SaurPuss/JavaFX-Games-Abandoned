package settings.GUI.buttons;

import game.hangman.Hangman;
import game.minesweeper.Minesweeper;
import javafx.scene.control.Button;
import settings.Session;

public interface GameSelectionButton {

    default Button gameSelectionButton(String selection) {
        Button btnSelect = new Button("Play!");
        btnSelect.setDefaultButton(true);

        btnSelect.setOnAction(e -> Session.gameSelection(selection));

        return btnSelect;
    }
}
