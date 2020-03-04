/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.commandgroups;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivebase.DrivebaseVisionC;
import frc.robot.subsystems.DrivebaseS;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.ShooterS;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ClassicAutoCG extends SequentialCommandGroup {
  /**
   * Creates a new ClassicAutoCG.
   */
  public ClassicAutoCG(DrivebaseS drivebase, ShooterS shooter, HopperS hopper) {
    super(new DrivebaseVisionC(drivebase),
          new MultipleAutoShootCG(shooter, hopper, 3),
          new RunCommand(() -> drivebase.arcadeDrive(1,0), drivebase).withTimeout(1)
          );
  }
}
