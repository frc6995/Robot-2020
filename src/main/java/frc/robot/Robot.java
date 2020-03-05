package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import io.github.oblarg.oblog.Logger;

/**
 * Robot Class
 * 
 * @author Sammcdo, EliSauder, JoeyFabel, Shueja, AriShashivkopanazak
 */
public class Robot extends TimedRobot {
  private Command autonomousCommand;

  private RobotContainer robotContainer;

  /**
   * Configurations when powered on
   */
  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();
    Logger.configureLoggingAndConfig(this.robotContainer, false);

    // Turns off Limelight LEDs
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry ledModeEntry = table.getEntry("ledmode");
    ledModeEntry.setDouble(1);

    // Rumble Operator Controller
    robotContainer.operatorController.setRumble(RumbleType.kLeftRumble, 0.25);
    robotContainer.operatorController.setRumble(RumbleType.kRightRumble, 0.25);
  }

  /**
   * Configuration that will run while powered on
   */
  @Override
  public void robotPeriodic() {
    Logger.updateEntries();
    CommandScheduler.getInstance().run();
  }

  /**
   * Configurations when Disabled is called
   */
  @Override
  public void disabledInit() {
    RobotContainer.shooterS.stop();
    robotContainer.climberBrakeOnC.initialize();
  }

  /**
   * Configurations that will run while disabled
   */
  @Override
  public void disabledPeriodic() {
    // Rumble Operator Controller
    robotContainer.operatorController.setRumble(RumbleType.kLeftRumble, 0.25);
    robotContainer.operatorController.setRumble(RumbleType.kRightRumble, 0.25);
  }

  /**
   * Configurations when Autonomous mode is called
   */
  @Override
  public void autonomousInit() {
    // Stops Rumble on Operator Controller
    robotContainer.operatorController.setRumble(RumbleType.kLeftRumble, 0);
    robotContainer.operatorController.setRumble(RumbleType.kRightRumble, 0);
    autonomousCommand = robotContainer.getAutonomousCommand();

    robotContainer.climberBrakeOffC.initialize();

    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  /**
   * Configurations that will run during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * Configurations that run when teleop is called
   */
  @Override
  public void teleopInit() {
    // Stops Rumble on Operator Controller
    robotContainer.operatorController.setRumble(RumbleType.kLeftRumble, 0);
    robotContainer.operatorController.setRumble(RumbleType.kRightRumble, 0);

    robotContainer.climberBrakeOffC.initialize();

    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  /**
   * Configurations that run during teleop
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
