package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * Retracts the Intake and turns off motors in parellel
 */
public class IntakeDeployCG extends ParallelCommandGroup {
  public IntakeDeployCG() {
    addCommands(new IntakeDeployC(), new IntakeMotorC(1.0));
  }
}
