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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.commands.hopper.HopperIdleBallsC;
import frc.robot.commands.shooter.ShooterWaitUntilFireC;
import frc.robot.constants.ShooterConstants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;
import io.github.oblarg.oblog.annotations.Log.ToString;

public class ShooterS extends SubsystemBase implements Loggable{
  CANSparkMax spark = new CANSparkMax(ShooterConstants.CAN_ID_SHOOTER_SPARK_MAX, MotorType.kBrushless);
  
  private CANPIDController pidController;
  
  private CANEncoder encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, rpm, maxError, armThreshold, fireThreshold, stopThreshold;
  private enum ShooterState {SPINUP, READY, ARMED, RECOVERY, SPINDOWN, STOPPED};
  private int cyclesInRange; 
  @Log 
  private int ballsFired;
  @ToString
  private ShooterState state = ShooterState.STOPPED;
  @Log.Graph(name = "ShooterRPM")
  private double currentRpm;
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
    


    kP = 0;
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = ShooterConstants.SHOOTER_MAX_RPM; //5700 max
    maxError = 500;
    //@Config(name= "ShooterPrefs/ArmingRPM")
    armThreshold = ShooterConstants.SHOOTER_RPM - 25;
    //@Config(name = "ShooterPrefs/PostShotRPM")
    fireThreshold = ShooterConstants.SHOOTER_RPM - 50;
    stopThreshold = 60;
    // set PID coefficients
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
    pidController.setOutputRange(kMinOutput, kMaxOutput);

    SmartDashboard.putNumber("SetPoint", 0);
  }

  @Override
  public void periodic() {
    currentRpm = encoder.getVelocity();
    updateState();
  }

  public void runVelocityPidRpm(double rpm) {
    //spark.set(ShooterConstants.SHOOTER_FEEDFORWARD.calculate(currentRPM));
    rpm = MathUtil.clamp(rpm, -ShooterConstants.SHOOTER_MAX_RPM, ShooterConstants.SHOOTER_MAX_RPM);
    SmartDashboard.putNumber("SetPoint", rpm);
    pidController.setReference(rpm, ControlType.kVelocity, 0,
      ShooterConstants.SHOOTER_FEEDFORWARD.calculate(rpm));
  }

  public void setSpeed(double stickVal) {
    SmartDashboard.putNumber("SetPoint", 0);
    stickVal = MathUtil.clamp(stickVal, -0.8, 0.8);
    spark.set(stickVal);
  }

  public void spinUp() {
    if(state == ShooterState.STOPPED || state == ShooterState.SPINDOWN){
      state = ShooterState.SPINUP;
    }  
  }
  
  private void updateState() {
    switch (state){
      case SPINUP:
        runVelocityPidRpm(ShooterConstants.SHOOTER_RPM);
        if(Math.abs(ShooterConstants.SHOOTER_RPM - currentRpm) < maxError) { //if we are less than maxError over out target
          cyclesInRange++; // increment the counter
        }
        if(cyclesInRange > ShooterConstants.MIN_LOOPS_IN_RANGE) { //if the counter is high enough
          state = ShooterState.READY; //set state to READY
          cyclesInRange = 0;
        }
        break;
      case READY:
        
        if(currentRpm < armThreshold){ //if velocity drops below "we might be shooting" threshold
          state = ShooterState.ARMED;
        } 
        if(currentRpm < fireThreshold){ //if velocity drops below "we might be shooting" threshold
        ballsFired++;
        state = ShooterState.RECOVERY;

      }  
        
        break;
      case ARMED:
        if (currentRpm < fireThreshold) {
          ballsFired++;
          state = ShooterState.RECOVERY;
        }
        else if (currentRpm > armThreshold){
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
        if (currentRpm > armThreshold){
          state = ShooterState.READY;
        }
        break;
      case SPINDOWN:
        spark.set(0);
        //pidController.setReference(0.0, ControlType.kVoltage);//set motor to coast mode 0 power.
        if (currentRpm < stopThreshold) {
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
  @Log.BooleanBox
  public boolean isReady() {
    return state == ShooterState.READY;
  }

  public int getBallsFired() {
    return ballsFired;
  }

  public Command buildSingleShootSequence(HopperS hopper, ShooterS shooter){
    return new RunCommand( ()-> {}).withInterrupt( () -> shooter.isReady())
    .andThen(new ParallelDeadlineGroup(new ShooterWaitUntilFireC(shooter, 1), new HopperIdleBallsC(hopper)));
  }

  public Command buildMultipleShootSequence(HopperS hopper, ShooterS shooter, int ammo){
    Command sequence = buildSingleShootSequence(hopper, shooter);
    for( int i = 0; i < ammo; i++) {
      sequence = sequence.andThen(buildSingleShootSequence(hopper, shooter));
    }
    return sequence;
  }

  
}