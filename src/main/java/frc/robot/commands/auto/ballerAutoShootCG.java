/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.hopper.HopperIdleBallsC;
import frc.robot.constants.Trajectories;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ballerAutoShootCG extends SequentialCommandGroup {
  /**
   * Creates a new ballerAutoShootCG.
   */
  public ballerAutoShootCG() {
    super(new InstantCommand(() -> RobotContainer.shooterS.spinUp(), RobotContainer.shooterS),
      new ParallelDeadlineGroup(new WaitCommand(5), new HopperIdleBallsC(RobotContainer.hopperS)),
      new NomadPathFollowerCommandBuilder(Trajectories.straight2m, RobotContainer.drivebaseS).buildPathFollowerCommandGroup()
    );
  }
}
