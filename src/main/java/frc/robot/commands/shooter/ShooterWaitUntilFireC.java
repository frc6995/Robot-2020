package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterS;

/**
 * Wait until Shooter is ready to shoot a power cell
 * 
 * @author Shuja
 */
public class ShooterWaitUntilFireC extends CommandBase {
  private ShooterS shooter;
  private int initBallsFired;
  private int ballsToFire;

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
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return shooter.getBallsFired() >= initBallsFired + ballsToFire;
  }
}
