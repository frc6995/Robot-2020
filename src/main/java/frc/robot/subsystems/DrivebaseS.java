/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.wrappers.MotorControllers.*;


/**
 * Add your docs here.
 */
public class DrivebaseS implements Subsystem {
  private NomadTalonSRX leftMasterTalon = new NomadTalonSRX(Constants.CAN_ID_DRIVE_LEFT_MASTER);
  private NomadTalonSRX rightMasterTalon = new NomadTalonSRX(Constants.CAN_ID_DRIVE_RIGHT_MASTER);
  private ArrayList<NomadVictorSPX> leftSlaveVictors;
  private ArrayList<NomadVictorSPX> rightSlaveVictors;
  private DifferentialDrive differentialDrive = new DifferentialDrive(leftMasterTalon, rightMasterTalon);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public DrivebaseS() {
    for (int i : Constants.ARRAY_CAN_ID_DRIVE_LEFT) { //assume the slaves are Victor SPXs
      leftSlaveVictors.add(new NomadVictorSPX(i, false, leftMasterTalon));
    }
    for (int i : Constants.ARRAY_CAN_ID_DRIVE_RIGHT) { //assume the slaves are Victor SPXs
      rightSlaveVictors.add(new NomadVictorSPX(i, false, rightMasterTalon));
    }
  }
  public void arcadeDrive(double driveSpeed, double turnSpeed) {
    differentialDrive.arcadeDrive(driveSpeed, turnSpeed);
  }
}
