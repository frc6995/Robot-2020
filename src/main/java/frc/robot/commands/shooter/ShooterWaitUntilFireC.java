/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterS;

public class ShooterWaitUntilFireC extends CommandBase {
  private ShooterS shooter;
  private int initBallsFired;
  /**
   * Creates a new ShooterWaitUntilReadyC.
   */
  public ShooterWaitUntilFireC(ShooterS shooterS) {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = shooterS;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initBallsFired = shooter.getBallsFired();
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
    return shooter.getBallsFired() > initBallsFired;
  }
}
