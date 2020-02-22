package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

/**
 * Processe for the Intake to Deploy
 * 
 * @author Ari Shashivkopanazak
 */
public class IntakeDeployC extends CommandBase {

  /**
   * Deploys the Intake
   */
  public IntakeDeployC() {
    addRequirements(RobotContainer.intakeS);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    RobotContainer.intakeS.intakeDeploy();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
