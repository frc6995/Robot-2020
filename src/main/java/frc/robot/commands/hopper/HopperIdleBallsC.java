package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.RobotLEDS;
import frc.robot.subsystems.RobotLEDS.ledStates;

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

    if (RobotLEDS.robotLEDS.currentState != ledStates.Climb_Time) RobotLEDS.robotLEDS.currentState = ledStates.Hopper_On;
    this.hopper.spinTube(true, 1);
    this.hopper.spinTube(false, 2);

  }

  /**
   * if the command is interupted, stop spinning the tubes.
   */
  @Override
  public void end(boolean interrupted) {
    RobotLEDS.robotLEDS.revertLEDS();
    this.hopper.stopTubes();

  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
