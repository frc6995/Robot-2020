package frc.robot.commands.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.hopper.HopperIdleBallsC;
import frc.robot.commands.shooter.ShooterWaitUntilFireC;
import frc.robot.commands.shooter.ShooterWaitUntilReadyC;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.ShooterS;

/**
 * Shoot one power cell
 * 
 * @author Shuja
 */
public class ShooterFireOneCG extends SequentialCommandGroup {
  public ShooterFireOneCG(ShooterS shooterS, HopperS hopperS) {
    super(new ShooterWaitUntilReadyC(shooterS), // wait until it says it is ready
        new ParallelDeadlineGroup(new ShooterWaitUntilFireC(shooterS, 1), new HopperIdleBallsC(hopperS)));
  }
}
