package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.DrivebaseS;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;

public class GyroPidC extends ProfiledPIDCommand implements Loggable{
  private DrivebaseS drivebaseS;
  /**
   * How long we've been in the setPoint.
   */
  private int timeWithinSetpoint = 0;
  /**
   * The minimum amount of time within setPoint;
   */
  private final int setPointLoops = 2;

  @Config()
  public static ProfiledPIDController anglePID = new ProfiledPIDController(RobotPreferences.gyroPidP.getValue(),
      RobotPreferences.gyroPidI.getValue(), RobotPreferences.gyroPidD.getValue(), new Constraints(30, 15));
  /**
   * is the robot within the angle range?
   */
  private boolean withinRange;

  /**
   * Create a new GyroPID
   * 
   * @param angleToGoTo what angle should the robot PID to.
   */
  public GyroPidC(double angleToGoTo, DrivebaseS drivebase) {
    // Use addRequirements() here to declare subsystem dependencies.
    super(anglePID, ()->-drivebase.getDegrees(), angleToGoTo, (output, setpoint) -> drivebase.turnDrive(output), drivebase);
    drivebaseS = drivebase;
    addRequirements(drivebaseS);
    getController().setTolerance(4);
    getController().enableContinuousInput(-180, 180);
    getController().setGoal(new State(angleToGoTo, 0));
  }

  // Called when the command is initially scheduled.
  /**
   * Determines whether the robot should turn left or right
   */
  @Override
  public void initialize() {
    
    drivebaseS.setDrivebasePIDConstants(RobotPreferences.gyroPidD.getValue(), RobotPreferences.gyroPidI.getValue(),
        RobotPreferences.gyroPidD.getValue(), 2);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (getController().atGoal()) {
      timeWithinSetpoint++;
    }  
    else timeWithinSetpoint = 0;

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timeWithinSetpoint > setPointLoops;
  }
}
