/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimberS.climberLevel;

public class ClimberUpPIDC extends CommandBase {
  private boolean endAtTarget;
  /**
   * Creates a new ClimberUpPIDC.
   * @param finishAtSetPoint if true, it ends the command 
   * at the set point, if false it runs until interrupted
   */
  public ClimberUpPIDC(boolean finishAtSetPoint) {
    this.endAtTarget = finishAtSetPoint;
    addRequirements(RobotContainer.climberS);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.climberS.isAtSetPoint(climberLevel.reset);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.climberS.runUpPID();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.climberS.setClimberPowerFeedForward(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return this.endAtTarget && RobotContainer.climberS.isAtSetPoint(climberLevel.AboveBar);
  }
}
