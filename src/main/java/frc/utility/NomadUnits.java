/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.utility;

import frc.robot.constants.DriveConstants;

/**
 * Add your docs here.
 */
public class NomadUnits {

    public static double DBTicksToMeters (double ticks) {
        return Math.PI * DriveConstants.kWheelDiameter * ticks / DriveConstants.ENCODER_CNTS_PER_WHEEL_REV;
    }

    public static double DBMetersToTicks (double meters) {
        return (meters / (DriveConstants.kWheelDiameter * Math.PI) * DriveConstants.ENCODER_CNTS_PER_WHEEL_REV); 
    }
}
