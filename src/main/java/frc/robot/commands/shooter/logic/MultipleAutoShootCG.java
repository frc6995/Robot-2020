package frc.robot.commands.shooter.logic;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.shooter.ShooterWaitUntilFireC;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.ShooterS;

/**
 * Shoot multiple power cells
 * 
 * @author Shuja
 */
public class MultipleAutoShootCG extends SequentialCommandGroup {

  /**
   * Creates a new ballerAutoShootCG.
   * @param shooterS The shooter
   * @param hopperS The hopper
   * @param ballsToFire The number of balls to fire (clips to 5)
   */
  public MultipleAutoShootCG(ShooterS shooterS, HopperS hopperS, int ballsToFire) {
    super(
      //spinup
      new InstantCommand(() -> shooterS.spinUp(), shooterS), 
      new ParallelDeadlineGroup(
        //until we have fired the right amount of balls, or 5
        new ShooterWaitUntilFireC(shooterS, ballsToFire <= 5 ? ballsToFire : 5),
        //repeatedly fire balls
        new ShooterFireOneCG(shooterS, hopperS).perpetually()
      )
    );
  }
}
