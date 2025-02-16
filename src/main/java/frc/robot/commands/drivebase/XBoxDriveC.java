package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivebaseS;

/**
 * Drivebase Command
 * 
 * @author JoeyFabel
 */
public class XBoxDriveC extends CommandBase {
  DrivebaseS drivebase;
  GenericHID controller;

  public XBoxDriveC(GenericHID xboxController) {
    addRequirements(RobotContainer.drivebaseS);
    controller = xboxController;
    drivebase = RobotContainer.drivebaseS;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double driveSpeed;
    double turnSpeed;
    double rightTrigger = controller.getRawAxis(3);
    double leftTrigger = controller.getRawAxis(2);

    if (rightTrigger > -0.02 && rightTrigger < 0.02) rightTrigger = 0;
    if (leftTrigger > -0.02 && leftTrigger < 0.02) leftTrigger = 0;

    driveSpeed = rightTrigger-leftTrigger;
    turnSpeed = controller.getRawAxis(0);

    if (turnSpeed > -0.02 && turnSpeed < 0.02) turnSpeed = 0;

    drivebase.arcadeDrive(driveSpeed, turnSpeed*0.8);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
