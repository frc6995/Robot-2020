package frc.robot;

import frc.utility.preferences.NomadBooleanPreference;
import frc.utility.preferences.NomadDoublePreference;
import frc.utility.preferences.NomadIntPreference;

/**
 * Preferences: Values to be changed on-the-fly
 * 
 * @author Sammcdo, EliSauder, JoeyFabel, Shueja, AriShashivkopanazak
 */
public final class RobotPreferences {
        // Climber
        public static final NomadDoublePreference climberKpUp = new NomadDoublePreference("Climber Kp Up", 0.65);
        public static final NomadDoublePreference climberKiUp = new NomadDoublePreference("Climber Ki Up", 0.001);
        public static final NomadDoublePreference climberKdUp = new NomadDoublePreference("Climber Kd Up", 8);
        public static final NomadDoublePreference climberKfUp = new NomadDoublePreference("Climber Kf Up", 0.0);
        public static final NomadDoublePreference climberKpDown = new NomadDoublePreference("Climber Kp Down", 0.1);
        public static final NomadDoublePreference climberKiDown = new NomadDoublePreference("Climber Ki Down", 0.0001);
        public static final NomadDoublePreference climberKdDown = new NomadDoublePreference("Climber Kd Donw", 9);
        public static final NomadDoublePreference climberKfDown = new NomadDoublePreference("Climber Kf Down", 0.0);
        public static final NomadIntPreference climberIZoneUp = new NomadIntPreference("Climber I zone up", 1000);
        public static final NomadIntPreference climberIZoneDown = new NomadIntPreference("Climber I zone down", 1500);
        public static final NomadIntPreference climberAllowableError = new NomadIntPreference("Climber Allowable Error",
                        1000);
        public static final NomadDoublePreference liftHeight = new NomadDoublePreference("liftHeight", 65500);
        public static final NomadDoublePreference pullHeight = new NomadDoublePreference("pullHeight", 2000);

        // Drivebase
        public static final NomadDoublePreference visionKpHorizontal = new NomadDoublePreference("Vision Horizontal kP",
                        -0.1f);
        public static final NomadDoublePreference visionKpVertical = new NomadDoublePreference("Vision Vertical kP",
                        -0.5f);
        public static NomadDoublePreference drivekP = new NomadDoublePreference("drivekP", 0.1);

        // Hopper
        public static NomadBooleanPreference hopperInvert = new NomadBooleanPreference("hopper motors invert", false);
        public static NomadDoublePreference hopperSpeed = new NomadDoublePreference("hopper speed", 0.75);

        // Intake

        // Shooter

        // Slider

}
