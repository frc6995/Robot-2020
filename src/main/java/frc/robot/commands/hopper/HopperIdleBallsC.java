package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
/**
 * Command that keeps balls idling inside the hopper.
 */
public class HopperIdleBallsC extends CommandBase {
  /**
   * Creates a new HopperIdleBallsC.
   */
  public HopperIdleBallsC() {
    addRequirements(RobotContainer.hopperS);
  }

  @Override
  public void initialize() {
  }

  /**
   * Spins one hopper tube one direction and the other tube the other direction.
   */
  @Override
  public void execute() {

    RobotContainer.hopperS.spinTube(true, 1);
    RobotContainer.hopperS.spinTube(false, 2);

  }

  /**
   * if the command is interupted, stop spinning the tubes.
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