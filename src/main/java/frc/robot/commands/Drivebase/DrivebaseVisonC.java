package frc.robot.commands.Drivebase;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DrivebaseVisonC extends CommandBase {

  /**
   * Network Table Values
   */
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

  /**
   * Get the offset values from network table
   */
  NetworkTableEntry txEntry = table.getEntry("tx`");
  NetworkTableEntry tyEntry = table.getEntry("ty");
  
  /**
   * Get the current LED mode
   */
  NetworkTableEntry ledModeEntry = table.getEntry("ledmode");

  /**
   * Get the current Pipeline, may not need
   */
  NetworkTableEntry pipelineEntry = table.getEntry("pipeline");

  /**
   * Error of our offsets
   */
  private double horizontalError = 0.0;
  private double verticalError = 0.0;

  /**
   * The Target Position
   */
  private double targetHorizontal = 0.0;
  private double targetVertical = 0.0;

  /**
   * Ramps our PID to full over the period of ramp time
   */
  private double clampValue = 0.0;

  /**
   * adjustment values
   */
  private double horizontalAdjust = 0.0;
  private double verticalAdjust = 0.0;

  /**
   * the valid range in +/- degrees to call the aiming valid
   */
  private double horizontalRange = 1;
  private double verticalRange = 1;

  /**
   * determines if this is the first loop in the target Range
   */
  private boolean firstLoop = true;

  /**
   * Increments if we are in the target zone
   */
  private int sumInRange = 0;

  /**
   * Increment limit to be called a successful aim
   */
  private int waitInRange = 10;

  /**
   * General WPILIB Timer
   */
  private Timer rampTimer = new Timer();

  /**
   * Set Pipeline to Vision Align
   * Turn off LEDs
   */
  public DrivebaseVisonC() {
    pipelineEntry.setDouble(Constants.VISION_PIPELINE);
    ledModeEntry.setDouble(0);
  }

  /**
   * Determine that this is the first loop
   */
  @Override
  public void initialize() {
    addRequirements(RobotContainer.drivebaseS);
    firstLoop = true;
  }

  /**
   * start the timer
   * after the first loop, first loop returns false
   * set the pipline to the vision mode
   * turns on LED
   * get horizontal position offset and assign it to a double
   * get vertical position offset and assign it to a double
   * the horizontal point we need to adjust to is defined as our horizontal error times our horizontal P Value
   * the Vertical point we need to adjust to is defined as our vertical error times our vertical P Value
   * input these values into the drivebases arcadeDrive
   */
  @Override
  public void execute() {
    if (firstLoop) {
      rampTimer.stop();
      rampTimer.reset();
      rampTimer.start();
      firstLoop = false;
    }

    pipelineEntry.setDouble(Constants.VISION_PIPELINE);
    ledModeEntry.setDouble(2);

    targetHorizontal = txEntry.getDouble(0);
    targetVertical = tyEntry.getDouble(0);

    horizontalError = -targetHorizontal;
    verticalError = -targetVertical;

    horizontalAdjust = horizontalError * Constants.VISION_KP_HORIZONTAL;
    verticalAdjust = verticalError * Constants.VISION_KP_VERTICAL;

    clampValue = clamp(rampTimer.get() / Constants.VISION_RAMP_TIME, 1);

    horizontalAdjust = clamp(horizontalAdjust, clampValue);
    verticalAdjust = clamp(verticalAdjust, clampValue);

    RobotContainer.drivebaseS.arcadeDrive(horizontalAdjust, verticalAdjust);    
  }

  /**
   * Stop Ramp timer
   * Reset to First loop
   * turn off the leds
   */
  @Override
  public void end(boolean interrupted) {
    rampTimer.stop();
    rampTimer.reset();
    firstLoop = true;
    ledModeEntry.setDouble(0);
  }

  /**
   * When Crosshairs are within the range, 
   * @return the count to the amount needed to end the command
   */
  @Override
  public boolean isFinished() {
    if (Math.abs(targetHorizontal) <= horizontalRange && Math.abs(targetVertical) <= verticalRange) {
      sumInRange++;
    }
    else {
      sumInRange = 0;
    }

    if (sumInRange >= waitInRange) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * if within max range and min range, return number
   * else return max range or min range value
   * @param val Min Num
   * @param mag Max Num
   * @return Value between max and min
   */
  private static double clamp(double val, double mag) {
    return Math.max(-mag, Math.min(mag, val));
  }
}
