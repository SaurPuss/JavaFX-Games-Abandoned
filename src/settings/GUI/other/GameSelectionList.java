package settings.GUI.other;

import java.util.ArrayList;

public interface GameSelectionList {

    /**
     * // TODO maybe put this in the Session?
     * Add all the games in the program to this list.
     * @return all available games
     */
    default ArrayList<String> gameSelectionList() {
        ArrayList<String> gameList = new ArrayList<>();

        gameList.add("Hangman");
        gameList.add("Minesweeper");

        return gameList;
    }
}
