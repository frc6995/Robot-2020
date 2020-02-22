package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotPreferences;

/**
 * The command that lowers balls in the hopper.
 */
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
  /**
   * Spins the hopper motors in the opposite direction from the lift ball command to lower the balls through the hopper
   */
  @Override
  public void execute() {
    //Spins it opposite direction from lift balls command, based on preference to make sure the invert value is correct.
    RobotContainer.hopperS.spinTubes(!RobotPreferences.hopperInvert.getValue());

  }

  // Called once the command ends or is interrupted.
  /**
   * If the command is interupted, stop spining the hopper.
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
