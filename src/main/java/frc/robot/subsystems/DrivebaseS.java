/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.Constants;
import frc.wrappers.MotorControllers.*;


/**
 * This subsystem is for the drivetrain, which is made up of two master Talons and two sets of Victors, each on a side of the drivetrain.
 */
public class DrivebaseS implements Subsystem {
  /**
   * The left master NomadTalonSRX
   */
  private NomadTalonSRX leftMasterTalon = new NomadTalonSRX(Constants.CAN_ID_DRIVE_LEFT_MASTER);
  /**
   * the right master NomadTalonSRX
   */
  private NomadTalonSRX rightMasterTalon = new NomadTalonSRX(Constants.CAN_ID_DRIVE_RIGHT_MASTER, true);
  /**
   * An ArrayList of NomadVictorSPXs for the left side of the drivebase.
   */
  private ArrayList<NomadVictorSPX> leftSlaveVictors = new ArrayList<>();
  /**
   * An ArrayList of NomadVictorSPXs for the left side of the drivebase.
   */
  private ArrayList<NomadVictorSPX> rightSlaveVictors = new ArrayList<>();
  /**
   * The DifferentialDrive object containing the master Talons.
   */
  private DifferentialDrive differentialDrive = new DifferentialDrive(leftMasterTalon, rightMasterTalon);
  
  /**
   * Our NavX gyro.
   */
  private AHRS gyro;

  /**
   * The Odometry object for tracking our position;
   */
  private DifferentialDriveOdometry odometry;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  /**
   * Creates a new DrivebaseS.
   */
  public DrivebaseS() {
    for (int i : Constants.ARRAY_CAN_ID_DRIVE_LEFT) { //assume the slaves are Victor SPXs
      leftSlaveVictors.add(new NomadVictorSPX(i, false, leftMasterTalon));
    }
    for (int i : Constants.ARRAY_CAN_ID_DRIVE_RIGHT) { //assume the slaves are Victor SPXs
      rightSlaveVictors.add(new NomadVictorSPX(i, true, rightMasterTalon));
    }

    gyro = new AHRS(SerialPort.Port.kMXP);
    odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
  }
  /**
   * Calls the DifferentialDrive arcadeDrive method
   * @param driveSpeed -1 to 1, forward-backward speed.
   * @param turnSpeed -1 to 1, turning speed.
   */
  public void arcadeDrive(double driveSpeed, double turnSpeed) {
    differentialDrive.arcadeDrive(driveSpeed, turnSpeed);
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    odometry.update(Rotation2d.fromDegrees(getHeading()), getLeftEncoder(),
                      getRightEncoder());
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getLeftEncoderRate(), getRightEncoderRate());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMasterTalon.setVoltage(leftVolts);
    rightMasterTalon.setVoltage(rightVolts);
  }

  /**
   * Resets the drive encoders to currently read a position of 0.
   */
  public void resetEncoders() {
    leftMasterTalon.setSelectedSensorPosition(0);
    rightMasterTalon.setSelectedSensorPosition(0);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (getLeftEncoder() + getRightEncoder()) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public double getLeftEncoder() {
    return leftMasterTalon.getSelectedSensorPosition();
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public double getRightEncoder() {
    return rightMasterTalon.getSelectedSensorPosition();
  }

  /**
   * Sets the max output of the drive.  Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    differentialDrive.setMaxOutput(maxOutput);
  }

  /**
   * Zeroes the heading of the robot.
   */
  public void zeroHeading() {
    gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from 180 to 180
   */
  public double getHeading() {
    return Math.IEEEremainder(gyro.getAngle(), 360) * (Constants.GYRO_REVERSED ? -1.0 : 1.0);
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return gyro.getRate() * (Constants.GYRO_REVERSED ? -1.0 : 1.0);
  }

  /**
   * Returns the left encoder rate in meters per second.
   * 
   * @return the left encoder rate in meters per second.
   */
  public double getLeftEncoderRate() {
    return leftMasterTalon.getSelectedSensorVelocity() * Constants.ENCODER_CNTS_PER_WHEEL_REV * 10 /*because Talon reports per 100ms*/;
  }

    /**
   * Returns the right encoder rate in meters per second.
   * 
   * @return the right encoder rate in meters per second.
   */
  public double getRightEncoderRate() {
    return rightMasterTalon.getSelectedSensorVelocity() * Constants.ENCODER_CNTS_PER_WHEEL_REV * 10 /*because Talon reports per 100ms*/;
  }
}
