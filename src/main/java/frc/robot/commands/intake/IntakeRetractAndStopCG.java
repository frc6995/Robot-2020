package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeS;

/**
 * Open Intake and Run motors
 * 
 * @author Shueja
 */
public class IntakeRetractAndStopCG extends SequentialCommandGroup {
  public IntakeRetractAndStopCG(IntakeS intake) {
    super(new InstantCommand(() -> intake.intakeRetract(), intake),
        new InstantCommand(() -> intake.intakeMotor(0.0), intake));
  }
}