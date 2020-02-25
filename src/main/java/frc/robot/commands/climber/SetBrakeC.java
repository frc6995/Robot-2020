package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.ClimberS.brakePosition;

public class SetBrakeC extends CommandBase {
  private ClimberS climber;
  private brakePosition Position;
  /**
   * Creates a new SetBrakeC.
   * @param position the brake position
   */
  public SetBrakeC(ClimberS climberS, brakePosition position) {
    this.climber = climberS;
    this.Position = position;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (this.Position == brakePosition.Brake) {
      this.climber.brake();
    }
    else {
      this.climber.unbrake();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
