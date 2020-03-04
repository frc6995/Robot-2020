package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.ClimberS.climberLevel;

/**
 * Runs Climber down using PID
 * 
 * @author Sammcdo
 */
public class ClimberDownPIDC extends CommandBase {
  private ClimberS climber;
  private boolean endAtTarget;

  /**
   * Creates a new ClimberDownPIDC.
   * 
   * @param finishAtSetPoint if true, it ends the command at the set point, if
   *                         false it runs until interrupted
   */
  public ClimberDownPIDC(ClimberS climberS, boolean finishAtSetPoint) {
    this.climber = climberS;
    this.endAtTarget = finishAtSetPoint;
    addRequirements(this.climber);
  }

  @Override
  public void initialize() {
    this.climber.isAtSetPoint(climberLevel.reset);
    if (this.climber.getPosition() < (RobotPreferences.liftHeight.getValue() - 500)) {
      end(false); // this does actually unschedule the command right?
    }
  }

  @Override
  public void execute() {
    this.climber.runDownPID();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    if (this.endAtTarget) {
      return this.climber.isAtSetPoint(climberLevel.Pullup);
    }
    else {
      return false;
    }
  }
}
