/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimberS.brakePosition;

public class SetBrakeC extends CommandBase {
  private brakePosition Position;
  /**
   * Creates a new SetBrakeC.
   * @param position the brake position
   */
  public SetBrakeC(brakePosition position) {
    this.Position = position;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (this.Position == brakePosition.Brake) {
      RobotContainer.climberS.brake();
    }
    else {
      RobotContainer.climberS.unbrake();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
