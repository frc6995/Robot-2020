package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

/**
 * Process for the Intake to Retract
 * 
 * @author Ari Shashivkopanazak
 */
public class IntakeRetractC extends CommandBase {

  /**
   * Retracts the intake
   */
  public IntakeRetractC() {
    addRequirements(RobotContainer.intakeS);
  }

  @Override
  public void initialize() {
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
