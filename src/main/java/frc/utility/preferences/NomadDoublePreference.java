package frc.utility.preferences;

import edu.wpi.first.wpilibj.Preferences;

/**
 * A class to create a double type preference.
 */
public class NomadDoublePreference extends NomadPreference {
    public double defaultValue;
    protected String prefName;

    /**
     * Create a double preference with a name and default value
     * and add it to preferences if it doesn't already exist
     * @param name
     * @param defaultVal
    */
    public NomadDoublePreference(String name, double defaultVal) {
        defaultValue = defaultVal;
        prefName = name;
        if (!Preferences.getInstance().containsKey(prefName)) {
            Preferences.getInstance().putDouble(prefName, defaultValue);
        }
    }

    /**
     * Return the value of the preference depending on
     * whether its using defaults
     * @return value
     */
    public double getValue() {
        if (isUsingDefaults()) {
            return defaultValue;
        }
        else {
            return Preferences.getInstance().getDouble(prefName, defaultValue);
        }
    }
}
