/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ShooterConstants;
import frc.utility.preferences.NomadDoublePreference;

public class ShooterS extends SubsystemBase {
  CANSparkMax spark = new CANSparkMax(ShooterConstants.CAN_ID_SHOOTER_SPARK_MAX, MotorType.kBrushless);
  
  private CANPIDController pidController;
  private CANEncoder encoder;
  public NomadDoublePreference kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, RPM, maxError, armThreshold, fireThreshold, stopThreshold;
  private enum ShooterState {SPINUP, READY, ARMED, RECOVERY, SPINDOWN, STOPPED};
  private int cyclesInRange, ballsFired;
  private ShooterState state = ShooterState.STOPPED;
  /**
   * Creates a new sparkMaxS.
   */
  public ShooterS() {
    spark.restoreFactoryDefaults();
    spark.enableVoltageCompensation(12);

    pidController = spark.getPIDController();

    encoder = spark.getEncoder();


    kP = new NomadDoublePreference("kP", 5e-5);
    kI = new NomadDoublePreference("kI", 1e-6);
    kD = new NomadDoublePreference("kD", 0); 
    kIz = new NomadDoublePreference("kIz", 0); 
    kFF = new NomadDoublePreference("kFF", 0); 
    kMaxOutput = new NomadDoublePreference("maxOutput", 1); 
    kMinOutput = new NomadDoublePreference("minOutput", -1);
    maxRPM = new NomadDoublePreference("MaxRPM", 5600); //5700 max
    RPM = new NomadDoublePreference("RPM", 3000); //5700 max
    maxError = new NomadDoublePreference("MaxError", 100);
    armThreshold = new NomadDoublePreference("ArmingRPM", RPM.getValue() - 100);
    fireThreshold = new NomadDoublePreference("PostShotRPM", RPM.getValue() -200);
    stopThreshold = new NomadDoublePreference("Stopped RPM", 60);
    // set PID coefficients
    pidController.setP(kP.getValue());
    pidController.setI(kI.getValue());
    pidController.setD(kD.getValue());
    pidController.setIZone(kIz.getValue());
    pidController.setFF(kFF.getValue());
    pidController.setOutputRange(kMinOutput.getValue(), kMaxOutput.getValue());

    SmartDashboard.putNumber("SetPoint", 0);
  }

  @Override
  public void periodic() {
    pidController.setP(kP.getValue());
    pidController.setI(kI.getValue());
    pidController.setD(kD.getValue());
    pidController.setIZone(kIz.getValue());
    pidController.setFF(kFF.getValue());
    pidController.setOutputRange(kMinOutput.getValue(), kMaxOutput.getValue());
    SmartDashboard.putNumber("ProcessVariable", encoder.getVelocity());
    SmartDashboard.putString("ShooterState", state.toString());
    updateState();
  }

  public void setVelocityPID(double setPtJstick) {
    double setPoint = setPtJstick * maxRPM.getValue();
    pidController.setReference(setPoint, ControlType.kVelocity);
    SmartDashboard.putNumber("SetPoint", setPoint);
  }

  public void runVelocityPIDrpm() {
    var setPoint = Math.max(-maxRPM.getValue(), Math.min(maxRPM.getValue(), RPM.getValue())); //make sure rpm is within max rpm

    pidController.setReference(setPoint, ControlType.kVelocity);
    SmartDashboard.putNumber("SetPoint", setPoint);
  }

  public void setSpeed(double stickVal) {
    SmartDashboard.putNumber("SetPoint", 0);
    spark.set(stickVal);
  }

  public void spinUp() {
    if(state == ShooterState.STOPPED || state == ShooterState.SPINDOWN){
      state = ShooterState.SPINUP;
      pidController.setReference(ShooterConstants.SHOOTER_RPM, ControlType.kVelocity);
    }  
  }
  
  private void updateState() {
    switch (state){
      case SPINUP:
        if(Math.abs(ShooterConstants.SHOOTER_RPM - encoder.getVelocity()) < maxError.getValue()) { //if we are within range
          cyclesInRange++; // increment the counter
        }
        if(cyclesInRange > ShooterConstants.MIN_LOOPS_IN_RANGE) { //if the counter is high enough
          state = ShooterState.READY; //set state to READY
          cyclesInRange = 0;
        }
        break;
      case READY:
        if(encoder.getVelocity() < armThreshold.getValue()){ //if velocity drops below "we might be shooting" threshold
          state = ShooterState.ARMED;
        } 
        
        break;
      case ARMED:
        if (encoder.getVelocity() < fireThreshold.getValue()) {
          ballsFired++;
          state = ShooterState.RECOVERY;
        }
        else if (encoder.getVelocity() > armThreshold.getValue()){
          state = ShooterState.READY;
        }
        // if it has dropped below "ball has definitely gone through" threshold
        //  increment balls fired.
        //  go straight to RECOVERY
        // if it goes back above the armed threshold go back to ready.
        break;
      case RECOVERY: 
      // if we are back up to setpt speed,
      //  go to READY
        if (encoder.getVelocity() > armThreshold.getValue()){
          state = ShooterState.READY;
        }
        break;
      case SPINDOWN:
        spark.set(0);//set motor to coast mode 0 power.
        if (encoder.getVelocity() < stopThreshold.getValue()) {
          state = ShooterState.STOPPED;
        }// if motor has stopped moving, 
        // go to STOPPED
        break;
      case STOPPED:
        //do nothing until further command.
        break;  
      }    
  }

  public void spinDown() {
    state = ShooterState.SPINDOWN;
  }
}