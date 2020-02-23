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
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ShooterConstants;
import frc.utility.preferences.NomadDoublePreference;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;
import io.github.oblarg.oblog.annotations.Log.ToString;

public class ShooterS extends SubsystemBase implements Loggable{
  CANSparkMax spark = new CANSparkMax(ShooterConstants.CAN_ID_SHOOTER_SPARK_MAX, MotorType.kBrushless);
  
  private CANPIDController pidController;
  
  private CANEncoder encoder;
  public NomadDoublePreference kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, RPM, maxError, armThreshold, fireThreshold, stopThreshold;
  private enum ShooterState {SPINUP, READY, ARMED, RECOVERY, SPINDOWN, STOPPED};
  private int cyclesInRange; 
  @Log 
  private int ballsFired;
  //@ToString
  private ShooterState state = ShooterState.STOPPED;
  @Log.Graph(name = "ShooterRPM")
  private double currentRPM;
  /**
   * Creates a new sparkMaxS.
   */
  public ShooterS() {
    spark.restoreFactoryDefaults();
    spark.enableVoltageCompensation(12);
    spark.setInverted(true);
    spark.setSmartCurrentLimit(30);
    spark.setIdleMode(IdleMode.kCoast);
    spark.burnFlash();
    pidController = spark.getPIDController();

    encoder = spark.getEncoder();
    


    kP = new NomadDoublePreference("kP", 0);
    kI = new NomadDoublePreference("kI", 0);
    kD = new NomadDoublePreference("kD", 0); 
    kIz = new NomadDoublePreference("kIz", 0); 
    kFF = new NomadDoublePreference("kFF", 0); 
    kMaxOutput = new NomadDoublePreference("maxOutput", 1); 
    kMinOutput = new NomadDoublePreference("minOutput", -1);
    maxRPM = new NomadDoublePreference("MaxRPM", 5600); //5700 max
    RPM = new NomadDoublePreference("RPM", 1000); //5700 max
    maxError = new NomadDoublePreference("MaxError", 500);
    armThreshold = new NomadDoublePreference("ArmingRPM", ShooterConstants.SHOOTER_RPM - 100);
    fireThreshold = new NomadDoublePreference("PostShotRPM", ShooterConstants.SHOOTER_RPM -200);
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
    currentRPM = encoder.getVelocity();
    updateState();
  }

  public void runVelocityPIDrpm(double RPM) {

    pidController.setReference(RPM, ControlType.kVelocity, 0,
      ShooterConstants.SHOOTER_FEEDFORWARD.calculate(currentRPM));
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
        runVelocityPIDrpm(ShooterConstants.SHOOTER_RPM);
        if(Math.abs(ShooterConstants.SHOOTER_RPM - currentRPM) < maxError.getValue()) { //if we are less than maxError over out target
          cyclesInRange++; // increment the counter
        }
        if(cyclesInRange > ShooterConstants.MIN_LOOPS_IN_RANGE) { //if the counter is high enough
          state = ShooterState.READY; //set state to READY
          cyclesInRange = 0;
        }
        break;
      case READY:
        
        if(currentRPM < armThreshold.getValue()){ //if velocity drops below "we might be shooting" threshold
          state = ShooterState.ARMED;
        } 
        
        break;
      case ARMED:
        if (currentRPM < fireThreshold.getValue()) {
          ballsFired++;
          state = ShooterState.RECOVERY;
        }
        else if (currentRPM > armThreshold.getValue()){
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
        if (currentRPM > armThreshold.getValue()){
          state = ShooterState.READY;
        }
        break;
      case SPINDOWN:
        spark.set(0);
        //pidController.setReference(0.0, ControlType.kVoltage);//set motor to coast mode 0 power.
        if (currentRPM < stopThreshold.getValue()) {
          state = ShooterState.STOPPED;
        }// if motor has stopped moving, 
        // go to STOPPED
        break;
      case STOPPED:
        pidController.setReference(0.0, ControlType.kVoltage);
        //do nothing until further command.
        break;  
      }    
  }

  public void spinDown() {
    state = ShooterState.SPINDOWN;
  }

  public void stop() {
    state = ShooterState.STOPPED;
  }

  public boolean isReady() {
    return state == ShooterState.READY;
  }
}