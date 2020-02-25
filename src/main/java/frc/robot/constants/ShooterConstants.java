/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.constants;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

/**
 * Add your docs here.
 */
public class ShooterConstants {
    /**
     * The target RPM for the shooter wheel.
     */
    public static final double SHOOTER_RPM = 3500;

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
    = new SimpleMotorFeedforward(0, 0.192/ 60, 0.0417 / 60);
}
