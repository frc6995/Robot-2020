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
    addRequirements(RobotContainer.hopperS);
  }

  @Override
  public void initialize() {
  }

  /**
   * Spins the hopper motors in the opposite direction from the lift ball command to lower the balls through the hopper
   */
  @Override
  public void execute() {
    //Spins it opposite direction from lift balls command, based on preference to make sure the invert value is correct.
    RobotContainer.hopperS.spinTubes(!RobotPreferences.hopperInvert.getValue());

  }

  /**
   * If the command is interupted, stop spining the hopper.
   */
  @Override
  public void end(boolean interrupted) {

  RobotContainer.hopperS.stopTubes();

  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
