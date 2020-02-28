package frc.robot.constants;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The DriveConstants class provides a convenient place for teams to hold
 * robot-wide numerical or boolean DriveConstants. This class should not be used
 * for any other purpose. All DriveConstants should be declared globally (i.e.
 * public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the DriveConstants are needed, to reduce verbosity.
 */
public final class DriveConstants {
    /** The CAN ID for the left master motor controller. */
    public final static int CAN_ID_DRIVE_LEFT_MASTER = 10;

    /** The CAN ID for the right master motor controller. */
    public final static int CAN_ID_DRIVE_RIGHT_MASTER = 11;
    /**
     * The USB device ID for the drive controller.
     */
    public static final int OI_DRIVE_CONTROLLER = 0;

    /**
     * The axis on the drive controller for driving forward and backward.
     */
    public static final int AXIS_DRIVE_FWD_BACK = 1;

    /** The axis on the drive controller for turning. */
    public static final int AXIS_DRIVE_TURN = 0;

    // Drivebase DriveConstants
    /** An array of slave controller IDs for the left drivebase side. */
    public static int[] ARRAY_CAN_ID_DRIVE_LEFT = { 13 };

    /** An array of slave controller IDs for the right drivebase side. */
    public static int[] ARRAY_CAN_ID_DRIVE_RIGHT = { 12 };

    /** Whether or not the gyro is reversed */
    public static final boolean GYRO_REVERSED = true;

    /** The number of encoder counts per encoder revolution. */
    public static final double ENCODER_CNTS_PER_REV = 1024;

    /**
     * The number of encoder counts per wheel revolution (7 encoder revolutions per
     * 3 wheel revolutions).
     */
    public static final double ENCODER_CNTS_PER_WHEEL_REV = ENCODER_CNTS_PER_REV * 7.0 / 3.0;

    // Drive characterization DriveConstants

    public static final double ksVolts = 1.26;
    public static final double kvVoltSecondsPerMeter = 2.19;
    public static final double kaVoltSecondsSquaredPerMeter = 0.683;
    public static final double kWheelDiameter = 0.1524;

    public static final double kPDriveVel = 0.0405;
    public static final double kPDriveVelLeft = 0.0405;
    public static final double kPDriveVelRight = 0.0405;
    public static final double kTrackWidthMeters = 0.6032;
    public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(
            kTrackWidthMeters);
}
