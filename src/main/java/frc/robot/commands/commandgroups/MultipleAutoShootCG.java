/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.shooter.ShooterWaitUntilFireC;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.ShooterS;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class MultipleAutoShootCG extends SequentialCommandGroup {

  /**
   * Creates a new ballerAutoShootCG.
   * @param shooterS The shooter
   * @param hopperS The hopper
   * @param ballsToFire The number of balls to fire (clips to 5)
   */
  public MultipleAutoShootCG(ShooterS shooterS, HopperS hopperS, int ballsToFire) {
    super(
      new InstantCommand(() -> shooterS.spinUp(), shooterS), //spinup
      new ParallelDeadlineGroup(
        new ShooterWaitUntilFireC(shooterS, ballsToFire <= 5 ? ballsToFire : 5), //until we have fired the right amount of balls, or 5
        new ScheduleCommand(new ShooterFireOneCG(shooterS, hopperS)).perpetually() //repeatedly fire balls.
      )
    );
  }
}
