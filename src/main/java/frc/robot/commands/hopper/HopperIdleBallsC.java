package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HopperS;

/**
 * Command that keeps balls idling inside the hopper.
 */
public class HopperIdleBallsC extends CommandBase {
  private HopperS hopper;

  /**
   * Creates a new HopperIdleBallsC.
   */
  public HopperIdleBallsC(HopperS hopperS) {
    this.hopper = hopperS;
    addRequirements(this.hopper);
  }

  @Override
  public void initialize() {
  }

  /**
   * Spins one hopper tube one direction and the other tube the other direction.
   */
  @Override
  public void execute() {

    this.hopper.spinTube(true, 1);
    this.hopper.spinTube(true, 2);

  }

  /**
   * if the command is interupted, stop spinning the tubes.
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
