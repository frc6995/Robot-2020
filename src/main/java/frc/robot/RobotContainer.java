package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ManualTranslateC;
import frc.robot.commands.auto.NomadPathFollowerCommandBuilder;
import frc.robot.commands.commandgroups.MultipleAutoShootCG;
import frc.robot.commands.climber.ClimberHomeC;
import frc.robot.commands.climber.ClimberManualC;
import frc.robot.commands.climber.ClimberPullupCG;
import frc.robot.commands.climber.ClimberUpPIDC;
import frc.robot.commands.drivebase.DrivebaseVisionC;
import frc.robot.commands.drivebase.EmptyAutoCG;
import frc.robot.commands.hopper.HopperIdleBallsC;
import frc.robot.commands.hopper.HopperLiftBallsC;
import frc.robot.commands.hopper.HopperLowerBallsC;
import frc.robot.commands.drivebase.XBoxDriveC;
import frc.robot.commands.intake.IntakeDeployAndRunCG;
import frc.robot.commands.intake.IntakeRetractAndStopCG;
import frc.robot.constants.OIConstants.CONTROLLER_TYPE;
import frc.robot.constants.OIConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.Trajectories;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.DrivebaseS;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.IntakeS;
import frc.robot.subsystems.LightStripS;
import frc.robot.subsystems.ShooterS;
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
  
  //@Log(name="DrivebaseS")
  public static final DrivebaseS drivebaseS = new DrivebaseS();
  @Log(name="ClimberS")
  public static final ClimberS climberS = new ClimberS();
  @Log(name="SliderS")
  public static final SliderS sliderS = new SliderS();
  @Log(name = " ShooterS")
  public static final ShooterS shooterS = new ShooterS();
  @Log(name="HopperS")
  public static final HopperS hopperS = new HopperS();
  @Log(name = "IntakeS")
  public static final IntakeS intakeS = new IntakeS();
  @Log(name="LEDs")
  public static final LightStripS lightStripsS = new LightStripS();
  
  
  private final CameraServer server = CameraServer.getInstance();
  private final UsbCamera camera = new UsbCamera("cam0", 0);
  @Log
  private final SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  private final EmptyAutoCG basicAutoCG = new EmptyAutoCG();
  private final SequentialCommandGroup sCurveRightAutoCG 
    = new NomadPathFollowerCommandBuilder(Trajectories.sCurveRight, drivebaseS).buildPathFollowerCommandGroup();
  
  private final Command driveStickC;
  private final XBoxDriveC xboxDriveC;
  private final DrivebaseVisionC visionAlignC;

  private final ManualTranslateC manualTranslateC;

  private final ClimberManualC manualClimbC;
  public final ClimberHomeC climberHomeC;
  private final Command climberBrakeOnC;
  public final Command climberBrakeOffC;
  private final ClimberUpPIDC climberUpPIDC;
  private final ClimberPullupCG climberPullupCG;

  private final IntakeDeployAndRunCG intakeDeployCG;
  private final IntakeRetractAndStopCG intakeRetractCG;

  public final Command defaultColor;

  @Log(tabName = "ShooterS")
  private final InstantCommand shooterSpinUpC;
  @Log(tabName = "ShooterS")
  private final InstantCommand shooterSpinDownC;
  @Log(tabName = "ShooterS")
  private final RunCommand shooterManualC;
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Initializes driveController as either a Joystick or Xbox depending on
    // Constants.DRIVE_CONTROLLER_TYPE.
    if (OIConstants.DRIVE_CONTROLLER_TYPE == CONTROLLER_TYPE.Joystick) {
      driveController = new Joystick(OIConstants.OI_DRIVE_CONTROLLER);
    } else {
      driveController = new XboxController(OIConstants.OI_OPERATOR_CONTROLLER);
    }
    operatorController = new XboxController(1); //put me in constants somewhere...

    autoChooser.setDefaultOption("Do Nothing", basicAutoCG);
    autoChooser.addOption("S Curve Right", sCurveRightAutoCG);
    autoChooser.addOption("Baller Auto", new MultipleAutoShootCG(shooterS, hopperS, 3));
    
    server.startAutomaticCapture(camera);

    final DoubleSupplier slideAxis = () -> driveController.getRawAxis(4);
    manualTranslateC = new ManualTranslateC(sliderS, slideAxis);

    final DoubleSupplier manualClimbPower = () -> -driveController.getRawAxis(5);
    manualClimbC = new ClimberManualC(climberS, manualClimbPower);
    //Initializes the driveStickC command inline. Simply passes the drive controller axes into the drivebaseS arcadeDrive.
    driveStickC = new RunCommand(() -> drivebaseS.arcadeDrive(-driveController.getRawAxis(DriveConstants.AXIS_DRIVE_FWD_BACK), driveController.getRawAxis(DriveConstants.AXIS_DRIVE_TURN)), drivebaseS);

    defaultColor = new RunCommand(() -> lightStripsS.setColor(0.01), lightStripsS);
    
    climberBrakeOnC = new InstantCommand(() -> climberS.brake(), climberS);
    climberBrakeOffC = new InstantCommand(() -> climberS.unbrake(), climberS);
    climberHomeC = new ClimberHomeC(climberS);
    climberUpPIDC = new ClimberUpPIDC(climberS, true);
    SmartDashboard.putData(climberUpPIDC);
    climberPullupCG = new ClimberPullupCG(climberS);
    SmartDashboard.putData(climberPullupCG);
    xboxDriveC = new XBoxDriveC(driveController);
    //Turn off LiveWindow telemetry. We don't use it and it takes 90% of the loop time.
    LiveWindow.disableAllTelemetry();
    intakeDeployCG = new IntakeDeployAndRunCG(intakeS);
    intakeRetractCG = new IntakeRetractAndStopCG(intakeS);
    
    shooterSpinUpC = new InstantCommand(() -> shooterS.spinUp(), shooterS);
    shooterSpinDownC = new InstantCommand(() -> shooterS.spinDown(), shooterS);
    shooterManualC = new RunCommand(() -> shooterS.setSpeed(operatorController.getRawAxis(0)));
    visionAlignC = new DrivebaseVisionC(drivebaseS);

    // Configure the button bindings
    configureButtonBindings();

    drivebaseS.setDefaultCommand(xboxDriveC);
    sliderS.setDefaultCommand(manualTranslateC);
    climberS.setDefaultCommand(manualClimbC);
    // defaults to Retracted state
    //intakeS.setDefaultCommand(intakeRetractCG);
  }

  /**
   * Use this method to define your button-command mappings.  Buttons can be created by

   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driveController, 1).whileHeld(visionAlignC);
    new JoystickButton(driveController, 2).whileHeld(visionAlignC);
    new JoystickButton(driveController, 3).whenPressed(climberBrakeOffC);
    new JoystickButton(driveController, 4).whenPressed(climberBrakeOnC);
    new JoystickButton(driveController, 5).toggleWhenPressed(climberUpPIDC); //test w/ toggle when pressed
    new JoystickButton(driveController, 6).toggleWhenPressed(climberPullupCG); //test w/ toggle when pressed
    new JoystickButton(driveController, 8).whenPressed(climberHomeC);

    final JoystickButton intakeButton = new JoystickButton(operatorController, 4); // We do two things with this button,
                                                                                   // so instantiate separately
    //to avoid double-allocation.
    intakeButton.whenPressed(()->intakeS.intakeDeploy(), intakeS);
    intakeButton.whenReleased(()->intakeS.intakeRetract(), intakeS);
    new JoystickButton(operatorController, 1).whileHeld(new HopperIdleBallsC(hopperS).alongWith(new RunCommand(()->intakeS.intakeMotor(0), intakeS)));
    new JoystickButton(operatorController, 2).whileHeld(new HopperLiftBallsC(hopperS).alongWith(new RunCommand(()->intakeS.intakeMotor(intakeS.isDeployed() ? -0.5 : 0), intakeS)));
    new JoystickButton(operatorController, 3).whileHeld(new HopperLowerBallsC(hopperS).alongWith(new RunCommand(()->intakeS.intakeMotor(intakeS.isDeployed() ? 0.5 : 0), intakeS)));
    new JoystickButton(operatorController, 5).whenPressed(shooterSpinUpC);
    new JoystickButton(operatorController, 6).whenPressed(shooterSpinDownC);
    
    
  }

  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return autoChooser.getSelected();
    return autoChooser.getSelected()/*new ballerAutoShootCG()*/;
  }
}