package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotPreferences;

/**
 * The command that lifts balls through the hopper
 */
public class HopperLiftBallsC extends CommandBase {
  /**
   * Creates a new HopperLiftBallsC.
   */
  public HopperLiftBallsC() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.hopperS);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  /**
   * Spins the hopper tubes to raise balls through the hopper.
   */
  @Override
  public void execute() {

    RobotContainer.hopperS.spinTubes(RobotPreferences.hopperInvert.getValue());

  }

  // Called once the command ends or is interrupted.
  /**
   * stops spinning the hopper when interupted
   */
  @Override
  public void end(boolean interrupted) {
    RobotContainer.hopperS.stopTubes();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}