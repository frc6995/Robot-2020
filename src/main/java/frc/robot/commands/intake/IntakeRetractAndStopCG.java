package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeS;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class IntakeRetractAndStopCG extends SequentialCommandGroup {
  /**
   * Creates a new IntakeRetractAndStopCG.
   */
  public IntakeRetractAndStopCG(IntakeS intake) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new InstantCommand(() -> intake.intakeRetract(), intake),
    new InstantCommand(() -> intake.intakeMotor(0.0), intake));
  }
}