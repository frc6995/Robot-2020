package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

/**
 * manages all processes for the Intake to Deploy
 * 
 * @author Ari Shashivkopanazak
 */
public class IntakeDeployC extends CommandBase {

  /**
   * Deploys the Intake and Turns on the wheels
   */
  public IntakeDeployC() {
    addRequirements(RobotContainer.intakeS);
  }

  @Override
  public void initialize() {
    RobotContainer.intakeS.intakeDeploy();
  }

  @Override
  public void execute() {
    RobotContainer.intakeS.intakeMotor(1.0);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
