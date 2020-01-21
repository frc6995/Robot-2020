package frc.utility.preferences;

import edu.wpi.first.wpilibj.Preferences;

/**
 * A class to create an int type preference.
 */
public class NomadIntPreference extends NomadPreference {
    public int defaultValue;
    protected String prefName;

    /**
     * Create an int preference with a name and default value
     * and add it to preferences if it doesn't already exist
     * @param name
     * @param defaultVal
    */
    public NomadIntPreference(String name, int defaultVal) {
        defaultValue = defaultVal;
        prefName = name;
        if (!Preferences.getInstance().containsKey(prefName)) {
            Preferences.getInstance().putInt(prefName, defaultValue);
        }
    }

    /**
     * Return the value of the preference depending on
     * whether its using defaults
     * @return value
     */
    public int getValue() {
        if (isUsingDefaults()) {
            return defaultValue;
        }
        else {
            return Preferences.getInstance().getInt(prefName, defaultValue);
        }
    }
}
