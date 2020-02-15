package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

/**
 * Manages all processes for the Intake to Retract
 * 
 * @author Ari Shashivkopanazak
 */
public class IntakeRetractC extends CommandBase {

  /**
   * Retracts the intake and turns off the wheels
   */
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
