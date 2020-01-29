package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //TODO Adjust these constants for an actual robot.
    /**
     * Defines the types of GenericHID controller.
     */
    enum CONTROLLER_TYPE {Joystick, Xbox};
    /**
     * The CAN ID for the left master motor controller.
     */
    public final static int CAN_ID_DRIVE_LEFT_MASTER = 10;

    /**
     * The CAN ID for the right master motor controller.
     */
    public final static int CAN_ID_DRIVE_RIGHT_MASTER = 11;

    /**
     * The GenericHID implementation being used for the driver controller. Can be either Joystick or Xbox
     */
    public static final CONTROLLER_TYPE DRIVE_CONTROLLER_TYPE = CONTROLLER_TYPE.Joystick;

    /**
     * The USB device ID for the drive controller.
     */
    public static final int OI_DRIVE_CONTROLLER = 0;

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

    /**
     * Horizontal and Vertical Proportional terms
     */
    public static final double VISION_KP_HORIZONTAL = 0.04f;
    public static final double VISION_KP_VERTICAL = 0.04f;

    /**
     * the time per increment in seconds
     */
    public static final double VISION_RAMP_TIME = 0.25;

    /**
     * Vision Pipeline Preset, should be in this state at all times
     */
    public static final double VISION_PIPELINE = 1;

}
