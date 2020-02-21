package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotPreferences;

public class GyroPID extends CommandBase {
  /**
   * The desired angle for the robot.
   */
  private double desiredAngle;
  /**
   * true if robot should turn left, false if it should turn right
   */
  private boolean turnLeft;
/**
 * How long we've been in the setPoint.
 */
  private int timeWithinSetpoint = 0;
  /**
   * The minimum amount of time within setPoint;
   */
  private final int setPointLoops = 2;
  
  private class DoubleRange{        
    public double max;
    public double min;
    public DoubleRange(double maximum, double minimum){
      max = maximum;
      min = minimum;
    }
  };
  private DoubleRange range;
  
/**
 * Create a new GyroPID
 * @param angleToGoTo what angle should the robot PID to.
 */
  public GyroPID(double angleToGoTo) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drivebaseS);
    desiredAngle = angleToGoTo;
  }

  // Called when the command is initially scheduled.
  /**
   * Determines whether the robot should turn left or right
   */
  @Override
  public void initialize() {
     if (RobotContainer.drivebaseS.getDegrees() > desiredAngle)
     turnLeft = RobotContainer.drivebaseS.getDegrees() - desiredAngle < 180 ? true : false;
     else turnLeft = desiredAngle - RobotContainer.drivebaseS.getDegrees() < 180 ? false : true;
     
     RobotContainer.drivebaseS.setDrivebasePIDConstants(RobotPreferences.gyroPidD.getValue(),
      RobotPreferences.gyroPidI.getValue(), RobotPreferences.gyroPidD.getValue(), 2);

      range = new DoubleRange(desiredAngle + 2, desiredAngle - 2);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (turnLeft){
        // <insert left turn pid here>
    }
    else {
      // <insert right turn pid here>
      
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
