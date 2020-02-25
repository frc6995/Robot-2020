package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.ClimberS.climberLevel;

public class ClimberDownPIDC extends CommandBase {
  private boolean endAtTarget;
  /**
   * Creates a new ClimberDownPIDC.
   * @param finishAtSetPoint if true, it ends the command 
   * at the set point, if false it runs until interrupted
   */
  public ClimberDownPIDC(boolean finishAtSetPoint) {
    this.endAtTarget = finishAtSetPoint;
    addRequirements(RobotContainer.climberS);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.climberS.isAtSetPoint(climberLevel.reset);
    if (RobotContainer.climberS.getPosition() < (RobotPreferences.liftHeight.getValue()-500)) {
      end(false); //this does actually unschedule the command right?
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.climberS.runDownPID();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (this.endAtTarget) {
      return RobotContainer.climberS.isAtSetPoint(climberLevel.Pullup);
    }
    else {
      return false;
    }
  }
}
