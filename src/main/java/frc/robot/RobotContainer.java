package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.NomadPathFollowerCommandBuilder;
import frc.robot.commands.climber.ClimberHomeC;
import frc.robot.commands.climber.ClimberManualC;
import frc.robot.commands.climber.ClimberPullupCG;
import frc.robot.commands.climber.ClimberUpPIDC;
import frc.robot.commands.drivebase.DrivebaseVisionC;
import frc.robot.commands.drivebase.EmptyAutoCG;
import frc.robot.commands.drivebase.XBoxDriveC;
import frc.robot.commands.hopper.HopperIdleBallsC;
import frc.robot.commands.hopper.HopperLiftBallsC;
import frc.robot.commands.hopper.HopperLowerBallsC;
import frc.robot.commands.intake.IntakeDeployAndRunCG;
import frc.robot.commands.intake.IntakeFlushCG;
import frc.robot.commands.intake.IntakeRetractAndStopCG;
import frc.robot.commands.slider.ManualTranslateC;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.OIConstants;
import frc.robot.constants.Trajectories;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.DrivebaseS;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.IntakeS;
import frc.robot.subsystems.ShooterS;
import frc.robot.subsystems.SliderS;
import io.github.oblarg.oblog.annotations.Log;

/**
 * Includes Subsystems, Commands, Camera Server, Autonomous Path Calls, and
 * Button Mappings.
 * 
 * @author Sammcdo, EliSauder, JoeyFabel, Shueja, AriShashivkopanazak
 */
public class RobotContainer {
  // Subsystem Instances, all loggable.
  @Log(name = "ClimberS")
  public static final ClimberS climberS = new ClimberS();
  @Log(name = "DrivebaseS")
  public static final DrivebaseS drivebaseS = new DrivebaseS();
  @Log(name = "HopperS")
  public static final HopperS hopperS = new HopperS();
  @Log(name = "IntakeS")
  public final static IntakeS intakeS = new IntakeS();
  @Log(name = "ShooterS")
  public static final ShooterS shooterS = new ShooterS();
  @Log(name = "SliderS")
  public static final SliderS sliderS = new SliderS();
  
  
  @Log
  private final SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  private final EmptyAutoCG basicAutoCG = new EmptyAutoCG();
  private final SequentialCommandGroup sCurveRightAutoCG 
    = new NomadPathFollowerCommandBuilder(Trajectories.sCurveRight, drivebaseS).buildPathFollowerCommandGroup();
  private final Command shoot3AutoCG = shooterS.buildMultipleShootSequence(shooterS, hopperS, intakeS, 3).withTimeout(6);
  private final Command shoot3LeaveLineAutoCG = shooterS.buildMultipleShootSequence(shooterS, hopperS, intakeS, 3).withTimeout(10)
  .andThen(new RunCommand(() -> drivebaseS.arcadeDrive(-0.5, 0), 
   drivebaseS).withTimeout(1))
   .andThen(new InstantCommand(()->drivebaseS.arcadeDrive(0, 0), drivebaseS));
  private final Command visionShoot3LeaveLineAutoCG = new InstantCommand(()->shooterS.spinUp(), shooterS).andThen(new DrivebaseVisionC(drivebaseS, VisionConstants.VISION_PIPELINE_LINE).withTimeout(4)
  .andThen(shooterS.buildMultipleShootSequence(shooterS, hopperS, intakeS, 3)
  .andThen(new RunCommand(() -> drivebaseS.arcadeDrive(-0.5, 0), drivebaseS).withTimeout(0.5)
  .andThen(new InstantCommand(() -> drivebaseS.arcadeDrive(0, 0), drivebaseS))))); 

  // Command Instances
  // OI
  private final GenericHID driveController;
  public final GenericHID operatorController;

  // Climber
  private final ClimberManualC manualClimbC;
  public final ClimberHomeC climberHomeC;
  public final Command climberBrakeOnC;
  public final Command climberBrakeOnAltC;
  public final Command climberBrakeOffC;
  private final ClimberUpPIDC climberUpPIDC;
  private final ClimberPullupCG climberPullupCG;

  // Drivebase
  private final XBoxDriveC xboxDriveC;
  private final Command driveStickC;
  private final DrivebaseVisionC visionAlignLineC;
  private final DrivebaseVisionC visionAlignTrenchC;

  // Hopper
  private final HopperLowerBallsC hopperLowerBallsC;
  private final HopperLiftBallsC hopperLiftBallsC;
  private final HopperIdleBallsC hopperIdleBallsC;

  // Intake
  private final IntakeDeployAndRunCG intakeDeployCG;
  private final IntakeRetractAndStopCG intakeRetractCG;

  // Shooter
  @Log(tabName = "ShooterS")
  private final InstantCommand shooterSpinUpC;
  @Log(tabName = "ShooterS")
  private final InstantCommand shooterSpinDownC;
  @Log(tabName = "ShooterS")
  private final RunCommand shooterManualC;

  // Slider
  private final ManualTranslateC manualTranslateC;

  // Camera Server
  private final CameraServer cam0 = CameraServer.getInstance();
  //private final CameraServer cam1 = CameraServer.getInstance();
  private final UsbCamera camera0 = new UsbCamera("cam0", OIConstants.OI_CAMERA_ZERO);
  private final UsbCamera camera1 = new UsbCamera("cam1", OIConstants.OI_CAMERA_ONE);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Controllers
    driveController = new XboxController(OIConstants.OI_DRIVE_CONTROLLER);
    operatorController = new XboxController(OIConstants.OI_OPERATOR_CONTROLLER);

    // Autonomous Calls
    autoChooser.setDefaultOption("Do Nothing", basicAutoCG);
    autoChooser.addOption("S Curve Right", sCurveRightAutoCG);
    autoChooser.addOption("Shoot 3", shoot3AutoCG);
    autoChooser.addOption("Shoot 3 Leave Line", shoot3LeaveLineAutoCG);
    autoChooser.addOption("VisionShoot3LeaveLine", visionShoot3LeaveLineAutoCG);

    // Start Camera Server
    camera0.setVideoMode(PixelFormat.kMJPEG, 180, 120, 20);
    camera1.setVideoMode(PixelFormat.kMJPEG, 300, 200, 20);
    cam0.startAutomaticCapture(camera0);
    cam0.startAutomaticCapture(camera1);


    // Configures Commands for use in Buttons
    // Climber
    final DoubleSupplier manualClimbPower = () -> -driveController.getRawAxis(5);
    manualClimbC = new ClimberManualC(climberS, manualClimbPower);
    climberBrakeOnC = new InstantCommand(() -> climberS.brake(), climberS);
    climberBrakeOnAltC = new InstantCommand(() -> climberS.brake(), climberS);
    climberBrakeOffC = new InstantCommand(() -> climberS.unbrake(), climberS);
    climberHomeC = new ClimberHomeC(climberS);
    climberUpPIDC = new ClimberUpPIDC(climberS, true);
    climberPullupCG = new ClimberPullupCG(climberS);
    SmartDashboard.putData(climberPullupCG);
    SmartDashboard.putData(climberUpPIDC);

    // Drivebase
    driveStickC = new RunCommand(
        () -> drivebaseS.arcadeDrive(-driveController.getRawAxis(DriveConstants.AXIS_DRIVE_FWD_BACK),
            driveController.getRawAxis(DriveConstants.AXIS_DRIVE_TURN)),
        drivebaseS);
    xboxDriveC = new XBoxDriveC(driveController);
    visionAlignLineC = new DrivebaseVisionC(drivebaseS, VisionConstants.VISION_PIPELINE_LINE);
    visionAlignTrenchC = new DrivebaseVisionC(drivebaseS, VisionConstants.VISION_PIPELINE_TRENCH);

    // Hopper
    hopperLowerBallsC = new HopperLowerBallsC(hopperS);
    hopperLiftBallsC = new HopperLiftBallsC(hopperS);
    hopperIdleBallsC = new HopperIdleBallsC(hopperS);

    // Intake
    intakeDeployCG = new IntakeDeployAndRunCG(intakeS, hopperS);
    intakeRetractCG = new IntakeRetractAndStopCG(intakeS);

    // Shooter
    shooterSpinUpC = new InstantCommand(() -> shooterS.spinUp(), shooterS);
    shooterSpinDownC = new InstantCommand(() -> shooterS.spinDown(), shooterS);
    shooterManualC = new RunCommand(() -> shooterS.setSpeed(operatorController.getRawAxis(0)));

    // Slider
    final DoubleSupplier slideAxis = () -> driveController.getRawAxis(4);
    manualTranslateC = new ManualTranslateC(sliderS, slideAxis);

    // Turn off LiveWindow telemetry. We don't use it and it takes 90% of the loop
    // time.
    LiveWindow.disableAllTelemetry();

    // Configure the button bindings
    configureButtonBindings();

    // Default Configuration of Robot
    drivebaseS.setDefaultCommand(xboxDriveC);
    sliderS.setDefaultCommand(manualTranslateC);
    climberS.setDefaultCommand(manualClimbC);
    // intakeS.setDefaultCommand(intakeRetractCG);
  }

  /**
   * Button Bindings
   */
  private void configureButtonBindings() {
    // Climber
    new JoystickButton(driveController, 3).whenPressed(climberBrakeOffC);
    new JoystickButton(driveController, 4).whenPressed(climberBrakeOnC);
    // test w/ toggle when pressed
    new JoystickButton(driveController, 6).whenPressed(climberUpPIDC.withTimeout(1.5));
    // test w/ toggle when pressed
    new JoystickButton(driveController, 5).whenPressed(climberPullupCG.withTimeout(5).andThen(climberBrakeOnAltC));

    new JoystickButton(driveController, 8).whenPressed(climberHomeC);

    // Drivebase
    new JoystickButton(driveController, 1).whileHeld(visionAlignLineC);
    new JoystickButton(driveController, 2).whileHeld(visionAlignTrenchC);

    // Intake
    JoystickButton intakeButton = new JoystickButton(operatorController, 3);
    intakeButton.whenPressed(intakeDeployCG);
    intakeButton.whenReleased(intakeRetractCG);

    new JoystickButton(operatorController, 7).whenPressed(new InstantCommand(() -> intakeS.intakeToggle(), intakeS), true);
    JoystickButton intakeFlushButton = new JoystickButton(operatorController, 8);
    intakeFlushButton.whenPressed(new IntakeFlushCG(intakeS, hopperS, shooterS));
    intakeFlushButton.whenReleased(new IntakeRetractAndStopCG(intakeS), true);

    // Hopper
    new JoystickButton(operatorController, 4).whileHeld(hopperLiftBallsC);
    new JoystickButton(operatorController, 2).whileHeld(hopperIdleBallsC);
    new JoystickButton(operatorController, 1).whileHeld(hopperLowerBallsC);

    // Shooter
    new JoystickButton(operatorController, 6).whenPressed(shooterSpinUpC);
    new JoystickButton(operatorController, 5).whenPressed(shooterSpinDownC);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    // return autoChooser.getSelected();
    return autoChooser.getSelected()/* new ballerAutoShootCG() */;
  }
}