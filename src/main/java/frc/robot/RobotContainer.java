package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.cscore.UsbCamera;
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
import frc.robot.commands.intake.IntakeRetractAndStopCG;
import frc.robot.commands.shooter.logic.MultipleAutoShootCG;
import frc.robot.commands.slider.ManualTranslateC;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.OIConstants;
import frc.robot.constants.Trajectories;
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

  // Command Instances
  // OI
  private final GenericHID driveController;
  public final GenericHID operatorController;
  public final Command driveStickC;

  // Climber
  private final ClimberManualC manualClimbC;
  public final ClimberHomeC climberHomeC;
  public final Command climberBrakeOnC;
  public final Command climberBrakeOffC;
  private final ClimberUpPIDC climberUpPIDC;
  private final ClimberPullupCG climberPullupCG;

  // Drivebase
  private final XBoxDriveC xboxDriveC;
  private final DrivebaseVisionC visionAlignC;

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
  private final CameraServer server = CameraServer.getInstance();
  private final UsbCamera camera = new UsbCamera("cam0", OIConstants.OI_CAMERA);

  // Autonomous Path Call Declarations
  @Log
  private final SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  private final EmptyAutoCG basicAutoCG = new EmptyAutoCG();
  private final SequentialCommandGroup sCurveRightAutoCG = new NomadPathFollowerCommandBuilder(Trajectories.sCurveRight,
      drivebaseS).buildPathFollowerCommandGroup();

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
    autoChooser.addOption("Baller Auto", new MultipleAutoShootCG(shooterS, hopperS, 3));

    // Start Camera Server
    server.startAutomaticCapture(camera);

    // Configures Commands for use in Buttons
    // Climber
    final DoubleSupplier manualClimbPower = () -> -driveController.getRawAxis(5);
    manualClimbC = new ClimberManualC(climberS, manualClimbPower);
    climberBrakeOnC = new InstantCommand(() -> climberS.brake(), climberS);
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
    visionAlignC = new DrivebaseVisionC(drivebaseS);

    // Hopper
    hopperLowerBallsC = new HopperLowerBallsC(hopperS);
    hopperLiftBallsC = new HopperLiftBallsC(hopperS);
    hopperIdleBallsC = new HopperIdleBallsC(hopperS);

    // Intake
    intakeDeployCG = new IntakeDeployAndRunCG(intakeS);
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
    new JoystickButton(driveController, 6).whenPressed(climberUpPIDC);
    // test w/ toggle when pressed
    new JoystickButton(driveController, 5).whenPressed(climberPullupCG);

    // Drivebase
    new JoystickButton(driveController, 1).whileHeld(visionAlignC);
    new JoystickButton(driveController, 2).whileHeld(visionAlignC);

    // Intake
    JoystickButton intakeButton = new JoystickButton(operatorController, 3);
    intakeButton.whenPressed(intakeDeployCG);
    intakeButton.whenReleased(intakeRetractCG);

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