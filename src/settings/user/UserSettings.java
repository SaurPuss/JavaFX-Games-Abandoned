package settings.user;

/**
 * This is a Singleton class, enjoy.
 * Generic settings go here
 */
public class UserSettings {
    // Make sure there is only ever one instance
    private static UserSettings single_instance = null;

    /**
     * Private constructor, if you don't understand what's happening
     * please take a class or use the Google machine.
     */
    private UserSettings() {
        // Get the config.yml in here
    }

    /**
     * Static method to create instance of UserSettings class
     */
    public static UserSettings getSettings() {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new UserSettings();
        }

        return single_instance;
    }


    // Get and set the settings in config.yml



}
