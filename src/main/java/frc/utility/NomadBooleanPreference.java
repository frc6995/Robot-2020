package frc.utility;

import edu.wpi.first.wpilibj.Preferences;

/**
 * A class to create a boolean type preference.
 */
public class NomadBooleanPreference extends NomadPreference {
    public boolean defaultValue;
    protected String prefName;

    /**
     * Create a boolean preference with a name and default value
     * and add it to preferences if it doesn't already exist
     * @param name
     * @param default
    */
    public NomadBooleanPreference(String name, boolean defaultVal) {
        defaultValue = defaultVal;
        prefName = name;
        if (!Preferences.getInstance().containsKey(prefName)) {
            Preferences.getInstance().putBoolean(prefName, defaultValue);
        }
    }

    /**
     * Return the value of the preference depending on
     * whether its using defaults
     * @return value
     */
    public boolean getValue() {
        if (isUsingDefaults()) {
            return defaultValue;
        }
        else {
            return Preferences.getInstance().getBoolean(prefName, defaultValue);
        }
    }
}
