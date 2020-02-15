package frc.robot;

import frc.utility.preferences.NomadDoublePreference;
import frc.utility.preferences.NomadIntPreference;

/**
 * The Preferences class provides a convenient place for us to hold robot-wide numerical or boolean
 * preferences.  This class should not be used for any other purpose.  All preferences should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class RobotPreferences {
    //Climber Constants
    public static final NomadDoublePreference liftHeight = new NomadDoublePreference("liftHeight", 66500);
    public static final NomadDoublePreference pullHeight = new NomadDoublePreference("pullHeight", 3000);
    
    public static final NomadDoublePreference climberKpUp = new NomadDoublePreference("Climber Kp Up", 0.5);
    public static final NomadDoublePreference climberKiUp = new NomadDoublePreference("Climber Ki Up", 0.0001);
    public static final NomadDoublePreference climberKdUp = new NomadDoublePreference("Climber Kd Up", 10);
    public static final NomadDoublePreference climberKf = new NomadDoublePreference("Climber Kf", 0.0);

    public static final NomadDoublePreference climberKpDown = new NomadDoublePreference("Climber Kp Down", 0.06);
    public static final NomadDoublePreference climberKiDown = new NomadDoublePreference("Climber Ki Down", 0.0001);
    public static final NomadDoublePreference climberKdDown = new NomadDoublePreference("Climber Kd Down", 8);

    public static final NomadIntPreference climberIZoneUp = new NomadIntPreference("Climber I zone up", 1000);
    public static final NomadIntPreference climberIZoneDown = new NomadIntPreference("Climber I zone down", 1000);

    public static final NomadIntPreference climberAllowableError = new NomadIntPreference("Climber Allowable Error", 1000);
}
