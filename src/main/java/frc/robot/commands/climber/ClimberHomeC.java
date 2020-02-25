package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimberS;

public class ClimberHomeC extends CommandBase {
  private double timeUp;
  private double count;
  private boolean finished;
  private ClimberS climber;
  /**
   * Creates a new ClimberHomeC.
   */
  public ClimberHomeC(ClimberS climberS) {
    this.climber = climberS;
    addRequirements(climberS);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.timeUp = 0.25;
    this.count = 0;
    this.finished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
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

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.climber.resetEncoders();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.finished;
  }
}
