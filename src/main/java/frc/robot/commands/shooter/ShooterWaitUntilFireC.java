package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RobotLEDS;
import frc.robot.subsystems.ShooterS;
import frc.robot.subsystems.RobotLEDS.ledStates;

/**
 * Wait until Shooter is ready to shoot a power cell
 * 
 * @author Shueja
 */
public class ShooterWaitUntilFireC extends CommandBase {
  private ShooterS shooter;
  private int initBallsFired;
  private int ballsToFire;
  private boolean firstLoop = true;
  /**
   * Creates a new ShooterWaitUntilReadyC.
   */
  public ShooterWaitUntilFireC(ShooterS shooterS, int ammo) {
    shooter = shooterS;
    ballsToFire = ammo;
  }

  @Override
  public void initialize() {
    initBallsFired = shooter.getBallsFired();
  }

  @Override
  public void execute() {
    if (firstLoop) {
      RobotLEDS.robotLEDS.currentState = ledStates.Shooting;
      firstLoop = false;
    }
  }

  @Override
  public void end(boolean interrupted) {
    RobotLEDS.robotLEDS.revertLEDS();
  }

  @Override
  public boolean isFinished() {
    return shooter.getBallsFired() >= initBallsFired + ballsToFire;
  }
}
