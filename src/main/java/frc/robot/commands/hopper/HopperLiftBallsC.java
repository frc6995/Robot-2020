package frc.robot.commands.hopper;

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
    addRequirements(RobotContainer.hopperS);
  }

  @Override
  public void initialize() {
  }

  /**
   * Spins the hopper tubes to raise balls through the hopper.
   */
  @Override
  public void execute() {

    RobotContainer.hopperS.spinTubes(RobotPreferences.hopperInvert.getValue());

  }

  /**
   * stops spinning the hopper when interupted
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
