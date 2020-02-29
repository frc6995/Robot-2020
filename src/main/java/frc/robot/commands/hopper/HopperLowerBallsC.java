package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.HopperS;

/**
 * The command that lowers balls in the hopper.
 */
public class HopperLowerBallsC extends CommandBase {
  private HopperS hopper;

  /**
   * Creates a new HopperLowerBallsC.
   */
  public HopperLowerBallsC(HopperS hopperS) {
    this.hopper = hopperS;
    addRequirements(this.hopper);
  }

  @Override
  public void initialize() {
  }

  /**
   * Spins the hopper motors in the opposite direction from the lift ball command
   * to lower the balls through the hopper
   */
  @Override
  public void execute() {
    // Spins it opposite direction from lift balls command, based on preference to
    // make sure the invert value is correct.
    this.hopper.spinTubes(true);

  }

  /**
   * If the command is interupted, stop spining the hopper.
   */
  @Override
  public void end(boolean interrupted) {

    this.hopper.stopTubes();

  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
