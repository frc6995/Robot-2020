package frc.robot.constants;

/**
 * Drivebase Constants
 * 
 * @author Ari Shashivkopanazak
 */
public class DrivebaseConstants {

    /**
     * The CAN ID for the left master motor controller.
     */
    public final static int CAN_ID_DRIVE_LEFT_MASTER = 10;

    /**
     * The CAN ID for the right master motor controller.
     */
    public final static int CAN_ID_DRIVE_RIGHT_MASTER = 11;

    /**
     * The axis on the drive controller for driving forward and backward.
     */
    public static final int AXIS_DRIVE_FWD_BACK = 0;

    /**
     * The axis on the drive controller for turning.
     */
    public static final int AXIS_DRIVE_TURN = 1;

    /**
     * An array of slave controller IDs for the left drivebase side.
     */
    public static int[] ARRAY_CAN_ID_DRIVE_LEFT = {13};

    /**
     * An array of slave controller IDs for the right drivebase side.
     */
    public static int[] ARRAY_CAN_ID_DRIVE_RIGHT = {12};
}
