package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ClimberHomeC extends CommandBase {
  private double timeUp;
  private double count;
  /**
   * Creates a new ClimberHomeC.
   */
  public ClimberHomeC() {
    addRequirements(RobotContainer.climberS);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timeUp = 0.25;
    count = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (count < timeUp * 50) {
      count++;
      RobotContainer.climberS.setClimberPower(0.3);
    }
    else if (!RobotContainer.climberS.isHomed()) {
      RobotContainer.climberS.setClimberPower(0);
    }
    else {
      RobotContainer.climberS.
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
