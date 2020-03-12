package frc.robot.constants;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

/**
 * Constants for Shooter Constants
 * 
 * @author Shuja
 */
public class ShooterConstants {
    /**
     * The target RPM for the shooter wheel.
     */
    public static final double SHOOTER_RPM = 4500;

    public static final double SHOOTER_RPM_TRENCH = 5000;

    public static final double SHOOTER_MAX_RPM = 5600;
    /**
     * The CAN ID of the SPARK MAX running the shooter NEO.
     */
    public static final int CAN_ID_SHOOTER_SPARK_MAX = 33;
    /**
     * The number of loops the RPM must stay within range to be considered at setpoint.
     */
    public static final int MIN_LOOPS_IN_RANGE = 50;
    /**
     * The SimpleMotorFeedForward for the shooter
     */
    public static final SimpleMotorFeedforward SHOOTER_FEEDFORWARD 
    = new SimpleMotorFeedforward(0.161, 0.189/90, 0.0434/90);
}
