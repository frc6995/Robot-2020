package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterS;

/**
 * Wait until Ready to Fire
 * 
 * @author Shueja
 */
public class ShooterWaitUntilReadyC extends CommandBase {
  private ShooterS shooter;

  public ShooterWaitUntilReadyC(ShooterS shooterS) {
    shooter = shooterS;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return shooter.isReady();
  }
}
