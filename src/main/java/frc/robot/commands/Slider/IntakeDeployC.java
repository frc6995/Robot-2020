package frc.robot.commands.Slider;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

/**
 * Deploys the Arm and Turns on the Motor
 * 
 * @author Ari Shashivkopanazak
 */
public class IntakeDeployC extends CommandBase {

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
    return false;
  }
}
