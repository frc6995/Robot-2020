package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.HopperS;

/**
 * The command that lifts balls through the hopper
 * 
 * @author JoeyFabel
 */
public class HopperLiftBallsC extends CommandBase {
  private HopperS hopper;
  private double speed;

  public HopperLiftBallsC(HopperS hopperS) {
    this.hopper = hopperS;
    addRequirements(this.hopper);
    this.speed = 6995;
  }

  public HopperLiftBallsC(HopperS hopperS, double spinSpeed) {
    this.hopper = hopperS;
    addRequirements(this.hopper);
    this.speed = spinSpeed;
  }

  @Override
  public void initialize() {
  }

  /**
   * Spins the hopper tubes to raise balls through the hopper.
   */
  @Override
  public void execute() {
    if (this.speed == 6995) {
      this.hopper.spinTubes(RobotPreferences.hopperInvert.getValue());
    }
    else {
      this.hopper.spinTubes(RobotPreferences.hopperInvert.getValue(), this.speed);
    }
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
