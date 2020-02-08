/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterS extends SubsystemBase {
  private CANSparkMax shooter;
  private double shooterSetpoint;
  /**
   * Creates a new ShooterS.
   */
  public ShooterS() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
