package frc.robot.commands.climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ClimberManualC extends CommandBase {
  private DoubleSupplier power;
  /**
   * Creates a new ClimberManualC.
   */
  public ClimberManualC(DoubleSupplier Power) {
    addRequirements(RobotContainer.climberS);
    power = Power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }
  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = Math.abs(this.power.getAsDouble()) > 0.15 ? this.power.getAsDouble() : 0;
    RobotContainer.climberS.setClimberPower(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.climberS.setClimberPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
