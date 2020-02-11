/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.constants;

/**
 * Add your docs here.
 */
public class VisionConstants {
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
