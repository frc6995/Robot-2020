package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Preferences;
import frc.robot.RobotContainer;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.DrivebaseS;
import frc.utility.preferences.NomadPreference;

/**
 * VisionAlignC
 * 
 * Processes data from Network tables using a Proportional controller in order
 * to Aim the shooter at the top power port
 * 
 * @author Ari Shashivkopanazak
 */
public class DrivebaseVisionC extends CommandBase {
  DrivebaseS drivebase;
  private DifferentialDriveWheelSpeeds wheelSpeeds;
  private DoubleSupplier fwdBack;
  /**
   * PIDController for turning. should input degrees and output rad/sec.
   */
  PIDController turnPid = new PIDController(Preferences.VISION_KP_HORIZONTAL.getValue(), 0, 0);
  /**
   * PIDController for distance. should input degrees and output m/sec.
   */
  PIDController distPid = new PIDController(Preferences.VISION_KP_VERTICAL.getValue(), 0, 0);

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

  /**
   * Get the offset values from network table
   */
  NetworkTableEntry txEntry = table.getEntry("tx");
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
  private double horizontalTarget = 0.0;
  private double verticalTarget = 0.0;

  /**
   * The valid range in +/- degrees to call the aiming valid
   */
  private double horizontalRange = 1;
  private double verticalRange = 1;

  /**
   * Adjustment values
   */
  private double horizontalAdjust = 0.0;
  private double verticalAdjust = 0.0;

  /**
   * Ramps our PID to full over the period of ramp time
   */
  private double clampValue = 0.0;

  /**
   * Determines if this is the first loop in the target Range
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
   * Allows the Robot to Aim the shooter at the top power port
   * Default state:
   * Sets Pipeline to Vision Align
   * Turns off LEDs
   * 
   * @param drivebaseS The DrivebaseS object to use.
   */
  public DrivebaseVisionC(DrivebaseS drivebaseS) {
    pipelineEntry.setDouble(VisionConstants.VISION_PIPELINE);
    ledModeEntry.setDouble(0);
    addRequirements(drivebaseS);
    drivebase = drivebaseS;
    turnPid.setTolerance(horizontalRange);
    distPid.setTolerance(verticalRange);
  }

  /**
   * Determine that this is the first loop
   */
  @Override
  public void initialize() {
    
    firstLoop = true;

    turnPid.setSetpoint(0);
    distPid.setSetpoint(0);
  }

  /**
   * Start the timer
   * After the first loop, first loop returns false
   * Set the pipline to the vision mode
   * Turns on LED
   * Get horizontal position offset and assign it to a double
   * Get vertical position offset and assign it to a double
   * The horizontal point we need to adjust to is defined as our horizontal error times our horizontal P Value
   * The Vertical point we need to adjust to is defined as our vertical error times our vertical P Value
   * Input these values into the drivebases arcadeDrive
   */
  @Override
  public void execute() {
    if (firstLoop) {
      rampTimer.stop();
      rampTimer.reset();
      rampTimer.start();
      firstLoop = false;
    }
    turnPid.setP(Preferences.VISION_KP_HORIZONTAL.getValue());
    distPid.setP(Preferences.VISION_KP_VERTICAL.getValue());

    pipelineEntry.setDouble(VisionConstants.VISION_PIPELINE);
    ledModeEntry.setDouble(2);

    horizontalTarget = txEntry.getDouble(0);
    verticalTarget = tyEntry.getDouble(0);

    horizontalError = -horizontalTarget;
    verticalError = -verticalTarget;

    

    horizontalAdjust = turnPid.calculate(horizontalError);
    verticalAdjust = distPid.calculate(verticalError); 

    clampValue = MathUtil.clamp(rampTimer.get() / VisionConstants.VISION_RAMP_TIME, -1, 1);

    horizontalAdjust = MathUtil.clamp(horizontalAdjust, -clampValue, clampValue);
    verticalAdjust = MathUtil.clamp(verticalAdjust, -clampValue, clampValue);

    wheelSpeeds = DriveConstants.kDriveKinematics.toWheelSpeeds(new ChassisSpeeds(verticalAdjust, 0, Math.toRadians(horizontalAdjust)));
    drivebase.trajectoryDrive(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
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
    if (turnPid.atSetpoint() && distPid.atSetpoint()) {
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
}
