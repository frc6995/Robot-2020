package frc.robot.commands.climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.ClimberS.climberLevel;
import io.github.oblarg.oblog.annotations.Log;

public class ClimberManualC extends CommandBase {
  private DoubleSupplier power;
  private ClimberS climber;
  /**
   * Creates a new ClimberManualC.
   */
  public ClimberManualC(ClimberS climberS, DoubleSupplier pwr) {
    climber = climberS;
    addRequirements(climberS);
    this.power = pwr;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climber.setClimberPower(0);
  }
  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    double speed = this.power.getAsDouble();
    if  (Math.abs(speed) < 0.1) {
      speed = 0;
    }
    
    /*if  (Math.abs(speed) < 0.1) {
      speed = 0;
    }*/
    SmartDashboard.putNumber("ClimberSpeed", speed);
    RobotContainer.climberS.setClimberPower(speed*0.5);
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
