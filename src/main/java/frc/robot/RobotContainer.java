/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.Constants.CONTROLLER_TYPE;
import frc.robot.commands.BasicAutoCG;
import frc.robot.commands.Slider.IntakeDeployC;
import frc.robot.commands.Slider.IntakeRetractC;
import frc.robot.subsystems.DrivebaseS;
import frc.robot.subsystems.IntakeS;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  
  @log(name="IntakeS")
  public final static IntakeS intakeS = new IntakeS();
  
  private final DrivebaseS drivebaseS = new DrivebaseS();
  private final BasicAutoCG basicAutoCG = new BasicAutoCG();
  private final IntakeDeployC intakeDeployC;
  private final IntakeRetractC intakeRetractC;
  private final GenericHID driveController;
  private final Command driveStickC;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //Initializes driveController as either a Joystick or Xbox depending on Constants.DRIVE_CONTROLLER_TYPE.
    if (Constants.DRIVE_CONTROLLER_TYPE == CONTROLLER_TYPE.Joystick) {
      driveController = new Joystick(Constants.OI_DRIVE_CONTROLLER);
    }
    else {
      driveController = new XboxController(Constants.OI_DRIVE_CONTROLLER);
    }
    //Initializes the driveStickC command inline. Simply passes the drive controller axes into the drivebaseS arcadeDrive.
    driveStickC = new RunCommand(() -> drivebaseS.arcadeDrive(driveController.getRawAxis(Constants.AXIS_DRIVE_FWD_BACK), driveController.getRawAxis(Constants.AXIS_DRIVE_TURN)), drivebaseS);
    //Turn off LiveWindow telemetry. We don't use it and it takes 90% of the loop time.
    LiveWindow.disableAllTelemetry();
    intakeDeployC = new IntakeDeployC();
    intakeRetractC = new IntakeRetractC();

    // Configure the button bindings
    configureButtonBindings();

    drivebaseS.setDefaultCommand(driveStickC);

    // defaults to Retracted state
    intakeS.setDefaultCommand(intakeRetractC);
  }

  /**
   * Use this method to define your button-&gt;command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driveController, 3).whenPressed(intakeDeployC);
    new JoystickButton(driveController, 8).whenPressed(intakeRetractC);
  }

  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return basicAutoCG;
  }
}
