package frc.robot.constants;

/**
 * OI Constants, Supports Xbox and Joystick
 * 
 * @author Ari Shashivkopanazak
 */
public final class OIConstants {
    
    /**
     * Defines the types of GenericHID controller.
     */
    public static enum CONTROLLER_TYPE {Joystick, Xbox};

    /**
     * The GenericHID implementation being used for the driver controller. Can be either Joystick or Xbox
     */
    public static final CONTROLLER_TYPE DRIVE_CONTROLLER_TYPE = CONTROLLER_TYPE.Joystick;

    /**
     * The USB device ID for the drive controller.
     */
    public static final int OI_DRIVE_CONTROLLER = 0;
    public static final int OI_OPERATOR_CONTROLLER = 1;
}
