package frc.robot.commands.Slider;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

/**
 * Turns off the motors and retracts the arm
 * 
 * @author Ari Shashivkopanazak
 */
public class IntakeRetractC extends CommandBase {

  public IntakeRetractC() {
    addRequirements(RobotContainer.intakeS);
  }

  @Override
  public void initialize() {
    RobotContainer.intakeS.intakeMotor(0.0);
  }

  @Override
  public void execute() {
    RobotContainer.intakeS.intakeRetract();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
