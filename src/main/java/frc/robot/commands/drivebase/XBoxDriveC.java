package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivebaseS;

public class XBoxDriveC extends CommandBase {
  DrivebaseS drivebase;
  GenericHID controller;
  /**
   * Creates a new XBoxDriveC.
   */
  public XBoxDriveC(GenericHID xboxController) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drivebaseS);
    controller = xboxController;
    drivebase = RobotContainer.drivebaseS;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double driveSpeed;
    double turnSpeed;
    double rightTrigger = controller.getRawAxis(3);
    double leftTrigger = controller.getRawAxis(2);

    driveSpeed = rightTrigger - leftTrigger;
    turnSpeed = controller.getRawAxis(0);

    drivebase.arcadeDrive(driveSpeed, turnSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
