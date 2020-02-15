package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.CONTROLLER_TYPE;
import frc.robot.commands.BasicAutoCG;
import frc.robot.commands.ManualTranslateC;
import frc.robot.commands.climber.ClimberHomeC;
import frc.robot.commands.climber.ClimberManualC;
import frc.robot.commands.climber.ClimberPullupCG;
import frc.robot.commands.climber.ClimberUpPIDC;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.DrivebaseS;
import frc.robot.subsystems.SliderS;
import io.github.oblarg.oblog.annotations.Log;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  
  private final GenericHID driveController;
  public final GenericHID operatorController;
  
  @Log(name="DrivebaseS")
  public static final DrivebaseS drivebaseS = new DrivebaseS();
  @Log(name="ClimberS")
  public static final ClimberS climberS = new ClimberS();
  @Log(name="SliderS")
  public static final SliderS sliderS = new SliderS();

  private final BasicAutoCG basicAutoCG = new BasicAutoCG();
  private final CameraServer server = CameraServer.getInstance();
  private final UsbCamera camera = new UsbCamera("cam0", 0);
  private final Command driveStickC;
  private final ManualTranslateC manualTranslateC;
  private final ClimberManualC manualClimbC;
  public final ClimberHomeC climberHomeC;
  private final Command climberBrakeOnC;
  public final Command climberBrakeOffC;
  private final Command resetEncodersC;
  private final ClimberUpPIDC climberUpPIDC;
  private final ClimberPullupCG climberPullupCG;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Initializes driveController as either a Joystick or Xbox depending on
    // Constants.DRIVE_CONTROLLER_TYPE.
    if (Constants.DRIVE_CONTROLLER_TYPE == CONTROLLER_TYPE.Joystick) {
      driveController = new Joystick(Constants.OI_DRIVE_CONTROLLER);
    } else {
      driveController = new XboxController(Constants.OI_DRIVE_CONTROLLER);
    }
    operatorController = new XboxController(1); //put me in constants somewhere...


    server.startAutomaticCapture(camera);

    final DoubleSupplier slideAxis = () -> driveController.getRawAxis(4);
    manualTranslateC = new ManualTranslateC(sliderS, slideAxis);

    final DoubleSupplier manualClimbPower = () -> -driveController.getRawAxis(5);
    manualClimbC = new ClimberManualC(climberS, manualClimbPower);
    //Initializes the driveStickC command inline. Simply passes the drive controller axes into the drivebaseS arcadeDrive.
    driveStickC = new RunCommand(() -> drivebaseS.arcadeDrive(driveController.getRawAxis(Constants.AXIS_DRIVE_FWD_BACK), -driveController.getRawAxis(Constants.AXIS_DRIVE_TURN)), drivebaseS);
    
    climberBrakeOnC = new InstantCommand(() -> climberS.brake(), climberS);
    climberBrakeOffC = new InstantCommand(() -> climberS.unbrake(), climberS);
    resetEncodersC = new RunCommand(() -> climberS.resetEncoders(), climberS);
    climberHomeC = new ClimberHomeC(climberS);
    climberUpPIDC = new ClimberUpPIDC(true);
    SmartDashboard.putData(climberUpPIDC);
    climberPullupCG = new ClimberPullupCG();
    SmartDashboard.putData(climberPullupCG);
    //Turn off LiveWindow telemetry. We don't use it and it takes 90% of the loop time.
    LiveWindow.disableAllTelemetry();
    // Configure the button bindings
    configureButtonBindings();

    drivebaseS.setDefaultCommand(driveStickC);
    sliderS.setDefaultCommand(manualTranslateC);
    climberS.setDefaultCommand(manualClimbC);
  }

  /**
   * Use this method to define your button command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driveController, 4).whenPressed(climberBrakeOnC);
    new JoystickButton(driveController, 3).whenPressed(climberBrakeOffC);
    new JoystickButton(driveController, 2).whenPressed(climberHomeC);
    new JoystickButton(driveController, 1).whenPressed(resetEncodersC);
    new JoystickButton(driveController, 5).whenPressed(climberUpPIDC);
    new JoystickButton(driveController, 6).whenPressed(climberPullupCG);
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
