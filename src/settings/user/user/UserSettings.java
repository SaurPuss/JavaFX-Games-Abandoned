package settings.user.user;

import settings.user.settings.ColorMode;
import settings.user.settings.GameDifficulty;

import java.io.*;

import static settings.AppSettings.*;

public class UserSettings implements Serializable {
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    /* Object Data Fields */
    private boolean rememberPassword;
    private boolean rememberUser;
    private ColorMode colorMode;
    private GameDifficulty gameDifficulty;

    /* Constructors */
    public UserSettings() {
        rememberPassword = false;
        rememberUser = false;
        colorMode = ColorMode.LIGHT;
        gameDifficulty = GameDifficulty.NORMAL;
    }

    // TODO I can probably kill this one and use setRememberUser() instead
    public UserSettings(boolean rememberUser) {
        rememberPassword = false;
        this.rememberUser = rememberUser;
        colorMode = ColorMode.LIGHT;
        gameDifficulty = GameDifficulty.NORMAL;
    }

    public UserSettings(boolean rememberUser, boolean rememberPassword, String gamedifficulty) {
        this.rememberUser = rememberUser;
        this.rememberPassword = rememberPassword;
        this.gameDifficulty = GameDifficulty.fromString(gamedifficulty);
    }

    /* Getters and Setters */
    public boolean isRememberPassword() { return rememberPassword; }
    public void setRememberPassword(boolean rememberPassword) { this.rememberPassword = rememberPassword; }
    public boolean isRememberUser() { return rememberUser; }
    public void setRememberUser(boolean rememberUser) { this.rememberUser = rememberUser; }
    public ColorMode getColorMode() { return this.colorMode; }
    public void setColorMode(ColorMode colorMode) { this.colorMode = colorMode; }
    public GameDifficulty getGameDifficulty() { return gameDifficulty; }
    public void setGameDifficulty(GameDifficulty gameDifficulty) { this.gameDifficulty = gameDifficulty; }
    public void setGameDifficulty(String gameDifficulty) { this.gameDifficulty = GameDifficulty.fromString(gameDifficulty);}

    /**
     * Implement Serializable Interface methods
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("USER SETTINGS: no Object data");
    }
}
