package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class IntakeMotorC extends CommandBase {

  double MotorSpeed;

  /**
   * Generic Motor
   * 
   * @param MotorSpeed Desired speed
   */
  public IntakeMotorC(double MotorSpeed) {
    addRequirements(RobotContainer.intakeS);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    RobotContainer.intakeS.intakeMotor(MotorSpeed);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
