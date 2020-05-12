package frc.robot.commands.climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.RobotLEDS;
import frc.robot.subsystems.RobotLEDS.ledStates;

/**
 * Manually move Climber
 * 
 * @author Sammcdo
 */
public class ClimberManualC extends CommandBase {
  private DoubleSupplier power;
  private ClimberS climber;

  public ClimberManualC(ClimberS climberS, DoubleSupplier pwr) {
    this.climber = climberS;
    addRequirements(climberS);
    this.power = pwr;
  }

  @Override
  public void initialize() {
    this.climber.setClimberPower(0);
  }
 
  @Override
  public void execute() {

    
    double speed = this.power.getAsDouble();
    if (Math.abs(speed) < 0.1) {
      speed = 0;
    }
    else {
      RobotLEDS.robotLEDS.currentState = ledStates.Climbing;
    }

    SmartDashboard.putNumber("ClimberSpeed", speed);
    this.climber.setClimberPower(speed * 0.5);

  }

  @Override
  public void end(boolean interrupted) {
    this.climber.setClimberPower(0);
    RobotLEDS.robotLEDS.revertLEDS();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
