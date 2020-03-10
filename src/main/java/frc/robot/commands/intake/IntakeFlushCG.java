package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.hopper.HopperLiftBallsC;
import frc.robot.subsystems.HopperS;
import frc.robot.subsystems.IntakeS;
import frc.robot.subsystems.ShooterS;

public class IntakeFlushCG extends SequentialCommandGroup {
  /**
   * Creates a new IntakeFlushCG.
   */
  public IntakeFlushCG(IntakeS intakeS, HopperS hopperS, ShooterS shooterS) {
    super(new InstantCommand(() -> intakeS.intakeDeploy(), intakeS),
    new ParallelCommandGroup(new RunCommand(() -> intakeS.intakeMotor(-0.5), intakeS),
                              new HopperLiftBallsC(hopperS, -0.2)),
                              new RunCommand(() -> shooterS.setSpeed(-0.8), shooterS));
  }
}
