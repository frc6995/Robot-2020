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
    public static final NomadDoublePreference liftHeight = new NomadDoublePreference("liftHeight", 7500);
    public static final NomadDoublePreference pullHeight = new NomadDoublePreference("pullHeight", 3000);
    
    public static final NomadDoublePreference climberKpUp = new NomadDoublePreference("Climber Kp Up", 0.0);
    public static final NomadDoublePreference climberKiUp = new NomadDoublePreference("Climber Ki Up", 0.0);
    public static final NomadDoublePreference climberKdUp = new NomadDoublePreference("Climber Kd Up", 0.0);
    public static final NomadDoublePreference climberKf = new NomadDoublePreference("Climber Kf", 0.0);

    public static final NomadDoublePreference climberKpDown = new NomadDoublePreference("Climber Kp Up", 0.0);
    public static final NomadDoublePreference climberKiDown = new NomadDoublePreference("Climber Ki Up", 0.0);
    public static final NomadDoublePreference climberKdDown = new NomadDoublePreference("Climber Kd Up", 0.0);

    public static final NomadIntPreference climberIZoneUp = new NomadIntPreference("Climber I zone up", 300);
    public static final NomadIntPreference climberIZoneDown = new NomadIntPreference("Climber I zone down", 300);

    public static final NomadIntPreference climberAllowableError = new NomadIntPreference("Climber Allowable Error", 15);

    public static final NomadDoublePreference gyroPidP = new NomadDoublePreference("Gyro PID P", 0.00);
    public static final NomadDoublePreference gyroPidI = new NomadDoublePreference("Gyro PID I", 0.00);
    public static final NomadDoublePreference gyroPidD = new NomadDoublePreference("Gyro PID D", 0.00);
    public static final NomadDoublePreference gyroPidAngle = new NomadDoublePreference("Gyro pid angle", 0);
}
