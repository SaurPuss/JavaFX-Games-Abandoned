package settings.GUI.panes;

import game.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import settings.AppSettings;
import settings.GUI.buttons.GameSelectionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class GameSelectionPane extends VBox implements GameSelectionButton {
    public static ComboBox<String> dropdown;
    public GameSelectionPane() {
        // TODO On first session maybe give a reminder to check their settings for difficulty etc?

        HBox box = new HBox(0);
        box.setAlignment(Pos.CENTER);

        dropdown = new ComboBox<>();
        dropdown.getItems().addAll(gameSelectionList());
        dropdown.getSelectionModel().selectFirst();

        Text intro = new Text("This is where stuff goes to choose a game");

        box.getChildren().addAll(dropdown, gameSelectionButton());

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(intro, box);

        // Fallback to make sure there is no bottom bar in case I forgot
        if (AppSettings.pane.getBottom() != null) {
            System.out.println("GAME SELECTION PANE: Yo moron, you forgot to disable to bottom bar");
            AppSettings.pane.setBottom(null);
        }
    }


    /**
     * Add all the games in the program to this list.
     * @return ArrayList of hard coded game names
     */
    private ArrayList<String> gameSelectionList() {
        Reflections reflections = new Reflections(ClasspathHelper.forPackage("game"));
        Set<Class<? extends Game>> classes = reflections.getSubTypesOf(Game.class);
        ArrayList<String> gameList = new ArrayList<>();

        for (Class<? extends Game> aClass : classes) {
            gameList.add(StringUtils.substringAfterLast(aClass.getName(),"."));
        }

        Collections.sort(gameList);

        return gameList;
    }
}
