package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.RobotLEDS;
import frc.robot.subsystems.RobotLEDS.ledStates;

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

    if (RobotLEDS.robotLEDS.currentState != ledStates.Climb_Time) RobotLEDS.robotLEDS.currentState = ledStates.Hopper_On;
    this.hopper.spinTubes(RobotPreferences.hopperInvert.getValue());

  }

  /**
   * stops spinning the hopper when interupted
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
