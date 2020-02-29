/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.hopper.HopperIdleBallsC;
import frc.robot.commands.shooter.ShooterWaitUntilFireC;
import frc.robot.commands.shooter.ShooterWaitUntilReadyC;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.ShooterS;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShooterFireOneCG extends SequentialCommandGroup {
  /**
   * Creates a new ShooterFireOneCG.
   */
  public ShooterFireOneCG(ShooterS shooterS, HopperS hopperS) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new ShooterWaitUntilReadyC(shooterS), //wait until it says it is ready
    new ParallelDeadlineGroup(new ShooterWaitUntilFireC(shooterS, 1), new HopperIdleBallsC(hopperS)));
  }
}
