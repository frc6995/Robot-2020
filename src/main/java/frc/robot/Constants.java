/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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
    /**
     * The CAN ID for the left master motor controller.
     */
    public final static int CAN_ID_DRIVE_LEFT_MASTER = 1;

    /**
     * The CAN ID for the right master motor controller.
     */
    public final static int CAN_ID_DRIVE_RIGHT_MASTER = 2;

    /**
     * An array of slave controller IDs for the left drivebase side.
     */
    public static int[] ARRAY_CAN_ID_DRIVE_LEFT = {3};

    /**
     * An array of slave controller IDs for the right drivebase side.
     */
    public static int[] ARRAY_CAN_ID_DRIVE_RIGHT = {4};

}
