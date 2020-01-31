/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SliderS;

public class ManualTranslateC extends CommandBase {
  
private GenericHID input;

  public ManualTranslateC(SliderS sliderS, GenericHID Hid) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sliderS);
    input = Hid;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.sliderS.Translate(0);    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = Math.abs(this.input.getRawAxis(4)) > 0.15 ? this.input.getRawAxis(4) : 0;
    RobotContainer.sliderS.Translate(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.sliderS.Translate(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
