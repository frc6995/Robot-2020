package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * Retracts the Intake and turns off motors in parellel
 */
public class IntakeRetractCG extends ParallelCommandGroup {
  /**
   * Creates a new IntakeRetract.
   */
  public IntakeRetractCG() {
    addCommands(new IntakeRetractC(), new IntakeMotorC(0.0));
  }
}
