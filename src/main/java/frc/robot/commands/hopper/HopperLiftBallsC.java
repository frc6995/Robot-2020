package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.HopperS;

/**
 * The command that lifts balls through the hopper
 */
public class HopperLiftBallsC extends CommandBase {
  private HopperS hopper;

  /**
   * Creates a new HopperLiftBallsC.
   */
  public HopperLiftBallsC(HopperS hopperS) {
    this.hopper = hopperS;
    addRequirements(this.hopper);
  }

  @Override
  public void initialize() {
  }

  /**
   * Spins the hopper tubes to raise balls through the hopper.
   */
  @Override
  public void execute() {

    this.hopper.spinTubes(false);

  }

  /**
   * stops spinning the hopper when interupted
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
