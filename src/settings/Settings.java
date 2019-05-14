package settings;

/**
 * This is a Singleton class, enjoy.
 * Generic settings go here
 */
public class Settings {
    // Make sure there is only ever one instance
    private static Settings single_instance = null;

    /**
     * Private constructor, if you don't understand what's happening
     * please take a class or use the Google machine.
     */
    private Settings() {
        // Get the config.yml in here
    }

    /**
     * Static method to create instance of Settings class
     */
    public static Settings getSettings() {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new Settings();
        }

        return single_instance;
    }


    // Get and set the settings in config.yml



}
