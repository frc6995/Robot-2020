package frc.robot.commands.climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.RobotLEDS;
import frc.robot.subsystems.RobotLEDS.ledStates;

public class ClimberManualC extends CommandBase {
  private DoubleSupplier power;
  private ClimberS climber;
  private boolean firstLoop = true;
  
  /**
   * Creates a new ClimberManualC.
   */
  public ClimberManualC(ClimberS climberS, DoubleSupplier pwr) {
    this.climber = climberS;
    addRequirements(climberS);
    this.power = pwr;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.climber.setClimberPower(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    
    double speed = this.power.getAsDouble();
    if (Math.abs(speed) < 0.1) {
      speed = 0;
    }
    else RobotLEDS.robotLEDS.currentState = ledStates.Climbing;
    /*
     * if (Math.abs(speed) < 0.1) { speed = 0; }
     */
    SmartDashboard.putNumber("ClimberSpeed", speed);
    this.climber.setClimberPower(speed * 0.5);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.climber.setClimberPower(0);
    RobotLEDS.robotLEDS.revertLEDS();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
