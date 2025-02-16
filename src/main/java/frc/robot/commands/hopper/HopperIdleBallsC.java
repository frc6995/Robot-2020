package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HopperS;

/**
 * Command that keeps balls idling inside the hopper.
 * 
 * @author JoeyFabel
 */
public class HopperIdleBallsC extends CommandBase {
  private HopperS hopper;

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
    this.hopper.spinTube(false, 1, 0.5);
    this.hopper.spinTube(true, 2, 0.25);

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
