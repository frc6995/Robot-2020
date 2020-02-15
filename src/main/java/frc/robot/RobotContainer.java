package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Slider.IntakeDeployC;
import frc.robot.commands.Slider.IntakeRetractC;
import frc.robot.commands.auto.NomadPathFollowerCommandBuilder;
import frc.robot.commands.drivebase.DrivebaseVisionC;
import frc.robot.commands.drivebase.EmptyAutoCG;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.DriveConstants.CONTROLLER_TYPE;
import frc.robot.constants.DrivebaseConstants;
import frc.robot.constants.Trajectories;
import frc.robot.subsystems.DrivebaseS;
import frc.robot.subsystems.IntakeS;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  
  public final static IntakeS intakeS = new IntakeS();
  public final DrivebaseS drivebaseS = new DrivebaseS();
  private final EmptyAutoCG basicAutoCG = new EmptyAutoCG();
  private final SequentialCommandGroup sCurveRightAutoCG 
    = new NomadPathFollowerCommandBuilder(Trajectories.sCurveRight, drivebaseS).buildPathFollowerCommandGroup();
    private final SequentialCommandGroup straight2mAutoCG 
    = new NomadPathFollowerCommandBuilder(Trajectories.straight2m, drivebaseS).buildPathFollowerCommandGroup();  
  public final GenericHID driveController;
  private final Command driveStickC;
  private DoubleSupplier fwdBackAxis;
  private final DrivebaseVisionC visionAlignC;

  private final IntakeDeployC intakeDeployCG;
  private final IntakeRetractC intakeRetractCG;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //Initializes driveController as either a Joystick or Xbox depending on DriveConstants.DRIVE_CONTROLLER_TYPE.
    if (DriveConstants.DRIVE_CONTROLLER_TYPE == CONTROLLER_TYPE.Joystick) {
      driveController = new Joystick(DriveConstants.OI_DRIVE_CONTROLLER);
    }
    else {
      driveController = new XboxController(DriveConstants.OI_DRIVE_CONTROLLER);
    }
    fwdBackAxis = () -> -driveController.getRawAxis(DriveConstants.AXIS_DRIVE_FWD_BACK);
    //Initializes the driveStickC command inline. Simply passes the drive controller axes into the drivebaseS arcadeDrive.
    driveStickC = new RunCommand(() -> drivebaseS.arcadeDrive(driveController.getRawAxis(DrivebaseConstants.AXIS_DRIVE_FWD_BACK), driveController.getRawAxis(DrivebaseConstants.AXIS_DRIVE_TURN)), drivebaseS);
    visionAlignC = new DrivebaseVisionC(drivebaseS);
    //Turn off LiveWindow telemetry. We don't use it and it takes 90% of the loop time.
    LiveWindow.disableAllTelemetry();
    intakeDeployCG = new IntakeDeployC();
    intakeRetractCG = new IntakeRetractC();

    // Configure the button bindings
    configureButtonBindings();

    drivebaseS.setDefaultCommand(driveStickC);

    // defaults to Retracted state
    intakeS.setDefaultCommand(intakeRetractCG);
  }

  /**
   * Use this method to define your button-&gt;command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //TODO: Figure out these buttons
    new JoystickButton(driveController, 2).whileHeld(intakeDeployCG);
    new JoystickButton(driveController, 2).whenReleased(intakeRetractCG);
    
    new JoystickButton(driveController, 4).whileHeld(visionAlignC);
    
  }

  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return sCurveRightAutoCG;
  }
}
