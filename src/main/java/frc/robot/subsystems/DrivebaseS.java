/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Preferences;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.utility.NomadUnits;
import frc.wrappers.MotorControllers.NomadTalonSRX;
import frc.wrappers.MotorControllers.NomadVictorSPX;

/**
 * This subsystem is for the drivetrain, which is made up of two master Talons
 * and two sets of Victors, each on a side of the drivetrain.
 */
public class DrivebaseS implements Subsystem {
  /**
   * The left master NomadTalonSRX
   */
  private NomadTalonSRX leftMasterTalon = new NomadTalonSRX(DriveConstants.CAN_ID_DRIVE_LEFT_MASTER);
  /**
   * the right master NomadTalonSRX
   */
  private NomadTalonSRX rightMasterTalon = new NomadTalonSRX(DriveConstants.CAN_ID_DRIVE_RIGHT_MASTER, true);
  /**
   * The configuration for the Talons.
   */
  private TalonSRXConfiguration talonConfig = new TalonSRXConfiguration();
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

  /**
   * The NetworkTableEntry for the current gyro heading;
   */
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  /**
   * Creates a new DrivebaseS.
   */
  public DrivebaseS() {
    for (int i : DriveConstants.ARRAY_CAN_ID_DRIVE_LEFT) { //assume the slaves are Victor SPXs
      leftSlaveVictors.add(new NomadVictorSPX(i, false, leftMasterTalon));
    }
    for (int i : DriveConstants.ARRAY_CAN_ID_DRIVE_RIGHT) { //assume the slaves are Victor SPXs
      rightSlaveVictors.add(new NomadVictorSPX(i, true, rightMasterTalon));
    }
    talonConfig.slot0.kP = Preferences.drivekP.getValue();
    leftMasterTalon.config_kP(0, 0.0309);
    leftMasterTalon.setSensorPhase(true);
    rightMasterTalon.config_kP(0, 0.0209);
    differentialDrive.setRightSideInverted(false);
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
    odometry.update(Rotation2d.fromDegrees(getHeading()), getLeftEncoderMeters(),
                      getRightEncoderMeters());
    updateTelemetry();

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
    rightMasterTalon.setVoltage(-1 * rightVolts); //Inverted to match what diffy drive does
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
    return (getLeftEncoderMeters() + getRightEncoderMeters()) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public double getLeftEncoderMeters() {
    return NomadUnits.DBTicksToMeters(leftMasterTalon.getSelectedSensorPosition());
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public double getRightEncoderMeters() {
    return NomadUnits.DBTicksToMeters(rightMasterTalon.getSelectedSensorPosition());
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
    return Math.IEEEremainder(gyro.getAngle(), 360) * (DriveConstants.GYRO_REVERSED ? -1.0 : 1.0);
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return gyro.getRate() * (DriveConstants.GYRO_REVERSED ? -1.0 : 1.0);
  }

  /**
   * Returns the left encoder rate in meters per second.
   * 
   * @return the left encoder rate in meters per second.
   */
  public double getLeftEncoderRate() {
    return NomadUnits.DBTicksToMeters((leftMasterTalon.getSelectedSensorVelocity() * 10)); /*because Talon reports per 100ms*/
  }

    /**
   * Returns the right encoder rate in meters per second.
   * 
   * @return the right encoder rate in meters per second.
   */
  public double getRightEncoderRate() {
    return NomadUnits.DBTicksToMeters(rightMasterTalon.getSelectedSensorVelocity() * 10); /* because Talon reports per 100ms */
  }
  /**
   * Uses Talon Velocity Control to drive the robot with arbitrary feed forward. Used for auto.
   * @param leftSpeed Left side speed in meters per second.
   * @param rightSpeed Right side speed in meters per second.
   */
  public void trajectoryDrive(double leftSpeed, double rightSpeed){
    leftMasterTalon.set(ControlMode.Velocity,
      NomadUnits.DBMetersToTicks(leftSpeed) / 10,
      DemandType.ArbitraryFeedForward,
      AutoConstants.trajectoryFeedForward.calculate(leftSpeed) / 12);
    rightMasterTalon.set(ControlMode.Velocity,
      NomadUnits.DBMetersToTicks(rightSpeed) / 10,
      DemandType.ArbitraryFeedForward,
      AutoConstants.trajectoryFeedForward.calculate(rightSpeed) / 12);
  }

  public void updateTelemetry(){
    SmartDashboard.putNumber("leftEncoderMeters", getLeftEncoderMeters());
    SmartDashboard.putNumber("rightEncoderMeters", getRightEncoderMeters());
    SmartDashboard.putNumber("leftEncoderRate", getLeftEncoderRate());
    SmartDashboard.putNumber("rightEncoderRate", getRightEncoderRate());
    SmartDashboard.putNumber("gyro heading", getHeading());
    leftMasterTalon.config_kP(0, Preferences.drivekP.getValue());
    rightMasterTalon.config_kP(0, Preferences.drivekP.getValue());
    
  }
}
