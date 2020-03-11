package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.ClimberS.brakePosition;

/**
 * Set the Brake on or off
 * 
 * @author Sammcdo
 */
public class SetBrakeC extends CommandBase {
  private ClimberS climber;
  private brakePosition Position;

  /**
   * Creates a new SetBrakeC.
   * 
   * @param position the brake position
   */
  public SetBrakeC(ClimberS climberS, brakePosition position) {
    this.climber = climberS;
    this.Position = position;
  }

  @Override
  public void initialize() {
    if (this.Position == brakePosition.Brake) {
      this.climber.brake();
    }
    else {
      this.climber.unbrake();
    }
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
