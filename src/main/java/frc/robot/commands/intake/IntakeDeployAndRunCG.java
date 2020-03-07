package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeS;

/**
 * Open Intake and Run motors
 * 
 * @author Shuja
 */
public class IntakeDeployAndRunCG extends SequentialCommandGroup {
  public IntakeDeployAndRunCG(IntakeS intake) {
    super(new InstantCommand(() -> intake.intakeDeploy(), intake),
        new RunCommand(() -> intake.intakeMotor(0.8), intake));
  }
}
