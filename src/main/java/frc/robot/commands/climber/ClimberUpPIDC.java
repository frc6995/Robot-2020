package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.RobotLEDS;
import frc.robot.subsystems.ClimberS.climberLevel;
import frc.robot.subsystems.RobotLEDS.ledStates;

/**
 * Runs our Climber up
 * 
 * @author Sammcdo
 */
public class ClimberUpPIDC extends CommandBase {
  private ClimberS climber;
  private boolean endAtTarget;
  private boolean firstLoop = true;
  /**
   * Creates a new ClimberUpPIDC.
   * 
   * @param finishAtSetPoint if true, it ends the command at the set point, if
   *                         false it runs until interrupted
   */
  public ClimberUpPIDC(ClimberS climberS, boolean finishAtSetPoint) {
    this.climber = climberS;
    this.endAtTarget = finishAtSetPoint;
    addRequirements(this.climber);
  }

  @Override
  public void initialize() {
    this.climber.isAtSetPoint(climberLevel.reset);
  }

  @Override
  public void execute() {
    this.climber.runUpPID();
    if (firstLoop) {
      RobotLEDS.robotLEDS.currentState = ledStates.Climbing;
      firstLoop = false;
    }
  }
  
  @Override
  public void end(boolean interrupted) {
    this.climber.setClimberPowerFeedForward(0);
    RobotLEDS.robotLEDS.revertLEDS();
    RobotContainer.drivebaseS.setMaxOutput(0.25);
  }
  
  @Override
  public boolean isFinished() {
    return this.endAtTarget && this.climber.isAtSetPoint(climberLevel.AboveBar);
  }
}
