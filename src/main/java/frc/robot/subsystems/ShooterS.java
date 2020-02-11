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

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ShooterConstants;

public class ShooterS extends SubsystemBase {
  CANSparkMax spark = new CANSparkMax(ShooterConstants.CA, MotorType.kBrushless);
  
  private CANPIDController m_pidController;
  private CANEncoder m_encoder;
  public NomadDoublePreference kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, RPM;
  /**
   * Creates a new sparkMaxS.
   */
  public ShooterS() {
    spark.restoreFactoryDefaults();
    spark.enableVoltageCompensation(12);

    m_pidController = spark.getPIDController();

    m_encoder = spark.getEncoder();


    kP = new NomadDoublePreference("kP", 5e-5);
    kI = new NomadDoublePreference("kI", 1e-6);
    kD = new NomadDoublePreference("kD", 0); 
    kIz = new NomadDoublePreference("kIz", 0); 
    kFF = new NomadDoublePreference("kFF", 0); 
    kMaxOutput = new NomadDoublePreference("maxOutput", 1); 
    kMinOutput = new NomadDoublePreference("minOutput", -1);
    maxRPM = new NomadDoublePreference("MaxRPM", 5600); //5700 max
    RPM = new NomadDoublePreference("RPM", 3000); //5700 max

    // set PID coefficients
    m_pidController.setP(kP.getValue());
    m_pidController.setI(kI.getValue());
    m_pidController.setD(kD.getValue());
    m_pidController.setIZone(kIz.getValue());
    m_pidController.setFF(kFF.getValue());
    m_pidController.setOutputRange(kMinOutput.getValue(), kMaxOutput.getValue());

    SmartDashboard.putNumber("SetPoint", 0);
  }

  @Override
  public void periodic() {
    m_pidController.setP(kP.getValue());
    m_pidController.setI(kI.getValue());
    m_pidController.setD(kD.getValue());
    m_pidController.setIZone(kIz.getValue());
    m_pidController.setFF(kFF.getValue());
    m_pidController.setOutputRange(kMinOutput.getValue(), kMaxOutput.getValue());
    SmartDashboard.putNumber("ProcessVariable", m_encoder.getVelocity());
  }

  public void setVelocityPID(double setPtJstick) {
    double setPoint = setPtJstick * maxRPM.getValue();
    m_pidController.setReference(setPoint, ControlType.kVelocity);
    SmartDashboard.putNumber("SetPoint", setPoint);
  }

  public void runVelocityPIDrpm() {
    var setPoint = Math.max(-maxRPM.getValue(), Math.min(maxRPM.getValue(), RPM.getValue())); //make sure rpm is within max rpm
    m_pidController.setReference(setPoint, ControlType.kVelocity);
    SmartDashboard.putNumber("SetPoint", setPoint);
  }

  public void setSpeed(double stickVal) {
    SmartDashboard.putNumber("SetPoint", 0);
    spark.set(stickVal);
  }
}

}
