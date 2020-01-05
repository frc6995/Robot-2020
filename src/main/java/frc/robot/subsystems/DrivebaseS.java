/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.wrappers.NomadTalonSRX;

/**
 * Add your docs here.
 */
public class DrivebaseS implements Subsystem {
  private NomadTalonSRX leftMasterTalon = new NomadTalonSRX(Constants.CAN_ID_DRIVE_LEFT_MASTER);
  private NomadTalonSRX rightMasterTalon = new NomadTalonSRX(Constants.CAN_ID_DRIVE_RIGHT_MASTER);
  private DifferentialDrive differentialDrive = new DifferentialDrive(leftMasterTalon, rightMasterTalon);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
}
