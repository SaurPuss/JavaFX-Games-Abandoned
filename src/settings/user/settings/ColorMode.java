package settings.user.settings;

public enum ColorMode {
    LIGHT("Light"), DARK("Dark");

    private final String name;

    ColorMode(String name) {
        this.name = name;
    }

    @Override
    public String toString() { return this.name; }

    public static ColorMode fromString(String value) {
        for (ColorMode b : ColorMode.values()) {
            if (b.name.equalsIgnoreCase(value)) {
                return b;
            }
        }
        return null;
    }
}
