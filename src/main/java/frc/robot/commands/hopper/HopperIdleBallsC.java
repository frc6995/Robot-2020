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
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.hopperS);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  /**
   * Spins one hopper tube one direction and the other tube the other direction.
   */
  @Override
  public void execute() {

    RobotContainer.hopperS.spinTube(true, 1);
    RobotContainer.hopperS.spinTube(false, 2);

  }

  // Called once the command ends or is interrupted.
  /**
   * if the command is interupted, stop spinning the tubes.
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
