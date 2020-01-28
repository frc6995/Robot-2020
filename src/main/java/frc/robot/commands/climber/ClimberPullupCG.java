package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClimberS.brakePosition;

public class ClimberPullupCG extends SequentialCommandGroup {
  /**
   * Creates a new ClimberPullupCG.
   */
  public ClimberPullupCG() {
    addCommands(new ClimberDownPIDC(true),
                new ParallelDeadlineGroup(new SetBrakeC(brakePosition.Brake), new ClimberDownPIDC(false))
                );
  }
}
