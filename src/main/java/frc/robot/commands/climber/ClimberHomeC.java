package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberS;

/**
 * Climber Goes to Home Position
 * 
 * @author Sammcdo
 */
public class ClimberHomeC extends CommandBase {
  private double timeUp;
  private double count;
  private boolean finished;
  private ClimberS climber;

  public ClimberHomeC(ClimberS climberS) {
    this.climber = climberS;
    addRequirements(climberS);
  }

  @Override
  public void initialize() {
    this.timeUp = 0.25;
    this.count = 0;
    this.finished = false;
  }

  @Override
  public void execute() {
    if (this.count < this.timeUp * 50) {
      this.count++;
      this.climber.setClimberPower(0.1);
    }
    else if (!this.climber.isHomed()) {
      this.climber.setClimberPower(-0.05);
    }
    else {
      this.finished = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    this.climber.resetEncoders();
  }

  @Override
  public boolean isFinished() {
    return this.finished;
  }
}
