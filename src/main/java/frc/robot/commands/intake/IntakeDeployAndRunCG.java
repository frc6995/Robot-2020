package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.hopper.HopperIdleBallsC;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.IntakeS;

/**
 * Open Intake and Run motors
 * 
 * @author Shueja
 */
public class IntakeDeployAndRunCG extends SequentialCommandGroup {
  public IntakeDeployAndRunCG(IntakeS intake, HopperS hopperS) {
    super(new InstantCommand(() -> intake.intakeDeploy(), intake),
    new RunCommand(() -> intake.intakeMotor(0.5), intake).alongWith(new HopperIdleBallsC(hopperS)));
  }
}
