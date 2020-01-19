package frc.utility;

/**
 * A base class to provide a getter and setter for
 * the useDefaults variable. This will allow us know
 * whether to read from shuffleboard or use a default
 * value stored in code. <br><br>
 * Based on frc3255s 2019 code
 */
public abstract class NomadPreference {
    /**The boolean to check whether to use defaults*/
    private static boolean usingDefaults = false;
    /**The name of the preference instance*/
    protected String name;

    /**
     * Use values saved in code
     */
    public static void useDefaults() {
        usingDefaults = true;
    }

    /**Use values from shuffleboard*/
    public static void usePreferences() {
        usingDefaults = false;
    }

    /**Get whether to use defaults
     * @return using defaults?
    */
    public static boolean isUsingDefaults() {
        return usingDefaults;
    }
}
