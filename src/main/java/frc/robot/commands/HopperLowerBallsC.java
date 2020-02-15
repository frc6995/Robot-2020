/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotPreferences;

public class HopperLowerBallsC extends CommandBase {
  /**
   * Creates a new HopperLowerBallsC.
   */
  public HopperLowerBallsC() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.hopperS);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Spins it opposite direction from lift balls command, based on preference to make sure the invert value is correct.
    RobotContainer.hopperS.SpinTubes(!RobotPreferences.hopperInvert.getValue());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

RobotContainer.hopperS.StopTubes();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
