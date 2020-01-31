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
 * This subsystem is for the drivetrain, which is made up of two master Talons and two sets of Victors, each on a side of the drivetrain.
 */
public class DrivebaseS implements Subsystem {
  /**
   * The left master NomadTalonSRX
   */
  private NomadTalonSRX leftMasterTalon = new NomadTalonSRX(Constants.CAN_ID_DRIVE_LEFT_MASTER);
  /**
   * the right master NomadTalonSRX
   */
  private NomadTalonSRX rightMasterTalon = new NomadTalonSRX(Constants.CAN_ID_DRIVE_RIGHT_MASTER);
  /**
   * An ArrayList of NomadVictorSPXs for the left side of the drivebase.
   */
  private ArrayList<NomadVictorSPX> leftSlaveVictors = new ArrayList<>();
  /**
   * An ArrayList of NomadVictorSPXs for the left side of the drivebase.
   */
  private ArrayList<NomadVictorSPX> rightSlaveVictors = new ArrayList<>();
  /**
   * The DifferentialDrive object containing the master Talons.
   */
  private DifferentialDrive differentialDrive = new DifferentialDrive(leftMasterTalon, rightMasterTalon);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  /**
   * Creates a new DrivebaseS.
   */
  public DrivebaseS() {
    for (int i : Constants.ARRAY_CAN_ID_DRIVE_LEFT) { //assume the slaves are Victor SPXs
      leftSlaveVictors.add(new NomadVictorSPX(i, false, leftMasterTalon));
    }
    for (int i : Constants.ARRAY_CAN_ID_DRIVE_RIGHT) { //assume the slaves are Victor SPXs
      rightSlaveVictors.add(new NomadVictorSPX(i, false, rightMasterTalon));
    }
  }
  /**
   * Calls the DifferentialDrive arcadeDrive method
   * @param driveSpeed -1 to 1, forward-backward speed.
   * @param turnSpeed -1 to 1, turning speed.
   */
  public void arcadeDrive(double driveSpeed, double turnSpeed) {
    differentialDrive.arcadeDrive(driveSpeed, turnSpeed);
  }
}
