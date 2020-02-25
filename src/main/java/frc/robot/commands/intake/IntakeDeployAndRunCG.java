package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeS;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class IntakeDeployAndRunCG extends SequentialCommandGroup {
  /**
   * Creates a new IntakeDeployAndRunCG.
   */
  public IntakeDeployAndRunCG(IntakeS intake) {

    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new InstantCommand(() -> intake.intakeDeploy(), intake),
      new RunCommand(() -> intake.intakeMotor(0.8), intake));
  }
}
