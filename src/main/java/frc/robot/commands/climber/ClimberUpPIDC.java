package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.ClimberS.climberLevel;

public class ClimberUpPIDC extends CommandBase {
  private ClimberS climber;
  private boolean endAtTarget;
  /**
   * Creates a new ClimberUpPIDC.
   * @param finishAtSetPoint if true, it ends the command 
   * at the set point, if false it runs until interrupted
   */
  public ClimberUpPIDC(ClimberS climberS, boolean finishAtSetPoint) {
    this.climber = climberS;
    this.endAtTarget = finishAtSetPoint;
    addRequirements(this.climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.climber.isAtSetPoint(climberLevel.reset);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.climber.runUpPID();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.climber.setClimberPowerFeedForward(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return this.endAtTarget && this.climber.isAtSetPoint(climberLevel.AboveBar);
  }
}
