package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClimberS;
import frc.robot.subsystems.ClimberS.brakePosition;

/**
 * Pullup PID Command Group
 * 
 * @author Sammcdo
 */
public class ClimberPullupCG extends SequentialCommandGroup {
  public ClimberPullupCG(ClimberS climber) {
    addCommands(new ClimberDownPIDC(climber, true),
        new ParallelDeadlineGroup(new SetBrakeC(climber, brakePosition.Brake), new ClimberDownPIDC(climber, false)));
  }
}
